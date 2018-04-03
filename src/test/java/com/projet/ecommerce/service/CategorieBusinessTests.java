package com.projet.ecommerce.service;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.entity.Categorie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategorieBusinessTests {

	private List<Categorie> tempList;

	@Mock
	private ICategorieBusiness categorieBusiness;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void insertCategorie() {
		Categorie categorie = new Categorie();
		categorie.setNomCategorie("Transport1");
		categorie.setBorneGauche(1);
		categorie.setBorneDroit(8);
		Mockito.when(categorieBusiness.addCategorie("Transport1", 1 ,8, 1)).thenReturn(categorie);
		categorieBusiness.addCategorie("Transport1", 1 ,8, 1);
		Mockito.verify(categorieBusiness, Mockito.times(1)).addCategorie("Transport1", 1 ,8, 1);
	}

	@Test
	public void getCategorie() {
		List<Categorie> categories = new ArrayList<Categorie>();
		Mockito.when(categorieBusiness.getCategorie()).thenReturn(categories);
		Assert.assertEquals(categorieBusiness.getCategorie().size(), 0);
		Categorie categorie = new Categorie();
		categorie.setNomCategorie("Transport1");
		categorie.setBorneGauche(1);
		categorie.setBorneDroit(8);
		categories.add(categorie);
		Mockito.when(categorieBusiness.getCategorie()).thenReturn(categories);
		Mockito.verify(categorieBusiness, Mockito.times(1)).getCategorie();
		Assert.assertEquals(categorieBusiness.getCategorie().size(), 1);
	}

	@Test
	public void getCategorieByID() {
		Categorie categorie = new Categorie();
		categorie.setNomCategorie("Transport3");
		categorie.setBorneGauche(1);
		categorie.setBorneDroit(8);
		Mockito.when(categorieBusiness.getCategorieByID("Transport3")).thenReturn(categorie);
		Assert.assertEquals(categorieBusiness.getCategorieByID("Transport3").getNomCategorie(), categorie.getNomCategorie());
		Assert.assertEquals(categorieBusiness.getCategorieByID("Transport3").getBorneGauche(), categorie.getBorneGauche());
		Assert.assertEquals(categorieBusiness.getCategorieByID("Transport3").getBorneDroit(), categorie.getBorneDroit());
		Assert.assertNull(categorieBusiness.getCategorieByID("Transport15"));
	}

	@Test
	public void getCategorieByIDNotFound(){
		Mockito.when(categorieBusiness.getCategorieByID("Transport3")).thenReturn(null);
		Assert.assertNull(categorieBusiness.getCategorieByID("Transport3"));
	}

	@Test
	public void deleteCategorie() {
		Mockito.when(categorieBusiness.deleteCategorie("Transport3")).thenReturn(true);
		Assert.assertTrue(categorieBusiness.deleteCategorie("Transport3"));
	}
}
