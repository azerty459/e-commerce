package com.projet.ecommerce.persistance.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private static final Produit TEMP_INSERT2;
    private static final Produit TEMP_INSERT3;
    
    private static final Categorie TEMP_CATEGORIE;
//    private static final Categorie TEMP_CATEGORIE2;
    
    private static final AvisClient TEMP_AVIS;
    private static final AvisClient TEMP_AVIS2;
    private static final AvisClient TEMP_AVIS3;
    private static final AvisClient TEMP_AVIS4;

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT = new Produit();
        TEMP_INSERT.setNom("Test");
        TEMP_INSERT.setReferenceProduit("A05A87");
        TEMP_INSERT.setPrixHT(8.7f);
        TEMP_INSERT.setDescription("joli produit");
        TEMP_INSERT.setCategories(new ArrayList<>());
        TEMP_INSERT.setAvisClients(new ArrayList<>());

        TEMP_CATEGORIE = new Categorie();
        TEMP_CATEGORIE.setNomCategorie("Livre");
        TEMP_CATEGORIE.setBorneGauche(1);
        TEMP_CATEGORIE.setBorneDroit(2);
        TEMP_CATEGORIE.setLevel(1);
        TEMP_CATEGORIE.setProduits(new ArrayList<>());
        
//        TEMP_CATEGORIE2 = new Categorie();
//        TEMP_CATEGORIE2.setNomCategorie("Film");
//        TEMP_CATEGORIE2.setBorneGauche(1);
//        TEMP_CATEGORIE2.setBorneDroit(2);
//        TEMP_CATEGORIE2.setLevel(1);
//        TEMP_CATEGORIE2.setProduits(new ArrayList<>());
        
        TEMP_INSERT2 = new Produit();
        TEMP_INSERT2.setNom("Store");
        TEMP_INSERT2.setReferenceProduit("A05A88");
        TEMP_INSERT2.setPrixHT(9.7f);
        TEMP_INSERT2.setDescription("nouveau produit");
        TEMP_INSERT2.setCategories(new ArrayList<>());
        TEMP_INSERT2.setAvisClients(new ArrayList<>());
        
        TEMP_INSERT3 = new Produit();
        TEMP_INSERT3.setNom("Produit");
        TEMP_INSERT3.setReferenceProduit("A05A86");
        TEMP_INSERT3.setPrixHT(9.7f);
        TEMP_INSERT3.setDescription("nouveau produit");
        TEMP_INSERT3.setCategories(new ArrayList<>());
        TEMP_INSERT3.setAvisClients(new ArrayList<>());
        
        TEMP_AVIS = new AvisClient();
        TEMP_AVIS.setNote(3);
        TEMP_AVIS.setDescription("Bla bla bla");
        TEMP_AVIS.setProduit(TEMP_INSERT);
        
        TEMP_AVIS2 = new AvisClient();
        TEMP_AVIS2.setNote(4);
        TEMP_AVIS2.setDescription("Bla bla bla");
        TEMP_AVIS2.setProduit(TEMP_INSERT);
        
        TEMP_AVIS3 = new AvisClient();
        TEMP_AVIS3.setNote(2);
        TEMP_AVIS3.setDescription("Bla bla bla");
        TEMP_AVIS3.setProduit(TEMP_INSERT2);
        
        TEMP_AVIS4 = new AvisClient();
        TEMP_AVIS4.setNote(5);
        TEMP_AVIS4.setDescription("Bla bla bla");
        TEMP_AVIS4.setProduit(TEMP_INSERT3);
        

        Collection<Categorie> categorieCollection = TEMP_INSERT.getCategories();
        categorieCollection.add(TEMP_CATEGORIE);
        TEMP_INSERT.setCategories(new ArrayList<>(categorieCollection));
        //TEMP_INSERT2.setCategories(new ArrayList<>(categorieCollection));
        
