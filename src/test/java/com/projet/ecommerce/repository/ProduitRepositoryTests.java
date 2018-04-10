package com.projet.ecommerce.repository;

import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProduitRepositoryTests {

	private static final Produit TEMP_INSERT = new Produit();
	private static final Produit TEMP_DELETE = new Produit();
	private static final Produit TEMP_UPDATE = new Produit();
	private static final Produit TEMP_GET = new Produit();

	private List<Produit> tempList;

	@Autowired
	private ProduitRepository produitRepository;

	@Before
	public void init() {
		this.TEMP_INSERT.setReferenceProduit("A05A87");
		this.TEMP_INSERT.setPrixHT(8.7);
		this.TEMP_INSERT.setDescription("joli produit");
		this.TEMP_DELETE.setReferenceProduit("A05A88");
		this.TEMP_DELETE.setPrixHT(11.7);
		this.TEMP_DELETE.setDescription("produit");
		this.TEMP_UPDATE.setReferenceProduit("A05A89");
		this.TEMP_UPDATE.setPrixHT(10.875);
		this.TEMP_UPDATE.setDescription("joli truc");
		this.TEMP_GET.setReferenceProduit("A05A90");
		this.TEMP_GET.setPrixHT(9.214);
		this.TEMP_GET.setDescription("bas de gamme");
	}

	@Test
	public void insertProduit() {
		Assert.assertNotNull(produitRepository.save(this.TEMP_INSERT));
		Produit temp = produitRepository.findById(TEMP_INSERT.getReferenceProduit()).get();
		Assert.assertNotNull(temp);
	}

	@Test
	public void getProduit() {
//		this.tempList = produitRepository.findAll();
//		Assert.assertEquals(0, this.tempList.size());
//		produitRepository.save(this.TEMP_INSERT);
//		this.tempList = produitRepository.findAll();
//		Assert.assertNotEquals(0, this.tempList.size());
//		Assert.assertEquals(1, this.tempList.size());
	}

	@Test
	public void getProduitByID() {
		Assert.assertNotNull(produitRepository.save(this.TEMP_GET));
		Produit temp = produitRepository.findById(TEMP_GET.getReferenceProduit()).get();
		Assert.assertNotNull("Produit ne peut pas être null", temp);
		Assert.assertEquals(this.TEMP_GET.getReferenceProduit(), temp.getReferenceProduit());
		Assert.assertEquals(this.TEMP_GET.getPrixHT(), temp.getPrixHT(), 0);
		Assert.assertEquals(this.TEMP_GET.getDescription(), temp.getDescription());
	}

	@Test
	public void updateProduit() {
		produitRepository.save(TEMP_UPDATE);
		Produit temp = produitRepository.findById(TEMP_UPDATE.getReferenceProduit()).get();
		Assert.assertEquals(this.TEMP_UPDATE.getReferenceProduit(), temp.getReferenceProduit());
		Assert.assertEquals(this.TEMP_UPDATE.getPrixHT(), temp.getPrixHT(), 0);
		Assert.assertEquals(this.TEMP_UPDATE.getDescription(), temp.getDescription());
		this.TEMP_UPDATE.setReferenceProduit("A05A100");
		this.TEMP_UPDATE.setPrixHT(15.5);
		this.TEMP_UPDATE.setDescription("joli chose");
		Assert.assertNotNull(produitRepository.save(this.TEMP_UPDATE));
		temp = produitRepository.findById(TEMP_UPDATE.getReferenceProduit()).get();
		Assert.assertEquals("joli chose", temp.getDescription());
		Assert.assertEquals(TEMP_UPDATE.getPrixHT(), temp.getPrixHT(), 0);
		Assert.assertEquals(TEMP_UPDATE.getReferenceProduit(), temp.getReferenceProduit());
	}

	@Test
	public void deleteProduit() {
		Assert.assertNotNull(produitRepository.save(this.TEMP_DELETE));
		produitRepository.delete(TEMP_DELETE);
		Assert.assertFalse(produitRepository.findById(TEMP_DELETE.getReferenceProduit()).isPresent());
	}

	@After
	public void end(){
		produitRepository.delete(this.TEMP_DELETE);
		produitRepository.delete(this.TEMP_GET);
		produitRepository.delete(this.TEMP_INSERT);
		produitRepository.delete(this.TEMP_UPDATE);
	}
}
