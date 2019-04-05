package com.projet.ecommerce.persistance.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CaracteristiqueRepositoryTests {
	
	@Autowired
	private CaracteristiqueRepository caracteristiqueRepository;
	@Autowired
	private TypeCaracteristiqueRepository typeCaracteristiqueRepository;
	@Autowired
	private ProduitRepository produitRepository;
	
	private static final Caracteristique CARAC_1 = new Caracteristique();
	private static final Caracteristique CARAC_2 = new Caracteristique();
	private static final Caracteristique CARAC_3 = new Caracteristique();
	
	private static final TypeCaracteristique TYPECARAC_1 = new TypeCaracteristique();
	private static final TypeCaracteristique TYPECARAC_2 = new TypeCaracteristique();
	
	private static final Produit PROD_1 = new Produit();
	private static final Produit PROD_2 = new Produit();
	
    private static final String VALEUR_1 = "15 cm" ;
    private static final String VALEUR_2 = "5 cm" ;
	
	static {
		System.setProperty("spring.config.location", "classpath:application-test.properties");
		
		TYPECARAC_1.setTypeCaracteristique("Longueur");
		TYPECARAC_2.setTypeCaracteristique("Largeur");
		
		PROD_1.setNom("A");
		PROD_1.setReferenceProduit("A05A87");
		PROD_1.setPrixHT(1.1f);
		PROD_1.setDescription("joli produit 1");
		
		PROD_1.setNom("B");
		PROD_2.setReferenceProduit("A05A88");
		PROD_2.setPrixHT(2.2f);
		PROD_2.setDescription("joli produit 2");
		
		CARAC_1.setProduit(PROD_1);
		CARAC_1.setTypeCaracteristique(TYPECARAC_1);
		CARAC_1.setValeurCaracteristique(VALEUR_1);
		
		CARAC_2.setProduit(PROD_1);
		CARAC_2.setTypeCaracteristique(TYPECARAC_2);
		CARAC_2.setValeurCaracteristique(VALEUR_2);
		
		CARAC_3.setProduit(PROD_2);
		CARAC_3.setTypeCaracteristique(TYPECARAC_1);
		CARAC_1.setValeurCaracteristique(VALEUR_1);
	}
	
	@Before
	public void initialisationDesDonnéesCaracteristique() {
		produitRepository.save(PROD_1);
		produitRepository.save(PROD_2);
		
		typeCaracteristiqueRepository.save(TYPECARAC_1);
		typeCaracteristiqueRepository.save(TYPECARAC_2);
		
		caracteristiqueRepository.save(CARAC_1);
		caracteristiqueRepository.save(CARAC_2);
		caracteristiqueRepository.save(CARAC_3);
	}
	
	@Test
	public void finAll() {
		
	}
	
	@After
	public void razDesDonnéesCaracteristique() {
		produitRepository.deleteAll();
		caracteristiqueRepository.deleteAll();
		typeCaracteristiqueRepository.deleteAll();
	}
}
