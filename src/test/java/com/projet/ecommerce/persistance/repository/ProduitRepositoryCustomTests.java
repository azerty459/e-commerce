package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProduitRepositoryCustomTests {

    private static final Produit TEMP_INSERT;
    private static final Categorie TEMP_CATEGORIE;
    private static final AvisClient TEMP_AVIS;
    private static final AvisClient TEMP_AVIS2;

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT = new Produit();
        TEMP_INSERT.setReferenceProduit("A05A87");
        TEMP_INSERT.setPrixHT(8.7f);
        TEMP_INSERT.setNom("Test nom");
        TEMP_INSERT.setDescription("joli produit");
        TEMP_INSERT.setCategories(new ArrayList<>());
        TEMP_INSERT.setAvisClients(new ArrayList<>());

        TEMP_CATEGORIE = new Categorie();
        TEMP_CATEGORIE.setIdCategorie(1);
        TEMP_CATEGORIE.setNomCategorie("Livre");
        TEMP_CATEGORIE.setBorneGauche(1);
        TEMP_CATEGORIE.setBorneDroit(2);
        TEMP_CATEGORIE.setLevel(1);
        TEMP_CATEGORIE.setProduits(new ArrayList<>());
        
        TEMP_AVIS = new AvisClient();
        TEMP_AVIS.setProduit(TEMP_INSERT);
        TEMP_AVIS.setNote(3);
        
        TEMP_AVIS2 = new AvisClient();
        TEMP_AVIS2.setProduit(TEMP_INSERT);
        TEMP_AVIS2.setNote(5);
        
        List<AvisClient> listeAvis = new ArrayList<AvisClient>();
        listeAvis.add(TEMP_AVIS);
        listeAvis.add(TEMP_AVIS2);
        TEMP_INSERT.setAvisClients(listeAvis);

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
    // Je teste que la méthode si l'on l'appelle avec null dans les paramètres, elle retourne une collection de produit.
    public void findProduitWithCriteriaByNull() {
        Collection<Produit> produitCollection = produitRepository.findProduitWithCriteria(null, null, null, null, null);
        Assert.assertNotNull(produitCollection);
    }
    
    @Test
    public void findProduitWithCriteriaByNomCompletCorrect() {
        Collection<Produit> produitCollection = produitRepository.findProduitWithCriteria(null, null, "Test nom", null, null);
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(1, produitCollection.size());
    }
    
    @Test
    public void findProduitWithCriteriaByNomCompletNonCorrect() {
        Collection<Produit> produitCollection = produitRepository.findProduitWithCriteria(null, null, "Test ", null, null);
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(0, produitCollection.size());
    }
    
    @Test
    public void findProduitWithCriteriaByNomIncomplet() {
        Collection<Produit> produitCollection = produitRepository.findProduitWithCriteria(null, null, null, "Test", null);
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(1, produitCollection.size());
    }
    

    @Test
    public void findProduitWithCriteriaByMoyenneInf() {
        Collection<Produit> produitCollection = produitRepository.findProduitWithCriteria(3, null, null,null, null);
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(1, produitCollection.size());
    }
    
    @Test
    public void findProduitWithCriteriaByMoyenneSup() {
        Collection<Produit> produitCollection = produitRepository.findProduitWithCriteria(null, 6, null, null, null);
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(1, produitCollection.size());
    }
    
    @Test
    public void findProduitWithCriteriaByCategorie() {
        Collection<Produit> produitCollection = produitRepository.findProduitWithCriteria(null, null, null, null, "Livre");
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(1, produitCollection.size());
    }
}
