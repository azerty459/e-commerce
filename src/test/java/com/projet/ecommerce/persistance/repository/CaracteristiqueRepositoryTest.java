package com.projet.ecommerce.persistance.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CaracteristiqueRepositoryTest {

	
	private static final Caracteristique TEMP_INSERT = new Caracteristique();
	
	@Autowired
	private CaracteristiqueRepository caracteristiqueRepository;
	
	@Autowired 
	private TypeCaracteristiqueRepository typeCaracteristiqueRepo;
	
	@Autowired
	private ProduitRepository produitRepository;
	
	static {
		//Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
        
        TypeCaracteristique typeCaract = new TypeCaracteristique();
        typeCaract.setIdTypeCaracteristique(1);
        typeCaract.setType("Langue");
        
        Produit p = new Produit();
        p.setReferenceProduit("AAA");
        p.setNom("A");
        p.setDescription("Bla");
        p.setPrixHT(5);
        		
        
        TEMP_INSERT.setIdCaracteristique(1);
        TEMP_INSERT.setProduit(p);
        TEMP_INSERT.setTypeCaracteristique(typeCaract);
        TEMP_INSERT.setValeur("Orange");
	}
	
	@Test
	public void testCaracteristiqueDao() {
		Assert.assertNotNull(typeCaracteristiqueRepo.save(TEMP_INSERT.getTypeCaracteristique()));
		Assert.assertNotNull(produitRepository.save(TEMP_INSERT.getProduit()));
		Assert.assertNotNull(caracteristiqueRepository.save(TEMP_INSERT));
		Caracteristique caracteristique = caracteristiqueRepository.findById(TEMP_INSERT.getIdCaracteristique()).orElse(null);
		Assert.assertNotNull(caracteristique);
		caracteristique.setValeur("Noir");
		Assert.assertNotNull(caracteristiqueRepository.save(caracteristique));
		caracteristique = caracteristiqueRepository.findById(caracteristique.getIdCaracteristique()).orElse(null);
		Assert.assertNotNull(caracteristique);
		Assert.assertEquals(caracteristique.getIdCaracteristique(), TEMP_INSERT.getIdCaracteristique());
		Assert.assertEquals(caracteristique.getValeur(), "Noir");
		caracteristiqueRepository.delete(caracteristique);
		caracteristique = caracteristiqueRepository.findById(caracteristique.getIdCaracteristique()).orElse(null);
		Assert.assertNull(caracteristique);
		
		
	}
	
        
	
}
