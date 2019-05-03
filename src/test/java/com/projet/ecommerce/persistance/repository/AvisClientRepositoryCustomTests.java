package com.projet.ecommerce.persistance.repository;

import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.impl.AvisClientRepositoryCustomImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AvisClientRepositoryCustomTests {

	private static final Produit PRODUIT1;
	private static final Produit PRODUIT2;

	private static final AvisClient AVIS_CLIENT1_PRODUIT1;
	private static final AvisClient AVIS_CLIENT2_PRODUIT1;
	private static final AvisClient AVIS_CLIENT3_PRODUIT1;

	private static final AvisClient AVIS_CLIENT1_PRODUIT2;
	private static final AvisClient AVIS_CLIENT2_PRODUIT2;
	private static final AvisClient AVIS_CLIENT3_PRODUIT2;

	static {

		//Permet d'écraser la config application.properties par application-test.properties
		System.setProperty("spring.config.location", "classpath:application-test.properties");

		//Concerne le produit 1 'PRODUIT1'
		AVIS_CLIENT1_PRODUIT1 = new AvisClient();
		AVIS_CLIENT1_PRODUIT1.setDescription("bien");
		AVIS_CLIENT1_PRODUIT1.setNote(5);

		AVIS_CLIENT2_PRODUIT1 = new AvisClient();
		AVIS_CLIENT2_PRODUIT1.setDescription("assez bien");
		AVIS_CLIENT2_PRODUIT1.setNote(4);

		AVIS_CLIENT3_PRODUIT1 = new AvisClient();
		AVIS_CLIENT3_PRODUIT1.setDescription("null");
		AVIS_CLIENT3_PRODUIT1.setNote(3);

		PRODUIT1 = new Produit();
		PRODUIT1.setReferenceProduit("A05A01");
		PRODUIT1.setNom("livre");
		PRODUIT1.setPrixHT(4.5f);
		PRODUIT1.setAvisClients(new ArrayList<>());
		AVIS_CLIENT1_PRODUIT1.setProduit(PRODUIT1);
		AVIS_CLIENT2_PRODUIT1.setProduit(PRODUIT1);
		AVIS_CLIENT3_PRODUIT1.setProduit(PRODUIT1);
		PRODUIT1.getAvisClients().add(AVIS_CLIENT1_PRODUIT1);
		PRODUIT1.getAvisClients().add(AVIS_CLIENT2_PRODUIT1);
		PRODUIT1.getAvisClients().add(AVIS_CLIENT3_PRODUIT1);
		System.out.println(PRODUIT1.getAvisClients().get(0).getNote()+"\n"
				+PRODUIT1.getAvisClients().get(1).getNote()+"\n"
				+PRODUIT1.getAvisClients().get(2).getNote());
		
		
		//Concerne le produit 2 'PRODUIT2'
		AVIS_CLIENT1_PRODUIT2 = new AvisClient();
		AVIS_CLIENT1_PRODUIT2.setDescription("bien");
		AVIS_CLIENT1_PRODUIT2.setNote(1);

		AVIS_CLIENT2_PRODUIT2 = new AvisClient();
		AVIS_CLIENT2_PRODUIT2.setDescription("assez bien");
		AVIS_CLIENT2_PRODUIT2.setNote(2);

		AVIS_CLIENT3_PRODUIT2 = new AvisClient();
		AVIS_CLIENT3_PRODUIT2.setDescription("null");
		AVIS_CLIENT3_PRODUIT2.setNote(3);

		PRODUIT2 = new Produit();
		PRODUIT2.setReferenceProduit("A05A02");
		PRODUIT2.setNom("chaise");
		PRODUIT2.setPrixHT(4.5f);
		PRODUIT2.setAvisClients(new ArrayList<>());
		AVIS_CLIENT1_PRODUIT2.setProduit(PRODUIT2);
		AVIS_CLIENT2_PRODUIT2.setProduit(PRODUIT2);
		AVIS_CLIENT3_PRODUIT2.setProduit(PRODUIT2);
		PRODUIT2.getAvisClients().add(AVIS_CLIENT1_PRODUIT2);
		PRODUIT2.getAvisClients().add(AVIS_CLIENT2_PRODUIT2);
		PRODUIT2.getAvisClients().add(AVIS_CLIENT3_PRODUIT2);

	}
	
	@Autowired
	private AvisClientRepository avisClientRepository;
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@Before
	public void insertProduit() {
		produitRepository.save(PRODUIT1);
		produitRepository.save(PRODUIT2);
	}
	
	@Test
	public void averageAvisClientByReferenceProduit1() {
		double res = avisClientRepository.averageByReferenceProduit("A05A01");
		Assert.assertNotNull(res);
		Assert.assertEquals(4.0, res, 0.1);
	}
	
	@Test
	public void averageAvisClientByReferenceProduit2() {
		double res = avisClientRepository.averageByReferenceProduit("A05A02");
		Assert.assertNotNull(res);
		Assert.assertEquals(2.0, res, 0.1);
	}
	
	@Test
	public void averageAvisClientByEmptyReference() {
		double res = avisClientRepository.averageByReferenceProduit("");
		Assert.assertEquals(0.0, res, 0.1);
	}
	
	@Test
	public void averageAvisClientByEmptyReference2() {
		double res = avisClientRepository.averageByReferenceProduit(" ");
		Assert.assertEquals(0.0, res, 0.1);
	}
	
	@Test
	public void averageAvisClientByNullReference() {
		double res = avisClientRepository.averageByReferenceProduit(null);
		Assert.assertEquals(0.0, res, 0.1);
	}

}
