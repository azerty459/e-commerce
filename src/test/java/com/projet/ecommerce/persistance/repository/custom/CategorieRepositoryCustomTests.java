package com.projet.ecommerce.persistance.repository.custom;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategorieRepositoryCustomTests {

	private static final Categorie TEMP_CATEGORIE = new Categorie();

	static {
		//Permet d'écraser la config application.properties par application-test.properties
		System.setProperty("spring.config.location", "classpath:application-test.properties");

		// On créer une nouvelle catégorie pour que les tests fonctionne
		TEMP_CATEGORIE.setNomCategorie("Livre");
		TEMP_CATEGORIE.setBorneGauche(1);
		TEMP_CATEGORIE.setBorneDroit(2);
		TEMP_CATEGORIE.setLevel(1);
	}

	@Before
	public void insertProduit(){
		categorieRepository.save(TEMP_CATEGORIE);
	}

	@Autowired
	private CategorieRepository categorieRepository;

	@Test
	// Je teste que la méthode si l'on l'appelle avec null dans les deux paramètres, elle retourne une collection de produit.
	public void findAllWithCriteriaByNull() {
		Collection<Categorie> produitCollection = categorieRepository.findAllWithCriteria(null);
		Assert.assertNotNull(produitCollection);
	}

	@Test
	public void findAllWithCriteriaByNom() {
		Collection<Categorie> categorieCollection = categorieRepository.findAllWithCriteria("Livre");
		Assert.assertEquals(1, categorieCollection.size());
		Assert.assertEquals(0, categorieRepository.findAllWithCriteria("Toto").size());
	}
}
