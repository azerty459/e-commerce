package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ecommerce.persistance.entity.Caracteristique;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CaracteristiqueRepositoryTests {

	private static final Caracteristique TEMP_INSERT = new Caracteristique();
	private static final Caracteristique TEMP_INSERT2 = new Caracteristique();
	private static final Caracteristique TEMP_DELETE = new Caracteristique();
	private static final Caracteristique TEMP_UPDATE = new Caracteristique();
	private static final Caracteristique TEMP_GET = new Caracteristique();
	
	static {
		
		//Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
	
        TEMP_INSERT.setLibelle("couleur");
        
        TEMP_INSERT2.setLibelle("couleur2");
        
        TEMP_DELETE.setLibelle("poids");
        
        TEMP_UPDATE.setLibelle("reference");
        
        TEMP_GET.setLibelle("date");
	}
	
	@Autowired
	private CaracteristiqueRepository caracteristiqueRepository;
	
	@Test
	public void insertCaracteristique() {
		Assert.assertNotNull(caracteristiqueRepository.save(TEMP_INSERT));
		Caracteristique temp = caracteristiqueRepository.findById(TEMP_INSERT.getIdCaracteristique()).orElse(null);
		Assert.assertNotNull(temp);
	}
	
	@Test
	public void getCaracteristique() {
		caracteristiqueRepository.deleteAll();
		Collection<Caracteristique> caracteristiqueCollection =  (Collection<Caracteristique>) caracteristiqueRepository.findAll();
		Assert.assertEquals(0, caracteristiqueCollection.size());
		
		caracteristiqueRepository.save(TEMP_INSERT);
		caracteristiqueRepository.save(TEMP_INSERT2);
		caracteristiqueCollection = (Collection<Caracteristique>) caracteristiqueRepository.findAll();
		Assert.assertEquals(2, caracteristiqueCollection.size());
	}
	
	@Test
	public void updateCaracteristique() {
		caracteristiqueRepository.deleteAll();
		caracteristiqueRepository.save(TEMP_UPDATE);
		
		Caracteristique caracteristique = caracteristiqueRepository.findById(TEMP_UPDATE.getIdCaracteristique()).orElse(null);
		Assert.assertEquals(caracteristique.getLibelle(), TEMP_UPDATE.getLibelle());
		
		TEMP_UPDATE.setLibelle("reference2");
		Assert.assertNotNull(caracteristiqueRepository.save(TEMP_UPDATE));
		caracteristique = caracteristiqueRepository.findById(TEMP_UPDATE.getIdCaracteristique()).orElse(null);
		Assert.assertEquals("reference2", caracteristique.getLibelle());
	}
	
	@Test
	public void deleteCaracteristique() {
		caracteristiqueRepository.deleteAll();
		Assert.assertNotNull(caracteristiqueRepository.save(TEMP_DELETE));
		caracteristiqueRepository.delete(TEMP_DELETE);
		Assert.assertFalse(caracteristiqueRepository.findById(TEMP_DELETE.getIdCaracteristique()).isPresent());
		
		
	}
	
	


}
