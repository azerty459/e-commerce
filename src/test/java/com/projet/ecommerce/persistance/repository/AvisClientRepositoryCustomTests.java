package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Produit;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AvisClientRepositoryCustomTests {

	static {

		// Permet d'écraser la config application.properties par
		// application-test.properties
		System.setProperty("spring.config.location", "classpath:application-test.properties");
	}

	@Autowired
	private AvisClientRepository avisClientRepository;

	@Autowired
	private ProduitRepository produitRepository;

	@Test
	public void averageAvisClientByReferenceProduit() {
		Produit produit1 = buildProduit("A05A01", 1, 2, 3);
		Produit produit2 = buildProduit("A05A02", 5, 4, 3);
		Produit produit3 = buildProduit("A05A03");
		produitRepository.save(produit1);
		produitRepository.save(produit2);
		produitRepository.save(produit3);

		float res = avisClientRepository.averageByReferenceProduit(produit1.getReferenceProduit());
		Assert.assertEquals(2F, res, 0.1);
		res = avisClientRepository.averageByReferenceProduit(produit2.getReferenceProduit());
		Assert.assertEquals(4F, res, 0.1);
		res = avisClientRepository.averageByReferenceProduit(produit3.getReferenceProduit());
		Assert.assertEquals(0F, res, 0.1);
	}

	@Test
	public void averageAvisClientByEmptyReference() {
		float res = avisClientRepository.averageByReferenceProduit("");
		Assert.assertEquals(0.0, res, 0.1);
	}

	@Test
	public void averageAvisClientByBlankReference() {
		float res = avisClientRepository.averageByReferenceProduit(" ");
		Assert.assertEquals(0.0, res, 0.1);
	}

	@Test
	public void averageAvisClientByNullReference() {
		float res = avisClientRepository.averageByReferenceProduit(null);
		Assert.assertEquals(0.0, res, 0.1);
	}

	@NotNull
	private Produit buildProduit(String ref, int... notes) {

		Produit produit = new Produit();
		produit.setReferenceProduit(ref);
		produit.setPrixHT(2.1f);
		produit.setDescription("Un livre");
		produit.setNom("Livre1");
		produit.setPhotos(new ArrayList<>());
		produit.setCategories(new ArrayList<>());

		if (notes.length > 0) {
			ArrayList<AvisClient> avisClients = new ArrayList<>();
			for (int note : notes) {
				AvisClient avisClient = new AvisClient();
				avisClient.setDescription("Avis client test");
				avisClient.setNote(note);
				avisClient.setProduit(produit);
				avisClients.add(avisClient);
			}
			produit.setAvisClients(avisClients);
		} else {
			produit.setAvisClients(new ArrayList<>());
		}

		return produit;
	}

}
