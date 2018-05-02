package com.projet.ecommerce.persistance.repository.custom;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProduitRepositoryCustomTests {

	private static final Produit TEMP_INSERT = new Produit();
	private static final Categorie TEMP_CATEGORIE = new Categorie();

	static {
		//Permet d'écraser la config application.properties par application-test.properties
		System.setProperty("spring.config.location", "classpath:application-test.properties");
		TEMP_INSERT.setReferenceProduit("A05A87");
		TEMP_INSERT.setPrixHT(8.7);
		TEMP_INSERT.setDescription("joli produit");
		TEMP_INSERT.setCategories(new ArrayList<>());

		// On créer une nouvelle catégorie pour que les tests fonctionne
		TEMP_CATEGORIE.setNomCategorie("Livre");
		TEMP_CATEGORIE.setBorneGauche(1);
		TEMP_CATEGORIE.setBorneDroit(2);
		TEMP_CATEGORIE.setLevel(1);
	}

	@Before
	public void insertProduit(){
		categorieRepository.save(TEMP_CATEGORIE);

		// On insert la catégorie créer dans le produit
		Collection<Categorie> categorieCollection = TEMP_INSERT.getCategories();
		categorieCollection.add(TEMP_CATEGORIE);
		TEMP_INSERT.setCategories(new ArrayList<>(categorieCollection));
		produitRepository.save(TEMP_INSERT);
	}

	@Autowired
	private ProduitRepository produitRepository;

	@Autowired
	private CategorieRepository categorieRepository;

	@Test
	// Je teste que la méthode si l'on l'appelle avec null dans les deux paramètres, elle retourne une collection de produit.
	public void findAllWithCriteriaByNull() {
		Collection<Produit> produitCollection = produitRepository.findAllWithCriteria(null, null);
		Assert.assertNotNull(produitCollection);
	}

	@Test
	public void findAllWithCriteriaByReff() {
		Collection<Produit> produitCollection = produitRepository.findAllWithCriteria("A05A87", null);
		Assert.assertEquals(1, produitCollection.size());
		Assert.assertEquals(0, produitRepository.findAllWithCriteria("A05A88", null).size());
	}

	@Test
	@Transactional // Permet de charger le lazy loading getCategories du produit
	public void findAllWithCriteriaByCat() {
		List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findAllWithCriteria(null, "Livre"));
		Assert.assertEquals(1, retourProduitCollection.size());
		Assert.assertEquals(retourProduitCollection.get(0).getReferenceProduit(), TEMP_INSERT.getReferenceProduit());
		Assert.assertEquals(retourProduitCollection.get(0).getNom(), TEMP_INSERT.getNom());
		Assert.assertEquals(retourProduitCollection.get(0).getPrixHT(), TEMP_INSERT.getPrixHT(), 0);
		Assert.assertEquals(retourProduitCollection.get(0).getCategories().get(0).getNomCategorie(), TEMP_CATEGORIE.getNomCategorie());

		Assert.assertEquals(0, produitRepository.findAllWithCriteria("Toto", null).size());
	}
}
