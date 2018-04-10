package com.projet.ecommerce.service;

import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.entity.Categorie;
import com.projet.ecommerce.entity.Produit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProduitBusinessTests {

	private List<Produit> tempList;

	@Mock
	private ProduitBusiness produitBusiness;

	@Test
	public void insertProduit() {
		Produit produit = new Produit();
		produit.setReferenceProduit("A05A87");
		produit.setDescription("joli produit");
		produit.setPrixHT(8.7);
		Mockito.when(produitBusiness.addProduit(produit)).thenReturn(produit);
		produitBusiness.addProduit(produit);
		Mockito.verify(produitBusiness, Mockito.times(1)).addProduit(produit);
	}

	@Test
	public void getProduit() {
		List<Produit> produits = new ArrayList<Produit>();
		Mockito.when(produitBusiness.getProduit()).thenReturn(produits);
		Assert.assertEquals(produitBusiness.getProduit().size(), 0);
		Produit produit = new Produit();
		produit.setReferenceProduit("A05A87");
		produit.setDescription("joli produit");
		produit.setPrixHT(8.7);
		produits.add(produit);
		Mockito.when(produitBusiness.getProduit()).thenReturn(produits);
		Mockito.verify(produitBusiness, Mockito.times(1)).getProduit();
		Assert.assertEquals(produitBusiness.getProduit().size(), 1);
	}

	@Test
	public void getProduitByID() {
		Produit produit = new Produit();
		produit.setReferenceProduit("A05A87");
		produit.setDescription("joli produit");
		produit.setPrixHT(8.7);
		Mockito.when(produitBusiness.getProduitByID("A05A87")).thenReturn(produit);
		Assert.assertEquals(produitBusiness.getProduitByID("A05A87").getReferenceProduit(), "A05A87");
		Assert.assertEquals(produitBusiness.getProduitByID("A05A87").getPrixHT(), 8.7, 0);
		Assert.assertEquals(produitBusiness.getProduitByID("A05A87").getDescription(), "joli produit");
		Assert.assertNull(produitBusiness.getProduitByID("A05A90"));
	}

	@Test
	public void updateProduit() {
		Produit produit = new Produit();
		produit.setReferenceProduit("A05A87");
		produit.setDescription("joli produit");
		produit.setPrixHT(8.7);
		Mockito.when(produitBusiness.updateProduit(produit)).thenReturn(produit);
		Assert.assertNotNull(produitBusiness.updateProduit(produit));
	}

	@Test
	public void getCategorieByIDNotFound(){
		Mockito.when(produitBusiness.getProduitByID("A05A87")).thenReturn(null);
		Assert.assertNull(produitBusiness.getProduitByID("A05A87"));
	}

	@Test
	public void deleteProduit() {
		Mockito.when(produitBusiness.deleteProduit("A05A87")).thenReturn(true);
		Assert.assertTrue(produitBusiness.deleteProduit("A05A87"));
	}
}
