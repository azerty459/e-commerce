package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.ProduitCaracteristique;
import com.projet.ecommerce.persistance.entity.ProduitCaracteristiqueId;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProduitBusinessTests {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private CategorieRepository categorieRepository;

    @Mock
    private PhotoRepository photoRepository;
    
    @Mock
    private CaracteristiqueRepository caracteristiqueRepository;

    @Mock
    private Page page;

    @InjectMocks
    private ProduitBusiness produitBusiness;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void add() {
        Produit produit = buildProduit();
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour1 = produitBusiness.add("A05A01", "Test", "Test", 4.7f, null, null);
        Assert.assertNotNull(retour1);
        Assert.assertEquals(produit.getNom(), retour1.getNom());
        Assert.assertEquals(produit.getDescription(), retour1.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour1.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour1.getRef());

        // Je teste si le produit business m'envoie bien une GraphQLCustomException, si le produit existe déjà
        thrown.expect(GraphQLCustomException.class);
        ProduitDTO retour2 = produitBusiness.add("", "", "dfdfdf", 0, null, null);
        Assert.assertNull(retour2);
    }

    @Test
    public void addProductAlreadyExist() {
        Produit produit = buildProduit();

        // Je teste si le produit business m'envoie bien une GraphQLCustomException, si le produit existe déjà
        thrown.expect(GraphQLCustomException.class);
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        ProduitDTO retour = produitBusiness.add("A05A01", "Test", "Test", 4.7f, null, null);
        Assert.assertNull(retour);
    }

    @Test
    public void addProductWithCategories() {
        Produit produit = buildProduit();

        Categorie categorie = new Categorie();
        categorie.setNomCategorie("Transport");
        categorie.setIdCategorie(1);
        categorie.setBorneGauche(1);
        categorie.setBorneDroit(8);
        produit.getCategories().add(categorie);

        List<Integer> categoriesProduit = new ArrayList<>();
        categoriesProduit.add(1);
        categoriesProduit.add(2);
        categoriesProduit.add(3);

        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categorie));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.add("A05A01", "Test", "Test", 4.7f, categoriesProduit, null);

        Assert.assertNotNull(retour);
        Assert.assertEquals(retour.getClass(), ProduitDTO.class);
        Assert.assertEquals(retour.getCategories().get(0).getNom(), "Transport");
    }
    
    @Test
    public void testAddProduitAvecCaracteristiques() {
        String valeur = "TestValeur";
        Caracteristique caracteristique = buildCaracteristique();
        Mockito.when(produitRepository.save(Mockito.any())).then(mockInvoc -> mockInvoc.getArgument(0));
        Mockito.when(caracteristiqueRepository.findByLibelle(Mockito.anyString())).thenReturn(Optional.of(caracteristique));
        
        Map<String, String> caracteristiquesProduit = new HashMap<>();
        caracteristiquesProduit.put(caracteristique.getLibelle(), valeur);
        ProduitDTO retour = produitBusiness.add("A05A01", "Test", "Test", 4.7f, null, caracteristiquesProduit);
        Assert.assertEquals(1, retour.getCaracteristiques().size());
        ProduitCaracteristiqueDTO produitCaracteristiqueDTO = retour.getCaracteristiques().get(0);
        Assert.assertEquals(valeur, produitCaracteristiqueDTO.getValeur());
        Assert.assertEquals(caracteristique.getLibelle(), produitCaracteristiqueDTO.getCaracteristique().getLibelle());
    }

    @Test
    public void updateFound() {
        Produit produit = buildProduit();

        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(produit));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.update(produit);
        Assert.assertNotNull(retour);

        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());
    }

    @Test
    public void updateNull() {
        produitBusiness.update(null);
    }

    @Test
    public void updateNotFound() {
        Produit produit = buildProduit();

        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        thrown.expect(GraphQLCustomException.class);
        produitBusiness.update(produit);
    }

    @Test
    public void updateCatNotFound() {
        Produit produit = buildProduit();

        produit.getCategories().add(new Categorie());

        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));

        thrown.expect(GraphQLCustomException.class);
        produitBusiness.update(produit);
    }

    @Test
    public void updateCatFound() {
        Produit produit = buildProduit();

        Categorie categorie = new Categorie();
        produit.getCategories().add(categorie);

        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categorie));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.update(produit);
        Assert.assertNotNull(retour);
        Assert.assertEquals(1, retour.getCategories().size());
    }

    @Test
    public void updatePictureNotFound() {
        Produit produit = buildProduit();

        final Photo photo = new Photo();
        photo.setIdPhoto(1);
        produit.setPhotoPrincipale(photo);

        Mockito.when(photoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));

        thrown.expect(GraphQLCustomException.class);
        produitBusiness.update(produit);
    }

    @Test
    public void updatePictureFound() {
        Produit produit = buildProduit();

        Photo photo = new Photo();
        produit.setPhotoPrincipale(photo);

        Mockito.when(produitRepository.findById(produit.getReferenceProduit())).thenReturn(Optional.of(produit));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.update(produit);
        Assert.assertNotNull(retour);
        Assert.assertNotNull(retour.getPhotoPrincipale());
    }

    @Test
    public void delete() {
        Assert.assertTrue(produitBusiness.delete("A05A01"));
    }

    @Test
    public void getAll() {
        List<Produit> produitList = new ArrayList<>();
        Mockito.when(produitRepository.findAll()).thenReturn(produitList);
        Assert.assertEquals(produitBusiness.getAll().size(), 0);

        Produit produit = buildProduit();
        produitList.add(produit);

        Mockito.when(produitRepository.findAll()).thenReturn(produitList);
        Mockito.verify(produitRepository, Mockito.times(1)).findAll();
        List<ProduitDTO> produitDTOList = produitBusiness.getAll();
        Assert.assertEquals(produitDTOList.size(), 1);

        ProduitDTO retour = produitDTOList.get(0);
        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());

        Mockito.verify(produitRepository, Mockito.times(2)).findAll();
    }

    @Test
    public void getAllByRefAndCatAndName() {
        List<ProduitDTO> produitDTOList;

        // Initialisation
        Produit produit = buildProduit();
        List<Produit> produitList = new ArrayList<>();
        produitList.add(produit);

        Mockito.when(produitRepository.findAllWithCriteria(Mockito.any(), Mockito.any())).thenReturn(produitList);
        Mockito.when(produitRepository.findByNomContainingIgnoreCase(Mockito.any())).thenReturn(produitList);

        // Permets de tester si la méthode retourne une List avec une référence
        produitDTOList = produitBusiness.getAll(produit.getReferenceProduit(), null, null);
        Assert.assertEquals(produitDTOList.size(), 1);
        Mockito.verify(produitRepository, Mockito.times(1)).findAllWithCriteria(Mockito.any(), Mockito.any());
        Mockito.verify(produitRepository, Mockito.times(0)).findByNomContainingIgnoreCase(Mockito.any());

        // Permets de tester si la méthode retourne une Liste avec un nom de catégorie
        produitDTOList = produitBusiness.getAll(null, "cat", null);
        Assert.assertEquals(produitDTOList.size(), 1);
        Mockito.verify(produitRepository, Mockito.times(2)).findAllWithCriteria(Mockito.any(), Mockito.any());
        Mockito.verify(produitRepository, Mockito.times(0)).findByNomContainingIgnoreCase(Mockito.any());

        // Permets de tester si la méthode retourne une Liste avec un nom de produit
        produitDTOList = produitBusiness.getAll(null, null, produit.getNom());
        Assert.assertEquals(produitDTOList.size(), 1);
        Mockito.verify(produitRepository, Mockito.times(2)).findAllWithCriteria(Mockito.any(), Mockito.any());
        Mockito.verify(produitRepository, Mockito.times(1)).findByNomContainingIgnoreCase(Mockito.any());

        // Teste si les valeurs sont cohérentes avec le produit retournées
        ProduitDTO retour = produitDTOList.get(0);
        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());
    }

    @Test
    public void getAllByRefAndCatEmpty() {
        thrown.expect(GraphQLCustomException.class);
        produitBusiness.getAll(null, null, null);
    }

    @Test
    public void getPageWithoutName() {
        Mockito.when(produitRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
        Assert.assertNotNull(produitBusiness.getPage(1, 5, null, 0));
    }

    @Test
    public void getPageWithName() {
        Mockito.when(produitRepository.findByNomContainingIgnoreCase(Mockito.any(Pageable.class), Mockito.anyString())).thenReturn(page);
        Assert.assertNotNull(produitBusiness.getPage(1, 5, "Toto", 0));
    }
    
    @Test
    public void testAddCaracteristique() {
        Produit produit = buildProduit();
        Caracteristique caracteristique = buildCaracteristique();
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caracteristique));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);
        
        String valeur = "TestValeur";
        ProduitDTO retour = produitBusiness.addCaracteristique("TestRef", CaracteristiqueTransformer.entityToDto(caracteristique), valeur);
        Assert.assertEquals(1, retour.getCaracteristiques().size());
        boolean have = false;
        for(ProduitCaracteristique pc : produit.getCaracterisitiques()) {
            have = have || (valeur.equals(pc.getValeur()) && pc.getId().getCaracteristique().getLibelle().equals(caracteristique.getLibelle()));
        }
        Assert.assertTrue(have);
        //Test des exceptions
        thrown.expect(GraphQLCustomException.class);
        //CaracteristiqueDTO ne correspond pas à une caracteristique de la base
        retour = produitBusiness.addCaracteristique("TestRef", new CaracteristiqueDTO(4, "TestDTO"), valeur);
        Assert.assertNull(retour);
        //CaracteristiqueDTO null
        retour = produitBusiness.addCaracteristique("TestRef", null, valeur);
        Assert.assertNull(retour);
        //Reference null
        retour = produitBusiness.addCaracteristique(null, CaracteristiqueTransformer.entityToDto(caracteristique), valeur);
        Assert.assertNull(retour);
        //Reference vide
        retour = produitBusiness.addCaracteristique("\t", CaracteristiqueTransformer.entityToDto(caracteristique), valeur);
        Assert.assertNull(retour);
        //Valeur null
        retour = produitBusiness.addCaracteristique("TestRef", CaracteristiqueTransformer.entityToDto(caracteristique), null);
        Assert.assertNull(retour);
        //Valeur vide
        retour = produitBusiness.addCaracteristique("TestRef", CaracteristiqueTransformer.entityToDto(caracteristique), "\t");
        Assert.assertNull(retour);
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testAddCaracteristiqueBadProduit() {
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        
        produitBusiness.addCaracteristique("TestRef", CaracteristiqueTransformer.entityToDto(buildCaracteristique()), "TestValeur");
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testAddCaracteristiqueBadCaracteristique() {
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(buildProduit()));
        Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        
        produitBusiness.addCaracteristique("TestRef", CaracteristiqueTransformer.entityToDto(buildCaracteristique()), "TestValeur");
    }
    
    @Test
    public void testDeleteCaracteristique() {
        Produit produit = buildProduit();
        Caracteristique caracteristique = buildCaracteristique();
        CaracteristiqueDTO caracteristiqueDTO = CaracteristiqueTransformer.entityToDto(caracteristique);
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caracteristique));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);
        ProduitDTO ajout = produitBusiness.addCaracteristique("TestRef", caracteristiqueDTO, "TestValeur");
        int sizeBefore = ajout.getCaracteristiques().size();
  
        ProduitDTO retour = produitBusiness.deleteCaracteristique("TestRef", caracteristiqueDTO);
        Assert.assertEquals(sizeBefore - 1, retour.getCaracteristiques().size());
        boolean have = false;
        for(ProduitCaracteristique pc : produit.getCaracterisitiques()) {
            have = have || (pc.getValeur().equals(pc.getValeur()) && pc.getId().getCaracteristique().getLibelle().equals(caracteristique.getLibelle()));
        }
        Assert.assertFalse(have);
        //Test des exceptions
        thrown.expect(GraphQLCustomException.class);
        //Reference null
        retour = produitBusiness.deleteCaracteristique(null, caracteristiqueDTO);
        Assert.assertNull(retour);
        //Reference vide
        retour = produitBusiness.deleteCaracteristique("\t", caracteristiqueDTO);
        Assert.assertNull(retour);
        //CaracteristiqueDTO qui ne correspond pas à celui en base
        retour = produitBusiness.deleteCaracteristique("TestRef", new CaracteristiqueDTO(4, "TestDTO"));
        Assert.assertNull(retour);
        //CaracteristiqueDTO null
        retour = produitBusiness.deleteCaracteristique("TestRef", null);
        Assert.assertNull(retour);
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testDeleteCaracteristiqueBadProduit() {
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        
        produitBusiness.deleteCaracteristique("TestRef", CaracteristiqueTransformer.entityToDto(buildCaracteristique()));
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testDeleteCaracteristiqueBadCaracteristique() {
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(buildProduit()));
        Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        
        produitBusiness.deleteCaracteristique("TestRef", CaracteristiqueTransformer.entityToDto(buildCaracteristique()));
    }
    
    @Test
    public void testGetAllCaracteristiques() {
        Produit produit = buildProduit();
        Caracteristique caracteristique = buildCaracteristique();
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caracteristique));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);
        
        List<ProduitCaracteristiqueDTO> listProduitCaracteristiqueDTO = produitBusiness.getAllCaracteristiques("TestRef");
        Assert.assertEquals(0, listProduitCaracteristiqueDTO.size());
        
        String valeur = "TestValeur";
        produitBusiness.addCaracteristique("TestRef", CaracteristiqueTransformer.entityToDto(caracteristique), valeur);
        
        listProduitCaracteristiqueDTO = produitBusiness.getAllCaracteristiques("TestRef");
        Assert.assertEquals(1, listProduitCaracteristiqueDTO.size());
        ProduitCaracteristiqueDTO produitCaracteristiqueDTO = listProduitCaracteristiqueDTO.get(0);
        Assert.assertEquals(valeur, produitCaracteristiqueDTO.getValeur());
        Assert.assertEquals(caracteristique.getLibelle(), produitCaracteristiqueDTO.getCaracteristique().getLibelle());
        
        //Test des exceptions
        thrown.expect(GraphQLCustomException.class);
        //Reference null
        listProduitCaracteristiqueDTO = produitBusiness.getAllCaracteristiques(null);
        Assert.assertNull(listProduitCaracteristiqueDTO);
        //Reference vide
        listProduitCaracteristiqueDTO = produitBusiness.getAllCaracteristiques("\t");
        Assert.assertNull(listProduitCaracteristiqueDTO);
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testGetAllCaracteristiquesBadProduit() {
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        
        produitBusiness.getAllCaracteristiques("TestRef");
    }

    //TODO Faire le teste avec id catégorie
    
    /* ----- Builder ----- */
    
    @NotNull
    private Produit buildProduit() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setPhotos(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
        produit.setCaracterisitiques(new ArrayList<>());
        return produit;
    }
    
    @NotNull
    private Caracteristique buildCaracteristique() {
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setIdCaracteristique(8);
        caracteristique.setLibelle("Test");
        caracteristique.setProduits(new ArrayList<>());
        return caracteristique;
    }
}
