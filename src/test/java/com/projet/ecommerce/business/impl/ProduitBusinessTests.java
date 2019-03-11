package com.projet.ecommerce.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProduitBusinessTests {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private CategorieRepository categorieRepository;

    @Mock
    private ProduitRepositoryCustom produitRepositoryCustom;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private Page page;
    
    @Mock
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;
    
    @Mock
    private CaracteristiqueRepository caracteristiqueRepository;

    @InjectMocks
    private ProduitBusiness produitBusiness;

    private TypeCaracteristiqueDTO typeCaracteristiqueDTO;
    private CaracteristiqueDTO caracteristiqueDTO;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
        typeCaracteristiqueDTO.setId(1);
        typeCaracteristiqueDTO.setTypeCarac("Broché");
        
        caracteristiqueDTO  = new CaracteristiqueDTO();
        caracteristiqueDTO.setId(1);
        caracteristiqueDTO.setTypeCaracteristiqueDTO(typeCaracteristiqueDTO);
        caracteristiqueDTO.setValeur("223 pages");
        
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void add() {
    	
        ArrayList<CaracteristiqueDTO> caracteristiquesDTO = new ArrayList<CaracteristiqueDTO>();
        caracteristiquesDTO.add(caracteristiqueDTO);
    	
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>(Arrays.asList(CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO))));
        
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);
        
        Mockito.when(typeCaracteristiqueRepository.findById(Mockito.any())).thenReturn(Optional.of(TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTO)));
        Mockito.when(caracteristiqueRepository.save(Mockito.any())).thenReturn(CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO));
        
        ProduitDTO retour1 = produitBusiness.add("A05A01", "Test", "Test", 4.7f, null,caracteristiquesDTO);
        Assert.assertNotNull(retour1);
        Assert.assertEquals(produit.getNom(), retour1.getNom());
        Assert.assertEquals(produit.getDescription(), retour1.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour1.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour1.getRef());

        // Je teste si le produit business m'envoie bien une GraphQLCustomException, si le produit existe déjà
        thrown.expect(GraphQLCustomException.class);
        ProduitDTO retour2 = produitBusiness.add("", "", "dfdfdf", 0, null,new ArrayList<>(Arrays.asList(caracteristiqueDTO)));
        Assert.assertNull(retour2);
    }

    @Test
    public void addProductAlreadyExist() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>(Arrays.asList(CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO))));

        // Je teste si le produit business m'envoie bien une GraphQLCustomException, si le produit existe déjà
        thrown.expect(GraphQLCustomException.class);
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        ProduitDTO retour = produitBusiness.add("A05A01", "Test", "Test", 4.7f, null,new ArrayList<>(Arrays.asList(caracteristiqueDTO)));
        Assert.assertNull(retour);
    }

    @Test
    public void addProductWithCategories() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");

        Categorie categorie = new Categorie();
        categorie.setNomCategorie("Transport");
        categorie.setIdCategorie(1);
        categorie.setBorneGauche(1);
        categorie.setBorneDroit(8);

        List<Categorie> categorieList = new ArrayList<>();
        categorieList.add(categorie);

        produit.setCategories(categorieList);
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>(Arrays.asList(CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO))));

        List<Integer> categoriesProduit = new ArrayList<>();
        categoriesProduit.add(1);
        categoriesProduit.add(2);
        categoriesProduit.add(3);


        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categorie));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);
        
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(produit));
        Mockito.when(typeCaracteristiqueRepository.findById(Mockito.any())).thenReturn(Optional.of(TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTO)));

        ProduitDTO retour = produitBusiness.add("A05A01", "Test", "Test", 4.7f, categoriesProduit, new ArrayList<>(Arrays.asList(caracteristiqueDTO)));


        Assert.assertNotNull(retour);
        Assert.assertEquals(retour.getClass(), ProduitDTO.class);
        Assert.assertEquals(retour.getCategories().get(0).getNom(), "Transport");
    }

    @Test
    public void updateFound() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());

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
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Produit produit = new Produit();
        produit.setReferenceProduit("Test");
        thrown.expect(GraphQLCustomException.class);
        produitBusiness.update(produit);
    }

    @Test
    public void updateCatNotFound() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");

        List<Categorie> categorieList = new ArrayList<>();
        categorieList.add(new Categorie());

        produit.setCategories(categorieList);
        produit.setPhotos(new ArrayList<>());

        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));

        thrown.expect(GraphQLCustomException.class);
        produitBusiness.update(produit);
    }

    @Test
    public void updateCatFound() {
        Categorie categorie = new Categorie();

        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");

        List<Categorie> categorieList = new ArrayList<>();
        categorieList.add(categorie);

        produit.setCategories(categorieList);
        produit.setPhotos(new ArrayList<>());

        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categorie));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.update(produit);
        Assert.assertNotNull(retour);
        Assert.assertEquals(1, retour.getCategories().size());
    }

    @Test
    public void updatePictureNotFound() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setCategories(new ArrayList<>());

        List<Photo> photoList = new ArrayList<>();
        photoList.add(new Photo());

        produit.setPhotos(photoList);

        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));

        thrown.expect(GraphQLCustomException.class);
        produitBusiness.update(produit);
    }

    @Test
    public void updatePictureFound() {
        Photo photo = new Photo();

        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setCategories(new ArrayList<>());

        List<Photo> photoList = new ArrayList<>();
        photoList.add(photo);

        produit.setPhotos(photoList);

        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        Mockito.when(photoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(photo));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.update(produit);
        Assert.assertNotNull(retour);
        Assert.assertEquals(1, retour.getPhotos().size());
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

        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setPhotos(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
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
        // Initialisation
        List<Produit> produitList = new ArrayList<>();
        Mockito.when(produitRepositoryCustom.findAllWithCriteria(Mockito.anyString(), Mockito.anyString())).thenReturn(produitList);
        Assert.assertEquals(produitRepositoryCustom.findAllWithCriteria(Mockito.anyString(), Mockito.anyString()).size(), 0);

        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setPhotos(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
        produitList.add(produit);

        // Permets de tester si la méthode retourne une List avec une référence
        Mockito.when(produitRepositoryCustom.findAllWithCriteria(Mockito.any(), Mockito.any())).thenReturn(produitList);
        Mockito.verify(produitRepositoryCustom, Mockito.times(1)).findAllWithCriteria(Mockito.any(), Mockito.any());
        List<ProduitDTO> produitDTOList = produitBusiness.getAll("ref", null, null);
        Assert.assertEquals(produitDTOList.size(), 1);

        // Permets de tester si la méthode retourne une Liste avec un nom de catégorie
        produitDTOList = produitBusiness.getAll(null, "cat", null);
        Assert.assertEquals(produitDTOList.size(), 1);

        // Permets de tester si la méthode retourne une Liste avec un nom de produit
        Mockito.when(produitRepository.findByNomContainingIgnoreCase(Mockito.any())).thenReturn(produitList);
        produitDTOList = produitBusiness.getAll(null, null, "Livre1");
        Mockito.verify(produitRepository, Mockito.times(1)).findByNomContainingIgnoreCase(Mockito.anyString());
        Assert.assertEquals(produitDTOList.size(), 1);

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

    //TODO Faire le teste avec id catégorie
}
