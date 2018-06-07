package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
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
import java.util.List;
import java.util.Optional;

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
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>());
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour1 = produitBusiness.add("A05A01", "Test", "Test", 4.7f, null);
        Assert.assertNotNull(retour1);
        Assert.assertEquals(produit.getNom(), retour1.getNom());
        Assert.assertEquals(produit.getDescription(), retour1.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour1.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour1.getRef());

        // Je teste si le produit business m'envoie bien une GraphQLCustomException, si le produit existe déjà
        thrown.expect(GraphQLCustomException.class);
        ProduitDTO retour2 = produitBusiness.add("", "", "dfdfdf", 0, null);
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
        produit.setCaracteristiques(new ArrayList<>());

        // Je teste si le produit business m'envoie bien une GraphQLCustomException, si le produit existe déjà
        thrown.expect(GraphQLCustomException.class);
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        ProduitDTO retour = produitBusiness.add("A05A01", "Test", "Test", 4.7f, null);
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

        List<Categorie> categorieList = new ArrayList<Categorie>();
        categorieList.add(categorie);

        produit.setCategories(categorieList);
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>());

        List<Integer> categoriesProduit = new ArrayList<>();
        categoriesProduit.add(1);
        categoriesProduit.add(2);
        categoriesProduit.add(3);


        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categorie));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.add("A05A01", "Test", "Test", 4.7f, categoriesProduit);


        Assert.assertNotNull(retour);
        Assert.assertEquals(retour.getClass(), ProduitDTO.class);
        Assert.assertEquals(retour.getCategories().get(0).getNom(), "Transport");
    }

    @Test
    public void updateFound() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>());

        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(produit));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);
        ProduitDTO retour = produitBusiness.update("Test", "Test", "Test", 4.7, 0, 0);
        Assert.assertNotNull(retour);

        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());
    }

    @Test
    public void updateEmpty() {
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        thrown.expect(GraphQLCustomException.class);
        ProduitDTO retour2 = produitBusiness.update("Test", "Test", "Test", 4.7, 0, 0);
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
        produit.setCaracteristiques(new ArrayList<>());
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
    public void getAllByRefAndCat() {
        List<Produit> produitList = new ArrayList<>();
        Mockito.when(produitRepositoryCustom.findAllWithCriteria(Mockito.anyString(), Mockito.anyString())).thenReturn(produitList);
        Assert.assertEquals(produitRepositoryCustom.findAllWithCriteria(Mockito.anyString(), Mockito.anyString()).size(), 0);

        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
        produitList.add(produit);


        Mockito.when(produitRepositoryCustom.findAllWithCriteria(Mockito.anyString(), Mockito.anyString())).thenReturn(produitList);
        Mockito.verify(produitRepositoryCustom, Mockito.times(1)).findAllWithCriteria(Mockito.anyString(), Mockito.anyString());
        List<ProduitDTO> produitDTOList = produitBusiness.getAll("Test", "Test");
        Assert.assertEquals(produitDTOList.size(), 1);

        ProduitDTO retour = produitDTOList.get(0);
        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());
    }

    @Test
    public void getAllByRefAndCatEmpty() {
        thrown.expect(GraphQLCustomException.class);
        produitBusiness.getAll(null, null);
    }

    @Test
    public void getProduitByRef() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>());

        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(produit));
        ProduitDTO retour = produitBusiness.getByRef("A05A01");
        Assert.assertNotNull(retour);

        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());
    }

    @Test
    public void getProduitByRefNotFound() {
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        thrown.expect(GraphQLCustomException.class);
        produitBusiness.getByRef("A04A78");
    }

    @Test
    public void getPage() {
        Mockito.when(produitRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
        Assert.assertNotNull(produitBusiness.getPage(1, 5));
    }
}
