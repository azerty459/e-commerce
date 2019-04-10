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

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProduitRepositoryTests2 {

	private static final Produit TEMP_INSERT1;
	private static final Produit TEMP_INSERT2;
	private static final Produit TEMP_INSERT3;
	
	static {
		//Permet d'écraser la config application.properties par application-test.properties
		System.setProperty("spring.config.location", "classpath:application-test.properties");

		TEMP_INSERT1 = new Produit();
		TEMP_INSERT1.setReferenceProduit("A05A87");
		TEMP_INSERT1.setNom("lampe");
        TEMP_INSERT1.setPrixHT(8.7f);
        TEMP_INSERT1.setDescription("joli produit");
        TEMP_INSERT1.setCategories(new ArrayList<>());
		
		TEMP_INSERT2 = new Produit();
		TEMP_INSERT2.setReferenceProduit("A05A88");
		TEMP_INSERT2.setNom("rampe");
        TEMP_INSERT2.setPrixHT(8.7f);
        TEMP_INSERT2.setDescription("joli produit encore");
        TEMP_INSERT2.setCategories(new ArrayList<>());
		
		TEMP_INSERT3 = new Produit();
		TEMP_INSERT3.setReferenceProduit("A05A89");
		TEMP_INSERT3.setNom("micro");
        TEMP_INSERT3.setPrixHT(8.7f);
        TEMP_INSERT3.setDescription("encore plus joli produit");
        TEMP_INSERT3.setCategories(new ArrayList<>());
	}

	@Autowired
	private ProduitRepository produitRepository;


	@Test
	public void FindProductWithCriteriaByName() {

		Collection<Produit> produitList = produitRepository.findProductWithCriteria(0, 0, TEMP_INSERT1.getNom(), null, null);
		Assert.assertEquals(1,produitList.size());
	}
	
	@Test
	public void FindProductWithCriteriaByValueInProductName() {
		
		Collection<Produit> produitList = produitRepository.findProductWithCriteria(0, 0, null, "amp", null);
		Assert.assertEquals(2,produitList.size());
	}
	
	@Test
	public void FindProductWithCriteriaByNameAndValueInProductName() {
		
		Collection<Produit> produitList = produitRepository.findProductWithCriteria(0, 0, TEMP_INSERT1.getNom(), "amp", null);
		Assert.assertEquals(1,produitList.size());
	}

	@Before
	public void insertProduit() {
		produitRepository.save(TEMP_INSERT1);
		produitRepository.save(TEMP_INSERT2);
		produitRepository.save(TEMP_INSERT3);
	}
}