//        Collection<Categorie> categorieCollection2 = TEMP_INSERT2.getCategories();
//        categorieCollection2.add(TEMP_CATEGORIE2);
//        TEMP_INSERT.setCategories(new ArrayList<>(categorieCollection2));
//        
//        
        Collection<AvisClient> avisClientCollectionProd1 = TEMP_INSERT.getAvisClients();
        avisClientCollectionProd1.add(TEMP_AVIS);
        avisClientCollectionProd1.add(TEMP_AVIS2);
        
        Collection<AvisClient> avisClientCollectionProd2 = TEMP_INSERT2.getAvisClients();
        avisClientCollectionProd2.add(TEMP_AVIS3);
        
        Collection<AvisClient> avisClientCollectionProd3 = TEMP_INSERT3.getAvisClients();
        avisClientCollectionProd3.add(TEMP_AVIS4);
        
        TEMP_INSERT.setAvisClients(new ArrayList<>(avisClientCollectionProd1));
        TEMP_INSERT2.setAvisClients(new ArrayList<>(avisClientCollectionProd2));
        TEMP_INSERT3.setAvisClients(new ArrayList<>(avisClientCollectionProd3));
    }

    @Before
    public void insertProduit() {
        produitRepository.save(TEMP_INSERT);
        produitRepository.save(TEMP_INSERT2);
        produitRepository.save(TEMP_INSERT3);
    }

    @Autowired
    private ProduitRepository produitRepository;

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
        Assert.assertEquals(0, produitRepository.findAllWithCriteria("A05A89", null).size());
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
	public void findAllWithCriteriaRequeteComplexeByNull() {
		Collection<Produit> lesProduits = produitRepository.findAll();
	  	Collection<Produit> produitCollection = produitRepository.findAllWithCriteriaRequeteComplexe(null, null, null, null, null);
	  	Assert.assertNotNull(produitCollection);
	  	Assert.assertEquals(lesProduits.size(),produitCollection.size());
	}
    
    @Test
    public void findAllWithCriteriaRequeteComplexeByNom() {
    	Collection<Produit> lesProduits = produitRepository.findAllWithCriteriaRequeteComplexe("Test", null, null, null, null);
    	Assert.assertEquals(1, lesProduits.size());
    	Assert.assertEquals(0, produitRepository.findAllWithCriteriaRequeteComplexe("FalseTest", null, null, null, null).size());
    }
    
    @Test
    public void findAllWithCriteriaRequeteComplexeByPartieNom() {
    	Collection<Produit> lesProduits = produitRepository.findAllWithCriteriaRequeteComplexe(null, "st", null, null, null);
    	Assert.assertEquals(2, lesProduits.size());
    	Assert.assertEquals(0, produitRepository.findAllWithCriteriaRequeteComplexe(null,"banane", null, null, null).size());
    }
    @Test
    public void findAllWithCriteriaRequeteComplexeByMoyenneAvisInferieurA() {
    	Collection<Produit> lesProduits = produitRepository.findAllWithCriteriaRequeteComplexe(null, null, 5.0, null, null);
    	Assert.assertEquals(2, lesProduits.size());
    	Assert.assertEquals(0, produitRepository.findAllWithCriteriaRequeteComplexe(null, null, 1.0, null, null).size());
    }
    
    @Test
    public void findAllWithCriteriaRequeteComplexeByMoyenneAvisSuperieurA() {
    	Collection<Produit> lesProduits = produitRepository.findAllWithCriteriaRequeteComplexe(null, null, null, 3.0, null);
    	Assert.assertEquals(2, lesProduits.size());
    	Assert.assertEquals(0, produitRepository.findAllWithCriteriaRequeteComplexe(null, null, null, 5.0, null).size());
    }
    
    @Test
    public void findAllWithCriteriaRequeteComplexeByCategorie() {
    	Collection<Produit> lesProduits = produitRepository.findAllWithCriteriaRequeteComplexe(null, null, null, null, TEMP_CATEGORIE);
    	Assert.assertEquals(1, lesProduits.size());
    	Assert.assertEquals(0, produitRepository.findAllWithCriteriaRequeteComplexe(null, null, null, null, new Categorie()).size());
    }
}
