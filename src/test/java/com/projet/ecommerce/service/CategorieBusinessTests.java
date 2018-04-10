package com.projet.ecommerce.service;

import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.entity.Categorie;
import com.projet.ecommerce.repository.CategorieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategorieBusinessTests {

	@Mock
	private CategorieRepository categorieRepository;

	@InjectMocks
	private CategorieBusiness categorieBusiness;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void insertCategorie() {
		Categorie categorie1 = new Categorie();
		categorie1.setNomCategorie("Transport1");
		categorie1.setBorneGauche(1);
		categorie1.setBorneDroit(8);
		categorie1.setLevel(1);
		Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(categorie1);

		Categorie retour1 = categorieBusiness.addCategorie(categorie1);
		Assert.assertNotNull(retour1);
		Assert.assertEquals(categorie1.getNomCategorie(), retour1.getNomCategorie());
        Assert.assertEquals(categorie1.getBorneGauche(), retour1.getBorneGauche());
        Assert.assertEquals(categorie1.getBorneDroit(), retour1.getBorneDroit());
        Assert.assertEquals(categorie1.getLevel(), retour1.getLevel());
		Mockito.verify(categorieRepository, Mockito.times(1)).save(Mockito.any());

		Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(null);
		Categorie categorie2 = new Categorie();
		Categorie retour2 = categorieBusiness.addCategorie(categorie2);
		Assert.assertNull(retour2);
	}

	@Test
	public void getCategorie() {
		List<Categorie> categories = new ArrayList<>();
		Mockito.when(categorieBusiness.getCategorie()).thenReturn(categories);
		Assert.assertEquals(categorieBusiness.getCategorie().size(), 0);

		Categorie categorie = new Categorie();
		categorie.setNomCategorie("Transport1");
		categorie.setBorneGauche(1);
		categorie.setBorneDroit(8);
		categorie.setLevel(1);
		categories.add(categorie);
		Mockito.when(categorieBusiness.getCategorie()).thenReturn(categories);
        Mockito.verify(categorieRepository, Mockito.times(1)).findAll();

        categories = categorieBusiness.getCategorie();
        Assert.assertEquals(categories.size(), 1);

		Categorie retour = categories.get(0);
		Assert.assertEquals(categorie.getNomCategorie(), retour.getNomCategorie());
        Assert.assertEquals(categorie.getLevel(), retour.getLevel());
        Assert.assertEquals(categorie.getBorneDroit(), retour.getBorneDroit());
        Assert.assertEquals(categorie.getBorneGauche(), retour.getBorneGauche());

		Mockito.verify(categorieRepository, Mockito.times(2)).findAll();
	}

	@Test
	public void getCategorieByID() {
		Categorie categorie = new Categorie();
		categorie.setNomCategorie("Transport3");
		categorie.setBorneGauche(1);
		categorie.setBorneDroit(8);
		categorie.setLevel(1);

        Mockito.when(categorieBusiness.getCategorieByID("Transport3")).thenReturn(categorie);
		Categorie retour = categorieBusiness.getCategorieByID(categorie.getNomCategorie());
		Assert.assertNotNull(retour);

		Assert.assertEquals(retour.getNomCategorie(), categorie.getNomCategorie());
		Assert.assertEquals(retour.getBorneGauche(), categorie.getBorneGauche());
		Assert.assertEquals(retour.getBorneDroit(), categorie.getBorneDroit());
		Assert.assertEquals(retour.getLevel(), categorie.getLevel());

		Assert.assertNull(categorieBusiness.getCategorieByID("Transport15"));
	}

	@Test
	public void getCategorieByIDNotFound(){
		Categorie categorie = categorieBusiness.getCategorieByID("Transport4");
		Assert.assertNull(categorie);
	}

	@Test
	public void deleteCategorie() {
		Assert.assertTrue(categorieBusiness.deleteCategorie("Transport3"));
	}
}
