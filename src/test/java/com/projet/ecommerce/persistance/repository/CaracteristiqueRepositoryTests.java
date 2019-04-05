package com.projet.ecommerce.persistance.repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import org.junit.Test;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CaracteristiqueRepositoryTests {

	@Autowired
	ProduitRepository produitRep;
	
	@Test
	public void testProduitDoubleType() {
		produitRep.findAll();

		Produit p = new Produit();
		p.setReferenceProduit("0");

		TypeCaracteristique tc = new TypeCaracteristique();
		tc.setIdTypeCarac(0);
		tc.setLibelle("libelle0");
		TypeCaracteristique tc2 = new TypeCaracteristique();
		tc2.setIdTypeCarac(2);
		tc2.setLibelle("libelle2");
		
//		Caracteristique c1 = new Caracteristique();
//		c1.setProd(p);
//		c1.setTypeC(tc);
//		c1.setValeur("2");
//		
//		Caracteristique c2 = new Caracteristique();
//		c2.setProd(p);
//		c2.setTypeC(tc2);
//		c2.setValeur("0");
//		
//		p.setCaracs(new ArrayList<Caracteristique>());
//		p.getCaracs().add(c1);
//		
//		produitRep.save(p);
//		p.getCaracs().add(c2);
//		produitRep.save(p);
		
	}
}
