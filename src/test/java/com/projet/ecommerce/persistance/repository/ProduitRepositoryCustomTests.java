package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.business.dto.StatistiqueProduitCategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProduitRepositoryCustomTests {

	static {
		//Permet d'écraser la config application.properties par application-test.properties
		System.setProperty("spring.config.location", "classpath:application-test.properties");
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
	public void findAllWithCriteriaByRef() {
		Produit produit = buildProduit(1);
		Categorie categorie = buildCategorie(1, "Livre");
		produit.getCategories().add(categorie);
		produitRepository.save(produit);

		Collection<Produit> produitCollection = produitRepository.findAllWithCriteria(produit.getReferenceProduit(), null);
		Assert.assertEquals(1, produitCollection.size());
		Assert.assertEquals(0, produitRepository.findAllWithCriteria("A05A88", null).size());
	}

	@Test
	@Transactional
	public void findAllWithCriteriaByCat() {
		Produit produit = buildProduit(1);
		Categorie categorie = buildCategorie(1, "Livre");
		produit.getCategories().add(categorie);
		produitRepository.save(produit);

		List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findAllWithCriteria(null, categorie.getNomCategorie()));
		Assert.assertEquals(1, retourProduitCollection.size());
		Assert.assertEquals(produit.getReferenceProduit(), retourProduitCollection.get(0).getReferenceProduit());
		Assert.assertEquals(produit.getNom(), retourProduitCollection.get(0).getNom());
		Assert.assertEquals(produit.getPrixHT(), retourProduitCollection.get(0).getPrixHT(), 0);
		Assert.assertEquals(categorie.getNomCategorie(), retourProduitCollection.get(0).getCategories().get(0).getNomCategorie());

		Assert.assertEquals(0, produitRepository.findAllWithCriteria("Toto", null).size());
	}

	@Test
	public void findByCategories() {
		Produit produit1 = buildProduit(1);
		Produit produit2 = buildProduit(2);
		Produit produit3 = buildProduit(3);
		Categorie categorie1 = buildCategorie(1, "Un");
		Categorie categorie2 = buildCategorie(2, "Deux");
		Categorie categorie3 = buildCategorie(3, "Trois");
		Categorie categorie4 = buildCategorie(4, "Quatre");
		categorie1.setBorneDroit(4);
		categorie2.setBorneGauche(2);
		categorie2.setBorneDroit(3);
		categorie2.setLevel(2);
		categorie3.setBorneGauche(5);
		categorie3.setBorneDroit(6);
		categorie4.setBorneGauche(7);
		categorie4.setBorneGauche(8);
		produit1.getCategories().add(categorie1);
		produit2.getCategories().add(categorie2);
		produit3.getCategories().add(categorie3);
		produitRepository.save(produit1);
		produitRepository.save(produit2);
		produitRepository.save(produit3);

		//Test recuperation des produits lié aux differentes categories
		PageRequest page = PageRequest.of(0, 5);
		Page<Produit> resultat = produitRepository.findByCategories(page, categorie1);
		Assert.assertEquals(2, resultat.getTotalElements());
		Assert.assertEquals(1, resultat.getTotalPages());
		resultat = produitRepository.findByCategories(page, categorie2);
		Assert.assertEquals(1, resultat.getTotalElements());
		Assert.assertEquals(1, resultat.getTotalPages());
		resultat = produitRepository.findByCategories(page, categorie3);
		Assert.assertEquals(1, resultat.getTotalElements());
		Assert.assertEquals(1, resultat.getTotalPages());
		resultat = produitRepository.findByCategories(page, categorie4);
		Assert.assertEquals(0, resultat.getTotalElements());
		Assert.assertEquals(0, resultat.getTotalPages());

		//Test avec plusieurs pages
		page = PageRequest.of(0, 1);
		resultat = produitRepository.findByCategories(page, categorie1);
		Assert.assertEquals(2, resultat.getTotalElements());
		Assert.assertEquals(2, resultat.getTotalPages());
		Assert.assertEquals(1, resultat.getNumberOfElements());
		resultat = produitRepository.findByCategories(page, categorie2);
		Assert.assertEquals(1, resultat.getTotalElements());
		Assert.assertEquals(1, resultat.getTotalPages());
		Assert.assertEquals(1, resultat.getNumberOfElements());

		//Test avec valeur null
		resultat = produitRepository.findByCategories(null, categorie1);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(Page.empty(), resultat);
		resultat = produitRepository.findByCategories(page, null);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(Page.empty(), resultat);
		resultat = produitRepository.findByCategories(null, null);
		Assert.assertNotNull(resultat);
		Assert.assertEquals(Page.empty(), resultat);
	}

	@Test
	public void countProduitsByCategories() {
		List<Categorie> categories = buildCategories();
		Categorie categorie1 = categorieRepository.save(categories.get(0));
		Categorie categorie2 = categorieRepository.save(categories.get(1));
		Categorie categorie3 = categorieRepository.save(categories.get(2));
		Categorie categorie4 = categorieRepository.save(categories.get(3));

		Produit produit1 = buildProduit(1);
		Produit produit2 = buildProduit(2);
		Produit produit3 = buildProduit(3);
		produit1.getCategories().add(categorie1);
		produit2.getCategories().add(categorie1);
		produit3.getCategories().add(categorie4);
		produitRepository.save(produit1);
		produitRepository.save(produit2);
		produitRepository.save(produit3);

		Map<String, Long> expected = new HashMap<>();
		expected.put(categorie1.getNomCategorie(), 2L);
		expected.put(categorie2.getNomCategorie(), 0L);
		expected.put(categorie3.getNomCategorie(), 0L);
		expected.put(categorie4.getNomCategorie(), 1L);

		Collection<StatistiqueProduitCategorieDTO> resultat = produitRepository.countProduitsByCategories();
		Assert.assertEquals(4, resultat.size());
		resultat.forEach(elt -> {
			Assert.assertNotNull(expected.get(elt.getCategorie()));
			Assert.assertEquals(expected.get(elt.getCategorie()).longValue(), elt.getNb());
		});
	}

	@NotNull
	public Produit buildProduit(int id) {
		return buildProduit(id, "Description test");
	}

	@NotNull
	public Produit buildProduit(int id, String desc) {
		Produit produit = new Produit();
		produit.setReferenceProduit("A08A" + id);
		produit.setNom("Test " + id);
		produit.setPrixHT(4.2f);
		produit.setDescription(desc);
		produit.setCategories(new ArrayList<>());
		return produit;
	}

	@NotNull
	public Categorie buildCategorie(int id, String nom) {
		Categorie categorie = new Categorie();
		categorie.setIdCategorie(id);
		categorie.setNomCategorie(nom);
		categorie.setBorneGauche(1);
		categorie.setBorneDroit(2);
		categorie.setLevel(1);
		categorie.setProduits(new ArrayList<>());
		return categorie;
	}

	@NotNull
	public List<Categorie> buildCategories() {
		List<Categorie> retour = new ArrayList<>(4);
		//Top
		Categorie categorie = new Categorie();
		categorie.setNomCategorie("Top");
		categorie.setLevel(1);
		categorie.setBorneDroit(1);
		categorie.setBorneGauche(8);
		retour.add(0, categorie);
		//Top > Mid 1
		categorie = new Categorie();
		categorie.setNomCategorie("Mid 1");
		categorie.setLevel(2);
		categorie.setBorneDroit(2);
		categorie.setBorneGauche(3);
		retour.add(1, categorie);
		//Top > Mid 2
		categorie = new Categorie();
		categorie.setNomCategorie("Mid 2");
		categorie.setLevel(2);
		categorie.setBorneDroit(4);
		categorie.setBorneGauche(7);
		retour.add(2, categorie);
		//Top > Mid 2 > Bot
		categorie = new Categorie();
		categorie.setNomCategorie("Bot");
		categorie.setLevel(3);
		categorie.setBorneDroit(5);
		categorie.setBorneGauche(6);
		retour.add(3, categorie);
		return retour;
	}

}
