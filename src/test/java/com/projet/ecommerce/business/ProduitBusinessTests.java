package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProduitBusinessTests {

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private ProduitBusiness produitBusiness;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void insertProduit() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());
        produit.setCaracteristiques(new ArrayList<>());
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour1 = produitBusiness.add("Test", "Test", "Test", 4.7f);
        Assert.assertNotNull(retour1);
        Assert.assertEquals(produit.getNom(), retour1.getNom());
        Assert.assertEquals(produit.getDescription(), retour1.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour1.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour1.getRef());

        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(null);
        ProduitDTO retour2 = produitBusiness.add("Test", "Test", "Test", 4.7f);
        Assert.assertNull(retour2);
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
    public void getProduitByIDNotFound() {
        ProduitDTO produit = produitBusiness.getByRef("A04A78");
        Assert.assertNull(produit);
    }

    @Test
    public void delete() {
        Assert.assertTrue(produitBusiness.delete("A05A01"));
    }

    @Test
    public void update() {
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
        ProduitDTO retour = produitBusiness.update("Test", "Test", "Test", 4.7f);

        Mockito.verify(produitRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(produitRepository, Mockito.times(1)).save(Mockito.any());
        Assert.assertNotNull(retour);

        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());
    }

    @Test
    public void updateProduitEmpty() {
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        ProduitDTO retour2 = produitBusiness.update("Test", "Test", "Test", 4.7f);
        Mockito.verify(produitRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(produitRepository, Mockito.times(0)).save(Mockito.any());
        Assert.assertNull(retour2);
    }
}