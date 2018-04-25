package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.CategorieRepositoryCustom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategorieBusinessTests {

	@Mock
	private CategorieRepository categorieRepository;

	@Mock
	private CategorieRepositoryCustom categorieRepositoryCustom;

	@InjectMocks
	private CategorieBusiness categorieBusiness;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void insertParent() {
		Categorie categorie1 = new Categorie();
		categorie1.setNomCategorie("Transport");
		categorie1.setBorneGauche(1);
		categorie1.setBorneDroit(4);
		categorie1.setLevel(1);

        Collection<Categorie> categorieCollection = new ArrayList<>();
        categorieCollection.add(categorie1);

		Mockito.when(categorieRepository.findAll()).thenReturn(categorieCollection);
		Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(categorie1);
		CategorieDTO retour1 = categorieBusiness.addParent("Test");

		Assert.assertNotNull(retour1);
		Assert.assertEquals(categorie1.getNomCategorie(), retour1.getNom());
	}

    @Test
    public void insertEnfant() {
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);

        Collection<Categorie> categorieCollection = new ArrayList<>();
        categorieCollection.add(categorie1);

        Mockito.when(categorieRepository.findById(Mockito.any())).thenReturn(Optional.of(categorie1));
        Mockito.when(categorieRepository.findAll()).thenReturn(categorieCollection);
        Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(categorie1);
        CategorieDTO retour1 = categorieBusiness.addEnfant("Test", "Transport");

        Assert.assertNotNull(retour1);
        Assert.assertEquals(categorie1.getNomCategorie(), retour1.getNom());
    }

    @Test
    public void delete() {
        Assert.assertTrue(categorieBusiness.delete("Fofo"));
    }

	@Test
	public void getAll() {
		List<Categorie> categories = new ArrayList<>();
		Mockito.when(categorieRepository.findAll()).thenReturn(categories);
		Assert.assertEquals(categorieBusiness.getAll().size(), 0);

		// Création des catégories et ajout dans la liste.
		Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport1");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);
        categorie1.setProduits(new ArrayList<>());

        Categorie categorie2 = new Categorie();
        categorie2.setNomCategorie("Transport1");
        categorie2.setBorneGauche(2);
        categorie2.setBorneDroit(3);
        categorie2.setLevel(2);
        categorie2.setProduits(new ArrayList<>());

        categories.add(categorie1);
        categories.add(categorie2);

        // Tests
		Mockito.when(categorieRepository.findAll()).thenReturn(categories);
        List<CategorieDTO> categorieDTOList = categorieBusiness.getAll();

        Assert.assertEquals(2, categories.size());

		CategorieDTO retour = categorieDTOList.get(0);
		Assert.assertEquals(categorie1.getNomCategorie(), retour.getNom());
        Assert.assertEquals(categorie2.getNomCategorie(), retour.getSousCategories().get(0).getNom());
	}

	@Test
	public void getCategorie() {
		List<Categorie> categories = new ArrayList<>();
		Mockito.when(categorieRepositoryCustom.findAll(Mockito.anyString())).thenReturn(categories);
		Assert.assertEquals(categorieBusiness.getCategorie("nom").size(), 0);

		// Création des catégories et ajout dans la liste.
		Categorie categorie1 = new Categorie();
		categorie1.setNomCategorie("Transport1");
		categorie1.setBorneGauche(1);
		categorie1.setBorneDroit(4);
		categorie1.setLevel(1);
		categorie1.setProduits(new ArrayList<>());

		Categorie categorie2 = new Categorie();
		categorie2.setNomCategorie("Transport1");
		categorie2.setBorneGauche(2);
		categorie2.setBorneDroit(3);
		categorie2.setLevel(2);
		categorie2.setProduits(new ArrayList<>());

		categories.add(categorie1);
		categories.add(categorie2);

		// Tests
		Mockito.when(categorieRepositoryCustom.findAll(Mockito.anyString())).thenReturn(categories);
		List<CategorieDTO> categorieDTOList = categorieBusiness.getCategorie("nom");

		Assert.assertEquals(2, categories.size());

		CategorieDTO retour = categorieDTOList.get(0);
		Assert.assertEquals(categorie1.getNomCategorie(), retour.getNom());
		Assert.assertEquals(categorie2.getNomCategorie(), retour.getSousCategories().get(0).getNom());
	}

	@Test
	public void getCategorieByNom() {
		Categorie categorie = new Categorie();
		categorie.setNomCategorie("Transport3");
		categorie.setBorneGauche(1);
		categorie.setBorneDroit(8);
		categorie.setLevel(1);
		categorie.setProduits(new ArrayList<>());

		Mockito.when(categorieRepository.findById(Mockito.any())).thenReturn(Optional.of(categorie));
		CategorieDTO retour1 = categorieBusiness.getByNom(categorie.getNomCategorie());
		Assert.assertNotNull(retour1);

		Assert.assertEquals(retour1.getNom(), categorie.getNomCategorie());
	}

	@Test
	public void getCategorieByNomNotFound(){
        Mockito.when(categorieRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		CategorieDTO retour = categorieBusiness.getByNom("Transport4");
		Assert.assertNull(retour);
	}
}