package com.projet.ecommerce.business;

import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
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
import java.util.Optional;

/*
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
		categorie1.setNom("Transport1");
		categorie1.setBorneGauche(1);
		categorie1.setBorneDroit(8);
		categorie1.setLevel(1);
		Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(categorie1);

		Categorie retour1 = categorieBusiness.add(categorie1);
		Assert.assertNotNull(retour1);
		Assert.assertEquals(categorie1.getNom(), retour1.getNom());
        Assert.assertEquals(categorie1.getBorneGauche(), retour1.getBorneGauche());
        Assert.assertEquals(categorie1.getBorneDroit(), retour1.getBorneDroit());
        Assert.assertEquals(categorie1.getLevel(), retour1.getLevel());
		Mockito.verify(categorieRepository, Mockito.times(1)).save(Mockito.any());

		Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(null);
		Categorie retour2 = categorieBusiness.add(new Categorie());
		Assert.assertNull(retour2);
	}

	@Test
	public void getAll() {
		List<Categorie> categories = new ArrayList<>();
		Mockito.when(categorieBusiness.getAll()).thenReturn(categories);
		Assert.assertEquals(categorieBusiness.getAll().size(), 0);

		Categorie categorie = new Categorie();
		categorie.setNom("Transport1");
		categorie.setBorneGauche(1);
		categorie.setBorneDroit(8);
		categorie.setLevel(1);
		categories.add(categorie);
		Mockito.when(categorieBusiness.getAll()).thenReturn(categories);
        Mockito.verify(categorieRepository, Mockito.times(1)).findAll();

        categories = categorieBusiness.getAll();
        Assert.assertEquals(categories.size(), 1);

		Categorie retour = categories.get(0);
		Assert.assertEquals(categorie.getNom(), retour.getNom());
        Assert.assertEquals(categorie.getLevel(), retour.getLevel());
        Assert.assertEquals(categorie.getBorneDroit(), retour.getBorneDroit());
        Assert.assertEquals(categorie.getBorneGauche(), retour.getBorneGauche());

		Mockito.verify(categorieRepository, Mockito.times(2)).findAll();
	}

	@Test
	public void getCategorieByID() {
		Categorie categorie = new Categorie();
		categorie.setNom("Transport3");
		categorie.setBorneGauche(1);
		categorie.setBorneDroit(8);
		categorie.setLevel(1);

		Mockito.when(categorieRepository.findById(Mockito.any())).thenReturn(Optional.of(categorie));
		Categorie retour1 = categorieBusiness.getCategorieByID(categorie.getNom());
		Assert.assertNotNull(retour1);

		Assert.assertEquals(retour1.getNom(), categorie.getNom());
		Assert.assertEquals(retour1.getBorneGauche(), categorie.getBorneGauche());
		Assert.assertEquals(retour1.getBorneDroit(), categorie.getBorneDroit());
		Assert.assertEquals(retour1.getLevel(), categorie.getLevel());
	}

	@Test
	public void getCategorieByIDNotFound(){
		Categorie categorie = categorieBusiness.getCategorieByID("Transport4");
		Mockito.verify(categorieRepository, Mockito.times(1)).findById(Mockito.any());
		Assert.assertNull(categorie);
	}

	@Test
	public void delete() {
		Mockito.when(categorieRepository.findById(Mockito.any())).thenReturn(Optional.of(new Categorie()));
		Assert.assertTrue(categorieBusiness.delete("Fofo"));
		Mockito.verify(categorieRepository, Mockito.times(1)).findById(Mockito.any());
		Mockito.verify(categorieRepository, Mockito.times(1)).delete(Mockito.any());
	}

	@Test
	public void deleteCategorieNotExists() {
		Mockito.when(categorieRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		Assert.assertFalse(categorieBusiness.delete("Fofo"));
		Mockito.verify(categorieRepository, Mockito.times(1)).findById(Mockito.any());
		Mockito.verify(categorieRepository, Mockito.times(0)).delete(Mockito.any());
	}
}
*/