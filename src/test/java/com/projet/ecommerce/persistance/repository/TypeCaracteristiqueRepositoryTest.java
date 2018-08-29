package com.projet.ecommerce.persistance.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TypeCaracteristiqueRepositoryTest {

	
	private static final TypeCaracteristique TEMP_INSERT = new TypeCaracteristique();
	private static final TypeCaracteristique TEMP_UPDATE = new TypeCaracteristique();
	
	static {
		//Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
        
        TEMP_INSERT.setIdTypeCaracteristique(1);
        TEMP_INSERT.setType("Langue");
        
        TEMP_UPDATE.setIdTypeCaracteristique(2);
        TEMP_UPDATE.setType("Format");
	}
	
	@Autowired 
	private TypeCaracteristiqueRepository typeCaracteristiqueRepo;
	
	
	@Test
	public void insertTypeCaracteristique() {
		Assert.assertNotNull(typeCaracteristiqueRepo.save(TEMP_INSERT));
		TypeCaracteristique typeCara = typeCaracteristiqueRepo.findById(TEMP_INSERT.getIdTypeCaracteristique()).orElse(null);
		Assert.assertNotNull(typeCara);
		
	}
	
	@Test
	public void updateTypeCaracteristique() {
		Assert.assertNotNull(typeCaracteristiqueRepo.save(TEMP_UPDATE));
		TypeCaracteristique typeCara = typeCaracteristiqueRepo.findById(TEMP_UPDATE.getIdTypeCaracteristique()).orElse(null);
		Assert.assertNotNull(typeCara);
		typeCara.setType("Couleur");
		Assert.assertNotNull(typeCaracteristiqueRepo.save(typeCara));
		typeCara = typeCaracteristiqueRepo.findById(TEMP_UPDATE.getIdTypeCaracteristique()).orElse(null);
		Assert.assertEquals(typeCara.getType(), "Couleur");
		Assert.assertEquals(typeCara.getIdTypeCaracteristique(),TEMP_UPDATE.getIdTypeCaracteristique());
		
	}
	
	@Test
	public void deleteTypeCaracteristique() {
		Assert.assertNotNull(typeCaracteristiqueRepo.save(TEMP_INSERT));
		TypeCaracteristique typeCara = typeCaracteristiqueRepo.findById(TEMP_INSERT.getIdTypeCaracteristique()).orElse(null);
		Assert.assertNotNull(typeCara);
		typeCaracteristiqueRepo.delete(typeCara);
		typeCara = typeCaracteristiqueRepo.findById(TEMP_INSERT.getIdTypeCaracteristique()).orElse(null);
		Assert.assertNull(typeCara);
	}
	
	@Test
	public void getAllTypeCaracteristique() {
		Assert.assertNotNull(typeCaracteristiqueRepo.save(TEMP_INSERT));
		TypeCaracteristique typeCara = typeCaracteristiqueRepo.findById(TEMP_INSERT.getIdTypeCaracteristique()).orElse(null);
		Assert.assertNotNull(typeCara);
		
		Assert.assertNotNull(typeCaracteristiqueRepo.save(TEMP_UPDATE));
		typeCara = typeCaracteristiqueRepo.findById(TEMP_INSERT.getIdTypeCaracteristique()).orElse(null);
		Assert.assertNotNull(typeCara);
		Collection<TypeCaracteristique> lstTypeCara = typeCaracteristiqueRepo.findAll();
		Assert.assertNotNull(lstTypeCara);
		Assert.assertEquals(2, lstTypeCara.size());
		
	}
	
}
