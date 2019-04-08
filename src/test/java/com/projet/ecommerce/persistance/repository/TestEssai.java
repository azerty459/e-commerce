package com.projet.ecommerce.persistance.repository;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.projet.ecommerce.persistance.repository.impl.ProduitRepositoryCustomImpl;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TestEssai {

	
	@Autowired
    private ProduitRepositoryCustomImpl produitRepositoryCustomImpl;
	
	@Test
	public void getSize() {
		int nombre = produitRepositoryCustomImpl.getNomberProduit();
		Assert.assertEquals(10, nombre);
	}
}
