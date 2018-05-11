package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.entrypoint.graphQL.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

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
        produit.setPrixHT(2.1);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>());
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour1 = produitBusiness.add("Test", "Test", "Test", 4.7, null);
        Assert.assertNotNull(retour1);
        Assert.assertEquals(produit.getNom(), retour1.getNom());
        Assert.assertEquals(produit.getDescription(), retour1.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour1.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour1.getRef());

        thrown.expect(GraphQLCustomException.class);
        ProduitDTO retour2 = produitBusiness.add("", "", "dfdfdf", null, null);
        Assert.assertNull(retour2);
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
        ProduitDTO retour = produitBusiness.update("Test", "Test", "Test", 4.7, null, null );
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
        ProduitDTO retour2 = produitBusiness.update("Test", "Test", "Test", 4.7, null, null);
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
        produit.setPrixHT(2.1);
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
        produit.setPrixHT(2.1);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
        produitList.add(produit);

        Mockito.when(produitRepositoryCustom.findAllWithCriteria(Mockito.anyString(), Mockito.anyString())).thenReturn(produitList);
        Mockito.verify(produitRepositoryCustom, Mockito.times(1)).findAllWithCriteria(Mockito.anyString(), Mockito.anyString());
        List<ProduitDTO> produitDTOList = produitBusiness.getAll("ref", "cat");
        Assert.assertEquals(produitDTOList.size(), 1);

        ProduitDTO retour = produitDTOList.get(0);
        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());

        Mockito.verify(produitRepositoryCustom, Mockito.times(2)).findAllWithCriteria(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void getProduitByRef() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1);
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
    public void getByCategorie() {
        Mockito.when(categorieRepository.findCategorieByNomCategorie(Mockito.any())).thenReturn(Optional.of(new Categorie()));
        Mockito.when(categorieRepository.findAll()).thenReturn(new ArrayList<Categorie>());
        List<ProduitDTO> produitDTOList = produitBusiness.getByCategorie("Test");
        Assert.assertNotNull(produitDTOList);
    }

    @Test
    public void getByCategorieNotFound() {
        Mockito.when(categorieRepository.findCategorieByNomCategorie(Mockito.any())).thenReturn(Optional.empty());
        List<ProduitDTO> produitDTOList = produitBusiness.getByCategorie("Test");
        Assert.assertNull(produitDTOList);
    }
}
