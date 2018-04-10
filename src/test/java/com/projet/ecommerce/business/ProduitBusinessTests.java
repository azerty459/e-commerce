package com.projet.ecommerce.business;

import com.projet.ecommerce.business.impl.ProduitBusiness;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
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
		Produit produit1 = new Produit();
		produit1.setReferenceProduit("A05A01");
		produit1.setPrixHT(2.1);
		produit1.setDescription("Un livre");
		produit1.setNom("Livre1");
		Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit1);

		Produit retour1 = produitBusiness.addProduit(produit1);
		Assert.assertNotNull(retour1);
		Assert.assertEquals(produit1.getNom(), retour1.getNom());
		Assert.assertEquals(produit1.getDescription(), retour1.getDescription());
		Assert.assertEquals(produit1.getPrixHT(), retour1.getPrixHT(), 0);
		Assert.assertEquals(produit1.getReferenceProduit(), retour1.getReferenceProduit());
		Mockito.verify(produitRepository, Mockito.times(1)).save(produit1);

		Mockito.when(produitRepository.save(Mockito.any())).thenReturn(null);
		Produit retour2 = produitBusiness.addProduit(new Produit());
		Assert.assertNull(retour2);
	}

	@Test
	public void getProduit() {
		List<Produit> produits = new ArrayList<>();
		Mockito.when(produitBusiness.getProduit()).thenReturn(produits);
		Assert.assertEquals(produitBusiness.getProduit().size(), 0);

		Produit produit = new Produit();
		produit.setReferenceProduit("A05A01");
		produit.setPrixHT(2.1);
		produit.setDescription("Un livre");
		produit.setNom("Livre1");
		produits.add(produit);
		Mockito.when(produitBusiness.getProduit()).thenReturn(produits);
		Mockito.verify(produitRepository, Mockito.times(1)).findAll();

		produits = produitBusiness.getProduit();
		Assert.assertEquals(produits.size(), 1);

		Produit retour = produits.get(0);
		Assert.assertEquals(produit.getNom(), retour.getNom());
		Assert.assertEquals(produit.getDescription(), retour.getDescription());
		Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
		Assert.assertEquals(produit.getReferenceProduit(), retour.getReferenceProduit());

		Mockito.verify(produitRepository, Mockito.times(2)).findAll();
	}

	@Test
	public void getProduitByID() {
		Produit produit = new Produit();
		produit.setReferenceProduit("A05A01");
		produit.setPrixHT(2.1);
		produit.setDescription("Un livre");
		produit.setNom("Livre1");

		Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(produit));
		Produit retour1 = produitBusiness.getProduitByID(produit.getReferenceProduit());
		Assert.assertNotNull(retour1);

		Assert.assertEquals(produit.getNom(), retour1.getNom());
		Assert.assertEquals(produit.getDescription(), retour1.getDescription());
		Assert.assertEquals(produit.getPrixHT(), retour1.getPrixHT(), 0);
		Assert.assertEquals(produit.getReferenceProduit(), retour1.getReferenceProduit());
	}

	@Test
	public void getProduitByIDNotFound(){
		Produit produit = produitBusiness.getProduitByID("A04A78");
		Assert.assertNull(produit);
	}

	@Test
	public void deleteProduit() {
		Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(new Produit()));
		Assert.assertTrue(produitBusiness.deleteProduit("A05A01"));
		Mockito.verify(produitRepository, Mockito.times(1)).findById(Mockito.any());
		Mockito.verify(produitRepository, Mockito.times(1)).delete(Mockito.any());
	}

	@Test
	public void deleteProduitNotExists() {
		Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		Assert.assertFalse(produitBusiness.deleteProduit("A05A078"));
		Mockito.verify(produitRepository, Mockito.times(1)).findById(Mockito.any());
		Mockito.verify(produitRepository, Mockito.times(0)).delete(Mockito.any());
	}

	@Test
	public void updateProduit() {
		Produit produit = new Produit();
		produit.setReferenceProduit("A05A01");
		produit.setPrixHT(2.1);
		produit.setDescription("Un livre");
		produit.setNom("Livre1");
		Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(produit));
		Produit retour = produitBusiness.updateProduit(produit);
		Mockito.verify(produitRepository, Mockito.times(1)).findById(Mockito.any());
		Mockito.verify(produitRepository, Mockito.times(1)).save(Mockito.any());
		Assert.assertNotNull(retour);

		Assert.assertEquals(produit.getNom(), retour.getNom());
		Assert.assertEquals(produit.getDescription(), retour.getDescription());
		Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
		Assert.assertEquals(produit.getReferenceProduit(), retour.getReferenceProduit());
	}

	@Test
	public void updateProduitEmpty() {
		Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		Produit retour2 = produitBusiness.updateProduit(new Produit());
		Mockito.verify(produitRepository, Mockito.times(1)).findById(Mockito.any());
		Mockito.verify(produitRepository, Mockito.times(0)).save(Mockito.any());
		Assert.assertNull(retour2);
	}
}*/
