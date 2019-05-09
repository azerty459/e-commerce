package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Produit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AvisClientRepositoryTests {

	@Autowired
	private AvisClientRepository avisClientRepository;

	@Autowired
	private ProduitRepository produitRepository;

	@Test
	public void findByProduit_ReferenceProduit() {
		Produit p = new Produit();
		p.setReferenceProduit("AAA");
		p.setNom("A");
		p.setDescription("Bla");
		p.setPrixHT(5);
		produitRepository.save(p);

		AvisClient avisClient = new AvisClient();
		avisClient.setNote(3);
		avisClient.setDescription("Bla bla bla");
		avisClient.setProduit(p);
		avisClientRepository.save(avisClient);

		List<AvisClient> lesAvisClientDuProduit = new ArrayList<>(
				avisClientRepository.findByProduit_ReferenceProduit(p.getReferenceProduit()));
		Assert.assertNotNull(lesAvisClientDuProduit);
		Assert.assertFalse(lesAvisClientDuProduit.isEmpty());
		Assert.assertNotNull(lesAvisClientDuProduit.get(0));
		AvisClient avisClientTrouve = lesAvisClientDuProduit.get(0);
		Assert.assertEquals(avisClientTrouve.getDescription(), avisClient.getDescription());
		Assert.assertEquals(avisClientTrouve.getNote(), avisClient.getNote());
		Assert.assertNotNull(avisClientTrouve.getProduit());
		Assert.assertEquals(avisClientTrouve.getProduit().getReferenceProduit(),
				avisClient.getProduit().getReferenceProduit());
	}

}
