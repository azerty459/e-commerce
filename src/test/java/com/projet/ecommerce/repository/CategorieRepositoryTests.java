package com.projet.ecommerce.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategorieRepositoryTests {

	private static final Categorie TEMP_INSERT = new Categorie();
	private static final Categorie TEMP_DELETE = new Categorie();
	private static final Categorie TEMP_UPDATE = new Categorie();
	private static final Categorie TEMP_GET = new Categorie();

	static {
		TEMP_INSERT.setNomCategorie("Transport");
		TEMP_INSERT.setBorneGauche(1);
		TEMP_INSERT.setBorneDroit(8);

		TEMP_DELETE.setNomCategorie("Aérien");
		TEMP_DELETE.setBorneGauche(2);
		TEMP_DELETE.setBorneDroit(7);

		TEMP_GET.setNomCategorie("Avion");
		TEMP_GET.setBorneGauche(3);
		TEMP_GET.setBorneDroit(4);

		TEMP_UPDATE.setNomCategorie("Fusée");
		TEMP_UPDATE.setBorneGauche(5);
		TEMP_UPDATE.setBorneDroit(6);
	}

	private Collection<Categorie> tempList;

	@Autowired
	private CategorieRepository categorieRepository;

	@Test
	public void insertCategorie() {
		Categorie save = categorieRepository.save(this.TEMP_INSERT);
		Assert.assertNotNull(save);
		Categorie temp = categorieRepository.findById(TEMP_INSERT.getNomCategorie()).get();
		Assert.assertNotNull(temp);
	}

	@Test
	public void getCategorie() {
		this.tempList = categorieRepository.findAll();
		Assert.assertEquals(0, this.tempList.size());
		categorieRepository.save(this.TEMP_INSERT);
		this.tempList = categorieRepository.findAll();
		Assert.assertEquals(1, this.tempList.size());
	}

	@Test
	public void getCategorieByID() {
		Assert.assertNotNull(categorieRepository.save(this.TEMP_GET));
		Categorie temp = categorieRepository.findById(TEMP_GET.getNomCategorie()).get();
		Assert.assertNotNull("Produit ne peut pas être null", temp);
		Assert.assertEquals(this.TEMP_GET.getBorneDroit(), temp.getBorneDroit());
		Assert.assertEquals(this.TEMP_GET.getBorneGauche(), temp.getBorneGauche());
		Assert.assertEquals(this.TEMP_GET.getNomCategorie(), temp.getNomCategorie());
	}

	@Test
	public void updateCategorie() {
		categorieRepository.save(TEMP_UPDATE);
		Categorie temp = categorieRepository.findById(TEMP_UPDATE.getNomCategorie()).get();
		Assert.assertEquals(this.TEMP_UPDATE.getNomCategorie(), temp.getNomCategorie());
		Assert.assertEquals(this.TEMP_UPDATE.getBorneGauche(), temp.getBorneGauche());
		Assert.assertEquals(this.TEMP_UPDATE.getBorneDroit(), temp.getBorneDroit());
		this.TEMP_UPDATE.setBorneGauche(6);
		this.TEMP_UPDATE.setBorneDroit(7);
		this.TEMP_UPDATE.setNomCategorie("Test");
		Assert.assertNotNull(categorieRepository.save(this.TEMP_UPDATE));

		temp = categorieRepository.findById(TEMP_UPDATE.getNomCategorie()).get();
		Assert.assertEquals(TEMP_UPDATE.getNomCategorie(), temp.getNomCategorie());
		Assert.assertEquals(TEMP_UPDATE.getBorneDroit(), temp.getBorneDroit());
		Assert.assertEquals(TEMP_UPDATE.getBorneGauche(), temp.getBorneGauche());
	}

	@Test
	public void deleteCategorie() {
		Assert.assertNotNull(categorieRepository.save(this.TEMP_DELETE));

		categorieRepository.delete(TEMP_DELETE);
		Assert.assertFalse(categorieRepository.findById(TEMP_DELETE.getNomCategorie()).isPresent());
	}

	@After
	public void end(){
		categorieRepository.delete(this.TEMP_DELETE);
		categorieRepository.delete(this.TEMP_GET);
		categorieRepository.delete(this.TEMP_INSERT);
		categorieRepository.delete(this.TEMP_UPDATE);
	}
}
