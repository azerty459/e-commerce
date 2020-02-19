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
    private static final Produit TEMP_FINDWCRITERIA_PRODUIT1 = new Produit();
    private static final Produit TEMP_FINDWCRITERIA_PRODUIT2 = new Produit();
    private static final Produit TEMP_FINDWCRITERIA_PRODUIT3 = new Produit();

    private static final AvisClient TEMP_FINDWCRITERIA_AVISCLIENT11 = new AvisClient();
    private static final AvisClient TEMP_FINDWCRITERIA_AVISCLIENT12 = new AvisClient();
    private static final AvisClient TEMP_FINDWCRITERIA_AVISCLIENT13 = new AvisClient();
    private static final AvisClient TEMP_FINDWCRITERIA_AVISCLIENT21 = new AvisClient();
    private static final AvisClient TEMP_FINDWCRITERIA_AVISCLIENT22 = new AvisClient();

    private static final Categorie TEMP_FINDWCRITERIA_CATEGORIE1 = new Categorie();
    private static final Categorie TEMP_FINDWCRITERIA_CATEGORIE2 = new Categorie();


    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_FINDWCRITERIA_PRODUIT1.setReferenceProduit("P1");
        TEMP_FINDWCRITERIA_PRODUIT1.setNom("Produit test 1");
        TEMP_FINDWCRITERIA_PRODUIT1.setPrixHT(10.80f);
        TEMP_FINDWCRITERIA_PRODUIT1.addCategories(TEMP_FINDWCRITERIA_CATEGORIE1);
        TEMP_FINDWCRITERIA_PRODUIT1.addCategories(TEMP_FINDWCRITERIA_CATEGORIE2);


        TEMP_FINDWCRITERIA_AVISCLIENT11.setId(1);
        TEMP_FINDWCRITERIA_AVISCLIENT11.setNote(5);
        TEMP_FINDWCRITERIA_AVISCLIENT11.setDescription("desc1");
        TEMP_FINDWCRITERIA_AVISCLIENT11.setProduit(TEMP_FINDWCRITERIA_PRODUIT1);

        TEMP_FINDWCRITERIA_AVISCLIENT12.setId(2);
        TEMP_FINDWCRITERIA_AVISCLIENT12.setNote(7);
        TEMP_FINDWCRITERIA_AVISCLIENT12.setDescription("desc2");
        TEMP_FINDWCRITERIA_AVISCLIENT12.setProduit(TEMP_FINDWCRITERIA_PRODUIT1);

        TEMP_FINDWCRITERIA_AVISCLIENT13.setId(3);
        TEMP_FINDWCRITERIA_AVISCLIENT13.setNote(0);
        TEMP_FINDWCRITERIA_AVISCLIENT13.setDescription("desc3");
        TEMP_FINDWCRITERIA_AVISCLIENT13.setProduit(TEMP_FINDWCRITERIA_PRODUIT1);

        TEMP_FINDWCRITERIA_PRODUIT2.setReferenceProduit("P2");
        TEMP_FINDWCRITERIA_PRODUIT2.setNom("Produit test 2");
        TEMP_FINDWCRITERIA_PRODUIT2.setPrixHT(99.95f);

        TEMP_FINDWCRITERIA_AVISCLIENT21.setId(4);
        TEMP_FINDWCRITERIA_AVISCLIENT21.setNote(0);
        TEMP_FINDWCRITERIA_AVISCLIENT21.setDescription("desc4");
        TEMP_FINDWCRITERIA_AVISCLIENT21.setProduit(TEMP_FINDWCRITERIA_PRODUIT2);

        TEMP_FINDWCRITERIA_AVISCLIENT22.setId(5);
        TEMP_FINDWCRITERIA_AVISCLIENT22.setNote(2);
        TEMP_FINDWCRITERIA_AVISCLIENT22.setDescription("desc5");
        TEMP_FINDWCRITERIA_AVISCLIENT22.setProduit(TEMP_FINDWCRITERIA_PRODUIT2);

        TEMP_FINDWCRITERIA_PRODUIT3.setReferenceProduit("P3");
        TEMP_FINDWCRITERIA_PRODUIT3.setNom("Produit test 3");
        TEMP_FINDWCRITERIA_PRODUIT3.setPrixHT(199.95f);
        TEMP_FINDWCRITERIA_PRODUIT3.addCategories(TEMP_FINDWCRITERIA_CATEGORIE2);


        TEMP_FINDWCRITERIA_CATEGORIE1.setIdCategorie(1);
        TEMP_FINDWCRITERIA_CATEGORIE1.setBorneDroit(10);
        TEMP_FINDWCRITERIA_CATEGORIE1.setBorneGauche(0);
        TEMP_FINDWCRITERIA_CATEGORIE1.setNomCategorie("Livres");
        TEMP_FINDWCRITERIA_CATEGORIE1.setLevel(0);

        TEMP_FINDWCRITERIA_CATEGORIE2.setIdCategorie(2);
        TEMP_FINDWCRITERIA_CATEGORIE2.setBorneDroit(10);
        TEMP_FINDWCRITERIA_CATEGORIE2.setBorneGauche(0);
        TEMP_FINDWCRITERIA_CATEGORIE2.setNomCategorie("Accessoires");
        TEMP_FINDWCRITERIA_CATEGORIE2.setLevel(0);

    }

    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private AvisClientRepository avisClientRepository;

    @Before
    public void populateDatabase() {
        produitRepository.save(TEMP_FINDWCRITERIA_PRODUIT1);
        produitRepository.save(TEMP_FINDWCRITERIA_PRODUIT2);
        produitRepository.save(TEMP_FINDWCRITERIA_PRODUIT3);

        avisClientRepository.save(TEMP_FINDWCRITERIA_AVISCLIENT11);
        avisClientRepository.save(TEMP_FINDWCRITERIA_AVISCLIENT12);
        avisClientRepository.save(TEMP_FINDWCRITERIA_AVISCLIENT13);
        avisClientRepository.save(TEMP_FINDWCRITERIA_AVISCLIENT21);
        avisClientRepository.save(TEMP_FINDWCRITERIA_AVISCLIENT22);

        categorieRepository.save(TEMP_FINDWCRITERIA_CATEGORIE1);
        categorieRepository.save(TEMP_FINDWCRITERIA_CATEGORIE2);
    }

    @Test
    public void findWithCriteria(){

        Collection<Produit> allPs = produitRepository.findAll();
        Assert.assertEquals(3,allPs.size());

        Collection<AvisClient> allAvClis = avisClientRepository.findAll();
        Assert.assertEquals(5,allAvClis.size());

        Collection<Categorie> allCats = categorieRepository.findAll();
        Assert.assertEquals(2, allCats.size());

        produitRepository.printAvgNoteByProduct();

        Collection<Produit> prodsByCrit = produitRepository.findByCriteria(null, null, null, null, null);
        Assert.assertEquals(3,prodsByCrit.size());

        prodsByCrit = produitRepository.findByCriteria(3f, null, null, null, null);
        Assert.assertEquals(1,prodsByCrit.size());

        prodsByCrit = produitRepository.findByCriteria(null, 3f, null, null, null);
        Assert.assertEquals(1,prodsByCrit.size());

        prodsByCrit = produitRepository.findByCriteria(null, null, "produit test 1", null, null);
        Assert.assertEquals(1,prodsByCrit.size());

        prodsByCrit = produitRepository.findByCriteria(null, null, null, "test", null);
        Assert.assertEquals(3,prodsByCrit.size());

        prodsByCrit = produitRepository.findByCriteria(null, null, null, null, TEMP_FINDWCRITERIA_CATEGORIE2);
        Assert.assertEquals(2,prodsByCrit.size());

        prodsByCrit = produitRepository.findByCriteria(3f, 10f, "produit test 1", "PRODUIT", TEMP_FINDWCRITERIA_CATEGORIE1);
        Assert.assertEquals(1,prodsByCrit.size());


/*
        Celle-ci devra accepter les filtres optionnels suivant:
        La note moyenne des avis clients est > paramA
        La note moyenne des avis clients est < paramB
        Le nom du produit = paramC
        Le nom du produit contient la valeur paramD
        La catégorie du produit = paramE (précisément, pas de notion de hiérarchie)
        Le format des paramètres paramA à paramE est libre mais doit être justifiée.
*/

    }
}
