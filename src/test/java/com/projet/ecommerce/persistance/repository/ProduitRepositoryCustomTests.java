package com.projet.ecommerce.persistance.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProduitRepositoryCustomTests {

    private static final Produit TEMP_INSERT;
    private static final Categorie TEMP_CATEGORIE;

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT = new Produit();
        TEMP_INSERT.setReferenceProduit("A05A87");
        TEMP_INSERT.setPrixHT(8.7f);
        TEMP_INSERT.setDescription("joli produit");
        TEMP_INSERT.setCategories(new ArrayList<>());

        TEMP_CATEGORIE = new Categorie();
        TEMP_CATEGORIE.setNomCategorie("Livre");
        TEMP_CATEGORIE.setBorneGauche(1);
        TEMP_CATEGORIE.setBorneDroit(2);
        TEMP_CATEGORIE.setLevel(1);
        TEMP_CATEGORIE.setProduits(new ArrayList<>());

        Collection<Categorie> categorieCollection = TEMP_INSERT.getCategories();
        categorieCollection.add(TEMP_CATEGORIE);
        TEMP_INSERT.setCategories(new ArrayList<>(categorieCollection));
    }

    @Before
    public void insertProduit() {
        produitRepository.save(TEMP_INSERT);
    }

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Test
    // Je teste que la méthode si l'on l'appelle avec null dans les deux paramètres, elle retourne une collection de produit.
    public void findAllWithCriteriaByNull() {
        Collection<Produit> produitCollection = produitRepository.findAllWithCriteria(null, null);
        Assert.assertNotNull(produitCollection);
    }

    @Test
    public void findAllWithCriteriaByReff() {
        Collection<Produit> produitCollection = produitRepository.findAllWithCriteria("A05A87", null);
        Assert.assertEquals(1, produitCollection.size());
        Assert.assertEquals(0, produitRepository.findAllWithCriteria("A05A88", null).size());
    }

    @Test
    @Transactional
    public void findAllWithCriteriaByCat() {
        List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findAllWithCriteria(null, "Livre"));
        Assert.assertEquals(1, retourProduitCollection.size());
        Assert.assertEquals(retourProduitCollection.get(0).getReferenceProduit(), TEMP_INSERT.getReferenceProduit());
        Assert.assertEquals(retourProduitCollection.get(0).getNom(), TEMP_INSERT.getNom());
        Assert.assertEquals(retourProduitCollection.get(0).getPrixHT(), TEMP_INSERT.getPrixHT(), 0);
        Assert.assertEquals(retourProduitCollection.get(0).getCategories().get(0).getNomCategorie(), TEMP_CATEGORIE.getNomCategorie());

        Assert.assertEquals(0, produitRepository.findAllWithCriteria("Toto", null).size());
    }
    
    @Test
    public void findAllWithCriteriaWithNullParam() {
    	Collection<Produit> allProduits = produitRepository.findAll();
    	Collection<Produit> produits = produitRepository.findAllWithCriteria(null, null,null,null,null);
    	assertEquals(allProduits.size(),produits.size());
    }
    
    @Test
    public void findAllWithCriteriaWithNoteMoyenneMin() {
    	List<Produit> prods = new ArrayList<>();
    	
    	Produit prod1 = createProduit();
    	prods.add(prod1);
    	AvisClient avi1 = createAvisClient(7);
    	AvisClient avi2 = createAvisClient(8);
    	avi1.setProduit(prod1);
    	avi2.setProduit(prod1);
    	prod1.getAvisClients().add(avi1);
    	prod1.getAvisClients().add(avi2);
    	

    	Produit prod2 = createProduit();
    	prods.add(prod2);
    	AvisClient avi3 = createAvisClient(2);
    	AvisClient avi4 = createAvisClient(3);
    	avi3.setProduit(prod1);
    	avi4.setProduit(prod1);
    	prod2.getAvisClients().add(avi3);
    	prod2.getAvisClients().add(avi4);

    	produitRepository.saveAll(prods);
    	
    	Collection<Produit> produits = produitRepository.findAllWithCriteria(5d, null,null,null,null);
    	assertTrue(produits.contains(prod1));
    	assertFalse(produits.contains(prod2));
    }

    @Test
    public void findAllWithCriteriaWithCategorie() {
    	List<Produit> prods = new ArrayList<>();
    	
    	Produit prod1 = createProduit();
    	List<Categorie> categs1 = new ArrayList<>();
    	Categorie categ1 = new Categorie();categ1.setNomCategorie("A");
    	categs1.add(categ1);
    	prod1.setCategories(categs1); // categ A 
    	prods.add(prod1);

    	Produit prod2 = createProduit();
    	List<Categorie> categs2 = new ArrayList<>();
    	Categorie categ2 = new Categorie();categ2.setNomCategorie("B");
    	categs2.add(categ2);
    	prod1.setCategories(categs2); // categ B
    	prods.add(prod2);

    	produitRepository.saveAll(prods);
    	
    	Collection<Produit> produits = produitRepository.findAllWithCriteria(null, null,null,null,categ1);
    	
    	assertTrue(produits.contains(prod1));
    	assertFalse(produits.contains(prod2));
    }

    
    
    @Test
    public void findAllWithCriteriaWithcontainsInProductName() {
    	List<Produit> prods = new ArrayList<>();
    	
    	Produit prod1 = createProduit();
    	prod1.setNom("AB");
    	prods.add(prod1);

    	Produit prod2 = createProduit();
    	prod2.setNom("BX");
    	prods.add(prod2);

    	produitRepository.saveAll(prods);
    	
    	Collection<Produit> produits = produitRepository.findAllWithCriteria(null, null,null,"A",null);
    	
    	assertTrue(produits.contains(prod1));
    	assertFalse(produits.contains(prod2));
    }
    
    @Test
    public void findAllWithCriteriaWithNameProduct() {
    	List<Produit> prods = new ArrayList<>();
    	
    	Produit prod1 = createProduit();
    	prod1.setNom("A");
    	prods.add(prod1);

    	Produit prod2 = createProduit();
    	prod2.setNom("B");
    	prods.add(prod2);

    	produitRepository.saveAll(prods);
    	
    	Collection<Produit> produits = produitRepository.findAllWithCriteria(null, null,"A",null,null);
    	
    	assertTrue(produits.contains(prod1));
    	assertFalse(produits.contains(prod2));
    }


    
    private AvisClient createAvisClient(int note) {
    	AvisClient avis = new AvisClient();
    	avis.setNote(note);
    	return avis;
    }
    
    private Produit createProduit() {
    	Produit produit = new Produit();
    	produit.setReferenceProduit("A"+new Random().nextInt(100));
    	produit.setAvisClients(new ArrayList());
    	return produit;
    }
}
