package com.projet.ecommerce.persistance.repository;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProduitRepositoryTests2 {

	private static final AvisClient AVIS_CLIENT1_PRODUIT1;
	private static final AvisClient AVIS_CLIENT2_PRODUIT1;
	private static final AvisClient AVIS_CLIENT3_PRODUIT1;
	
	private static final AvisClient AVIS_CLIENT1_PRODUIT2;
	private static final AvisClient AVIS_CLIENT2_PRODUIT2;
	private static final AvisClient AVIS_CLIENT3_PRODUIT3;
	
	private static final Produit TEMP_INSERT1;
	private static final Produit TEMP_INSERT2;
	private static final Produit TEMP_INSERT3;

	
	static {
		//Permet d'écraser la config application.properties par application-test.properties
		System.setProperty("spring.config.location", "classpath:application-test.properties");
		
		//Concerne le produit 1 'TEMP_INSERT1'
		AVIS_CLIENT1_PRODUIT1 = new AvisClient();
		AVIS_CLIENT1_PRODUIT1.setDescription("bien");
		AVIS_CLIENT1_PRODUIT1.setNote(20);

		AVIS_CLIENT2_PRODUIT1 = new AvisClient();
		AVIS_CLIENT2_PRODUIT1.setDescription("assez bien");
		AVIS_CLIENT2_PRODUIT1.setNote(15);

		AVIS_CLIENT3_PRODUIT1 = new AvisClient();
		AVIS_CLIENT3_PRODUIT1.setDescription("null");
		AVIS_CLIENT3_PRODUIT1.setNote(5);
		
       
		TEMP_INSERT1 = new Produit();
		TEMP_INSERT1.setReferenceProduit("A05A87");
		TEMP_INSERT1.setNom("lampe");
		TEMP_INSERT1.setPrixHT(8.7f);
		TEMP_INSERT1.setDescription("joli produit");
		TEMP_INSERT1.setCategories(new ArrayList<>());
		TEMP_INSERT1.setAvisClients(new ArrayList<>());
		AVIS_CLIENT1_PRODUIT1.setProduit(TEMP_INSERT1);
		AVIS_CLIENT2_PRODUIT1.setProduit(TEMP_INSERT1);
		AVIS_CLIENT3_PRODUIT1.setProduit(TEMP_INSERT1);
		TEMP_INSERT1.getAvisClients().add(AVIS_CLIENT1_PRODUIT1);
		TEMP_INSERT1.getAvisClients().add(AVIS_CLIENT2_PRODUIT1);
		TEMP_INSERT1.getAvisClients().add(AVIS_CLIENT3_PRODUIT1);
		
		//Concerne le produit 2 'TEMP_INSERT2'
		AVIS_CLIENT1_PRODUIT2 = new AvisClient();
		AVIS_CLIENT1_PRODUIT2.setDescription("bien");
		AVIS_CLIENT1_PRODUIT2.setNote(20);

		AVIS_CLIENT2_PRODUIT2 = new AvisClient();
		AVIS_CLIENT2_PRODUIT2.setDescription("assez bien");
		AVIS_CLIENT2_PRODUIT2.setNote(15);

		AVIS_CLIENT3_PRODUIT3 = new AvisClient();
		AVIS_CLIENT3_PRODUIT3.setDescription("null");
		AVIS_CLIENT3_PRODUIT3.setNote(5);

		TEMP_INSERT2 = new Produit();
		TEMP_INSERT2.setReferenceProduit("A05A88");
		TEMP_INSERT2.setNom("rampe");
		TEMP_INSERT2.setPrixHT(8.7f);
		TEMP_INSERT2.setDescription("joli produit encore");
		TEMP_INSERT2.setCategories(new ArrayList<>());
		TEMP_INSERT2.setAvisClients(new ArrayList<>());
		AVIS_CLIENT1_PRODUIT2.setProduit(TEMP_INSERT2);
		AVIS_CLIENT2_PRODUIT2.setProduit(TEMP_INSERT2);
		AVIS_CLIENT3_PRODUIT3.setProduit(TEMP_INSERT2);
		TEMP_INSERT2.getAvisClients().add(AVIS_CLIENT1_PRODUIT2);
		TEMP_INSERT2.getAvisClients().add(AVIS_CLIENT2_PRODUIT2);
		TEMP_INSERT2.getAvisClients().add(AVIS_CLIENT3_PRODUIT3);
		

		//concerne le produit 3 'TEMP_INSERT3'
		TEMP_INSERT3 = new Produit();
		TEMP_INSERT3.setReferenceProduit("A05A89");
		TEMP_INSERT3.setNom("micro");
		TEMP_INSERT3.setPrixHT(8.7f);
		TEMP_INSERT3.setDescription("encore plus joli produit");
		TEMP_INSERT3.setCategories(new ArrayList<>());
		TEMP_INSERT3.setAvisClients(new ArrayList<>());    
	}

	@Autowired
	private ProduitRepository produitRepository;
	
	@Autowired
	private CategorieRepository categorieRepository;
	

	@Test
	public void FindProductWithCriteriaByNameOrAndValueInProductName() {

		Collection<Produit> produitList = produitRepository.findProductWithCriteria(-1, -1, TEMP_INSERT1.getNom(), "amp", null);
		Assert.assertEquals(1,produitList.size());

		produitList = produitRepository.findProductWithCriteria(-1, -1, TEMP_INSERT1.getNom(), null, null);
		Assert.assertEquals(1,produitList.size());

		produitList = produitRepository.findProductWithCriteria(-1, -1, null, "amp", null);
		Assert.assertEquals(2,produitList.size());

		produitList = produitRepository.findProductWithCriteria(-1, -1, null, null, null);
		Assert.assertEquals(3,produitList.size());
	}

	@Test
	public void FindProductWithCriteriaByValueInProductName() {

		Collection<Produit> produitList = produitRepository.findProductWithCriteria(-1, -1, null, "amp", null);
		Assert.assertEquals(2,produitList.size());
	}

	@Test
	public void FindProductWithCriteriaByNameAndValueInProductName() {

		Collection<Produit> produitList = produitRepository.findProductWithCriteria(-1, -1, TEMP_INSERT1.getNom(), "amp", null);
		Assert.assertEquals(1,produitList.size());
	}

	@Test
	public void FindProductWithCriteriaByMoyenne() {

		Collection<Produit> produitList = produitRepository.findProductWithCriteria(0, 20, "lampe", "amp", null);
		Assert.assertEquals(1,produitList.size());
		
		produitList = produitRepository.findProductWithCriteria(0, 20, null, "amp", null);
		Assert.assertEquals(2,produitList.size());
		
		produitList = produitRepository.findProductWithCriteria(-1, -1, "lampe", "amp", null);
		Assert.assertEquals(1,produitList.size());
		
		produitList = produitRepository.findProductWithCriteria(-1, -1, null, null, null);
		Assert.assertEquals(3,produitList.size());
	
		produitList = produitRepository.findProductWithCriteria(0, 20, null, null, null);
		Assert.assertEquals(2,produitList.size());
	}
	
	@Test
	public void FindProductWithCriteriaWithCategorie() {
		Categorie TEMP_CATEGORIE1;
		TEMP_CATEGORIE1 = new Categorie();
        TEMP_CATEGORIE1.setNomCategorie("Luminaire");
        TEMP_CATEGORIE1.setBorneGauche(10);
        TEMP_CATEGORIE1.setBorneDroit(20);
        TEMP_CATEGORIE1.setLevel(0);
        categorieRepository.save(TEMP_CATEGORIE1);
        
        Produit produit = new Produit();
        produit.setReferenceProduit("aaaa");
        produit.setNom("lampe");
        produit.setPrixHT(8.7f);
		produit.setDescription("encore plus joli produit");
		produit.setCategories(new ArrayList<>());
		produit.getCategories().add(TEMP_CATEGORIE1);
		produit.setAvisClients(new ArrayList<>()); 
		produitRepository.save(produit);
        
		Collection<Produit> produitList = produitRepository.findProductWithCriteria(-1, -1, null, null, TEMP_CATEGORIE1);
		Assert.assertEquals(1,produitList.size());
		
		produitList = produitRepository.findProductWithCriteria(-1, -1, null, null, null);
		Assert.assertEquals(4,produitList.size());
		
		produitList = produitRepository.findProductWithCriteria(0, 20, null, "amp", null);
		Assert.assertEquals(2,produitList.size());
		
		produitList = produitRepository.findProductWithCriteria(-1, -1, null, "amp", null);
		Assert.assertEquals(3,produitList.size());
	}
	

	
	@Test
	public void testFinal() {
		
	}

	@Before
	public void insertProduit() {
        produitRepository.save(TEMP_INSERT1);
		produitRepository.save(TEMP_INSERT2);
		produitRepository.save(TEMP_INSERT3);	
	}
}
