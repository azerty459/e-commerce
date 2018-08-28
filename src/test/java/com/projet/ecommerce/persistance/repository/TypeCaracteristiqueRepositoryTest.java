package com.projet.ecommerce.persistance.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TypeCaracteristiqueRepositoryTest {

	
	private static final TypeCaracteristique TEMP_INSERT = new TypeCaracteristique();
	private static final TypeCaracteristique TEMP_GET = new TypeCaracteristique();
	
	static {
		//Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
        
        TEMP_INSERT.setIdTypeCaracteristique(1);
        TEMP_INSERT.setType("Langue");
        
        TEMP_GET.setIdTypeCaracteristique(2);
        TEMP_INSERT.setType("Format");
	}
	
	@Autowired 
	private TypeCaracteristiqueRepository typeCaracteristiqueRepo;
	
	
	@Test
	public void insertTypeCaracteristique() {
		Assert.assertNotNull(typeCaracteristiqueRepo.save(TEMP_INSERT));
		TypeCaracteristique typeCara = typeCaracteristiqueRepo.findById(TEMP_INSERT.getIdTypeCaracteristique()).orElse(null);
		Assert.assertNotNull(typeCara);
		
	}
	
/*	@Test
	public void getTypeCaracteristique() {
		TypeCaracteristique typeCara = typeCaracteristiqueRepo.find
	}*/
	
}
