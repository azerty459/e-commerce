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

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT = new Produit();
        TEMP_INSERT.setReferenceProduit("A05A87");
        TEMP_INSERT.setNom("Bidule");
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

        TEMP_INSERT.getCategories().add(TEMP_CATEGORIE);
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
    public void testFindByCriteria() {
        //Recup la catégorie
        Categorie categorie = TEMP_CATEGORIE;
        categorie.setIdCategorie(1);
        List<Categorie> listCategorie = new ArrayList<>();
        listCategorie.add(categorie);
        //Creation avis client
        AvisClient avisClient1 = new AvisClient();
        avisClient1.setNote(8);
        AvisClient avisClient2 = new AvisClient();
        avisClient2.setNote(4);
        //Ajout de 2 produits en base
        Produit produit = new Produit();
        produit.setReferenceProduit("A08A42");
        produit.setNom("ProduitTest");
        produit.setPrixHT(4.8f);
        produit.setDescription("Le meilleur produit");
        produit.setCategories(listCategorie);
        produit.setAvisClients(new ArrayList<>());
        produit.getAvisClients().add(avisClient1);
        avisClient1.setProduit(produit);
        produitRepository.save(produit);
        produit = new Produit();
        produit.setReferenceProduit("A04A82");
        produit.setNom("AutreProduit");
        produit.setPrixHT(6.2f);
        produit.setDescription("Autre produit");
        produit.setCategories(new ArrayList<>());
        produit.setAvisClients(new ArrayList<>());
        produit.getAvisClients().add(avisClient2);
        avisClient2.setProduit(produit);
        produitRepository.save(produit);
        
        //Appel la méthode sans filtre
        Collection<Produit> retour = produitRepository.findByCriteria(-1, -1, null, null, null);
        Assert.assertEquals(3, retour.size());
        //Test avec les notes
        retour = produitRepository.findByCriteria(4, -1, null, null, null);
        Assert.assertEquals(1, retour.size());
        retour = produitRepository.findByCriteria(-1, 10, null, null, null);
        Assert.assertEquals(2, retour.size());
        retour = produitRepository.findByCriteria(2, 6, null, null, null);
        Assert.assertEquals(1, retour.size());
        retour = produitRepository.findByCriteria(8, 10, null, null, null);
        Assert.assertEquals(0, retour.size());
        retour = produitRepository.findByCriteria(0, 100, null, null, null);
        Assert.assertEquals(2, retour.size());
        retour = produitRepository.findByCriteria(100, 0, null, null, null);
        Assert.assertEquals(0, retour.size());
        retour = produitRepository.findByCriteria(-8, 7.2, null, null, null);
        Assert.assertEquals(1, retour.size());
        retour = produitRepository.findByCriteria(4.4, -5, null, null, null);
        Assert.assertEquals(1, retour.size());
        retour = produitRepository.findByCriteria(-14.5, -18.6, null, null, null);
        Assert.assertEquals(3, retour.size());
        //Test avec le nom du produit
        retour = produitRepository.findByCriteria(-1, -1, "Bidule", null, null);
        Assert.assertEquals(1, retour.size());
        retour = produitRepository.findByCriteria(-1, -1, "Machin", null, null);
        Assert.assertEquals(0, retour.size());
        retour = produitRepository.findByCriteria(-1, -1, "", null, null); //Chaine vide = absence de parametre
        Assert.assertEquals(3, retour.size());
        retour = produitRepository.findByCriteria(-1, -1, "\t", null, null); //Chaine vide = absence de parametre
        Assert.assertEquals(3, retour.size());
        //Test avec le nom du produit contient
        retour = produitRepository.findByCriteria(-1, -1, null, "Produit", null);
        Assert.assertEquals(2, retour.size());
        retour = produitRepository.findByCriteria(-1, -1, null, "id", null);
        Assert.assertEquals(1, retour.size());
        retour = produitRepository.findByCriteria(-1, -1, null, "Nop", null);
        Assert.assertEquals(0, retour.size());
        retour = produitRepository.findByCriteria(-1, -1, null, "", null); //Chaine vide = absence de parametre
        Assert.assertEquals(3, retour.size());
        retour = produitRepository.findByCriteria(-1, -1, null, "\t", null); //Chaine vide = absence de parametre
        Assert.assertEquals(3, retour.size());
        //Test avec la catégorie
        Categorie unknowCategorie = new Categorie();
        unknowCategorie.setIdCategorie(8);
        retour = produitRepository.findByCriteria(-1, -1, null, null, categorie);
        Assert.assertEquals(2, retour.size());
        retour = produitRepository.findByCriteria(-1, -1, null, null, unknowCategorie);
        Assert.assertEquals(0, retour.size());
        //Test avec multiple filtre
        retour = produitRepository.findByCriteria(5.5, -1, null, "", categorie);
        Assert.assertEquals(1, retour.size());
        retour = produitRepository.findByCriteria(5.5, -1, null, "id", categorie);
        Assert.assertEquals(0, retour.size());
        retour = produitRepository.findByCriteria(-1, -1, "", "Produit", categorie);
        Assert.assertEquals(1, retour.size());
        retour = produitRepository.findByCriteria(-1, 8.2, "", "dui", null);
        Assert.assertEquals(2, retour.size());
        retour = produitRepository.findByCriteria(3, -1, "AutreProduit", null, null);
        Assert.assertEquals(1, retour.size());
        retour = produitRepository.findByCriteria(5, -1, "AutreProduit", null, null);
        Assert.assertEquals(0, retour.size());
    }
}
