package com.projet.ecommerce.business.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.CaracteristiquePK;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProduitBusinessTests {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private CategorieRepository categorieRepository;
    
    @Mock
    private CaracteristiqueRepository caracteristiqueRepo;

    @Mock
    private PhotoRepository photoRepository;

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
        assertDataProduit(produit, retour1);

        // Je teste si le produit business m'envoie bien une GraphQLCustomException, si le produit existe déjà
        thrown.expect(GraphQLCustomException.class);
        ProduitDTO retour2 = produitBusiness.add("", "", "dfdfdf", 0, null, null);
        Assert.assertNull(retour2);
    }

	private void assertDataProduit(Produit produit, ProduitDTO produitDto) {
		Assert.assertNotNull(produitDto);
		Assert.assertNotNull(produit);
        Assert.assertEquals(produit.getNom(), produitDto.getNom());
        Assert.assertEquals(produit.getDescription(), produitDto.getDescription());
        Assert.assertEquals(produit.getPrixHT(), produitDto.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), produitDto.getRef());
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
    public void addproduitAvecCaracteristiques() {
        //initialisation du contexte du test
    	Produit produit = buildProduit();
        
		Caracteristique caracteristique = produit.getCaracteristiques().get(0);
		String typeCaracteristique = caracteristique.getCaracteristiquePk().getTypeCaracteristique().getNomTypeCaracteristique();
		List<CaracteristiqueDTO> listeCaracteristiquesDto = CaracteristiqueTransformer.entityToDto(produit.getCaracteristiques(), null );
        
        Mockito.when(produitRepository.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());

		// test de la méthode
		ProduitDTO retour = produitBusiness.add("A05A01", "Livre1", "Un livre", 2.1f, null, listeCaracteristiquesDto );
		
		//Vérification du test
        
        CaracteristiqueDTO caracteristiquesRetour = retour.getCaracteristiques().get(0);
		
		//TODO Assert sur tout le produit + ordre equals
		//assertDataProduit(produit, retour);
        Assert.assertNotNull(retour);
		Assert.assertNotNull(produit);
        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());
		Assert.assertNotNull(caracteristiquesRetour);
		Assert.assertNotNull(caracteristique);
		Assert.assertEquals(typeCaracteristique, caracteristiquesRetour.getTypeCaracteristiqueDto().getNomType());
        Assert.assertEquals(caracteristique.getValeurCaracteristique(), caracteristiquesRetour.getValeurCaracteristique());
    }

	

    @Test
    public void updateFound() {
        Produit produit = buildProduit();

        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(produit));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.update(produit);
        assertDataProduit(produit, retour);
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
    
    private void addCaracteristiques(Produit produit) {
        TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
        typeCaracteristique.setNomTypeCaracteristique("Edition");
        
        CaracteristiquePK caracteristiquePk = new CaracteristiquePK();
        caracteristiquePk.setTypeCaracteristique(typeCaracteristique);
        caracteristiquePk.setProduit(produit);
        
        Caracteristique caracteristique = new Caracteristique();
		caracteristique.setCaracteristiquePk(caracteristiquePk);
		caracteristique.setValeurCaracteristique("Nathan");
		produit.setCaracteristiques(Collections.singletonList(caracteristique));
	}

    @NotNull
    private Produit buildProduit() { 	
    	
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setPhotos(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
        addCaracteristiques(produit);
        return produit;
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

    //TODO Faire le teste avec id catégorie
}
