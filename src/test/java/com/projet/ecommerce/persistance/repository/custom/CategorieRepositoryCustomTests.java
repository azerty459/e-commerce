package com.projet.ecommerce.persistance.repository.custom;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategorieRepositoryCustomTests {

	private static final Categorie TEMP_CATEGORIE = new Categorie();
	private static final Categorie TEMP_ENFANT1 = new Categorie();
	private static final Categorie TEMP_ENFANT2 = new Categorie();

	static {
		//Permet d'écraser la config application.properties par application-test.properties
		System.setProperty("spring.config.location", "classpath:application-test.properties");

		// On créer une nouvelle catégorie pour que les tests fonctionnent
		TEMP_CATEGORIE.setNomCategorie("Livre");
		TEMP_CATEGORIE.setBorneGauche(1);
		TEMP_CATEGORIE.setBorneDroit(8);
		TEMP_CATEGORIE.setLevel(1);

		// Création de 2 catégories enfant de Livre
		TEMP_ENFANT1.setNomCategorie("Droit");
		TEMP_ENFANT1.setBorneGauche(4);
		TEMP_ENFANT1.setBorneDroit(7);
		TEMP_ENFANT1.setLevel(2);

		TEMP_ENFANT2.setNomCategorie("Culture");
		TEMP_ENFANT2.setBorneGauche(5);
		TEMP_ENFANT2.setBorneDroit(6);
		TEMP_ENFANT2.setLevel(3);

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
		Collection<Categorie> produitCollection = categorieRepository.findAllWithCriteria(null, false);
		Assert.assertNotNull(produitCollection);
	}

	@Test
	public void findAllWithCriteriaByNom() {
		Collection<Categorie> categorieCollection = categorieRepository.findAllWithCriteria("Livre", false);
		Assert.assertEquals(1, categorieCollection.size());
		Assert.assertEquals(0, categorieRepository.findAllWithCriteria("Toto", false).size());
	}

	@Test
	public void findParents() {

		categorieRepository.save(TEMP_CATEGORIE);
		categorieRepository.save(TEMP_ENFANT1);
		categorieRepository.save(TEMP_ENFANT2);

		// Création du HashMap d'entrée
		HashMap<Integer,Categorie> entree = new HashMap<Integer, Categorie>();
		entree.put(1, TEMP_ENFANT1);
		entree.put(2, TEMP_ENFANT2);

		// Exécution de la méthode findParents
		Collection<Categorie> resultat = categorieRepository.findParents(entree);

		Assert.assertNotNull(resultat);
		Assert.assertEquals(2, resultat.size());

		ArrayList<Categorie> liste = new ArrayList<Categorie>(resultat);

		Assert.assertEquals(liste.get(0).getNomCategorie(), TEMP_ENFANT1.getNomCategorie());
		Assert.assertEquals(liste.get(0).getBorneDroit(), TEMP_ENFANT1.getBorneDroit());
		Assert.assertEquals(liste.get(0).getBorneGauche(), TEMP_ENFANT1.getBorneGauche());
		Assert.assertEquals(liste.get(0).getLevel(), TEMP_ENFANT1.getLevel());

		Assert.assertEquals(liste.get(1).getNomCategorie(), TEMP_ENFANT2.getNomCategorie());
		Assert.assertEquals(liste.get(1).getBorneDroit(), TEMP_ENFANT2.getBorneDroit());
		Assert.assertEquals(liste.get(1).getBorneGauche(), TEMP_ENFANT2.getBorneGauche());
		Assert.assertEquals(liste.get(1).getLevel(), TEMP_ENFANT2.getLevel());

	}

	@Test
	public void findDirectParent() {

		categorieRepository.save(TEMP_CATEGORIE);
		categorieRepository.save(TEMP_ENFANT1);
		categorieRepository.save(TEMP_ENFANT2);

		// Exécution de la méthode
		Categorie resultat = categorieRepository.findDirectParent(TEMP_ENFANT2);

		// Test du résultat
		Assert.assertNotNull(resultat);
		Assert.assertEquals(resultat.getLevel(), 2);
		Assert.assertEquals(resultat.getNomCategorie(), "Droit");

	}
}
