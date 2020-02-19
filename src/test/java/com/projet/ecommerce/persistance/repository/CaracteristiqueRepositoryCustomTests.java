package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Libelle;
import com.projet.ecommerce.persistance.entity.Produit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Collection;

//TODO supprimer cette classe qui ne sert qu'à checker le comportement du framework
//XXX - détails sur ces annotations ?
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CaracteristiqueRepositoryCustomTests {
    public static final Caracteristique TEMP_INSERT_1 = new Caracteristique();
    public static final Caracteristique TEMP_INSERT_2 = new Caracteristique();
    public static final Caracteristique TEMP_INSERT_3 = new Caracteristique();
    public static final Caracteristique TEMP_INSERT_4 = new Caracteristique();

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        //XXX - why?
        System.setProperty("spring.config.location", "classpath:application-test.properties");
    }

    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    LibelleRepository libelleRepository;
    @Autowired
    CaracteristiqueRepository caracteristiqueRepository;

    @Before
    public void populateDatabase() {
        Produit p1 = produitRepository.save(ProduitRepositoryTests.TEMP_INSERT);
        Produit p2 = produitRepository.save(ProduitRepositoryTests.TEMP_GET);
        Produit p3 = produitRepository.save(ProduitRepositoryTests.TEMP_DELETE);
        Produit p4 = produitRepository.save(ProduitRepositoryTests.TEMP_UPDATE);

        Libelle l1 = libelleRepository.save(LibelleRepositoryUnnecessaryTests.TEMP_INSERT_1);
        Libelle l2 = libelleRepository.save(LibelleRepositoryUnnecessaryTests.TEMP_INSERT_2);
        Libelle l3 = libelleRepository.save(LibelleRepositoryUnnecessaryTests.TEMP_INSERT_3);
        Libelle l4 = libelleRepository.save(LibelleRepositoryUnnecessaryTests.TEMP_INSERT_4);

        TEMP_INSERT_1.setProduit(p1);
        TEMP_INSERT_1.setLibelle(l1);
        TEMP_INSERT_1.setValeur("Interessant");

        TEMP_INSERT_2.setProduit(p1);
        TEMP_INSERT_2.setLibelle(l2);
        TEMP_INSERT_2.setValeur("De mieux en mieux");

        TEMP_INSERT_3.setProduit(p2);
        TEMP_INSERT_3.setLibelle(l3);
        TEMP_INSERT_3.setValeur("Quoi d'autre ?");

        caracteristiqueRepository.save(TEMP_INSERT_1);
        caracteristiqueRepository.save(TEMP_INSERT_2);
        caracteristiqueRepository.save(TEMP_INSERT_3);
    }

    /**
     * Unnecessary tests ... pour voir fonctionnement du framework
     * TODO - supprimer ces tests
     */
    @Test
    public void seeWhatsInProduitAndLibelleAndCaracteristiques() {
        //XXX - Pourquoi date = null quand on execute ce test... ?
        Collection<Produit> allP = produitRepository.findAll();
        //XXX - pourquoi ici findAll renvoie Iterable ??
        Collection<Libelle> allL = libelleRepository.findByNomContainingIgnoreCase("");
        Iterable<Caracteristique> allC = caracteristiqueRepository.findAll();
        boolean mettrePointDarret = true;
    }

    @Test
    public void findByIdRefProduit()
    {
        Collection<Caracteristique> a05A87caracs = caracteristiqueRepository.findById_ReferenceProduit("A05A87");
        boolean mettrePointDarret = true;
    }

    @Test
    public void findByProduit()
    {
        Collection<Caracteristique> byProduit = caracteristiqueRepository.findByProduit(ProduitRepositoryTests.TEMP_INSERT);
        boolean mettrePointDarret = true;

    }

    @Test
    public void findByProduitWithNewProduitWithIdOnly()
    {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A87");
        Collection<Caracteristique> byProduit = caracteristiqueRepository.findByProduit(produit);
        //XXX - ceci confirme que findByProduit ne semble ne regarder que la clé primaire du produit
        //  (reference_produit) pour trouver les caracteristiques
        boolean mettrePointDarret = true;

    }

    /**
     * END OF UNNECESSARY TESTS
     */

    @Test
    public void findByProduitAndMotCle() {
        //Cas null
        Collection<Caracteristique> caracsFound = caracteristiqueRepository.findByProduitAndMotCle
                (null, null);
        Assert.assertEquals(3,caracsFound.size());

        //Cas produit et pas de mot cle
        caracsFound = caracteristiqueRepository.findByProduitAndMotCle
                (ProduitRepositoryTests.TEMP_INSERT, null);
        Assert.assertEquals(2,caracsFound.size());

        //Cas mot clé et pas de produit
        caracsFound = caracteristiqueRepository.findByProduitAndMotCle
                (null, "broché");
        Assert.assertEquals(1,caracsFound.size());

        //Autres cas

        caracsFound = caracteristiqueRepository.findByProduitAndMotCle
                (ProduitRepositoryTests.TEMP_INSERT, "interessant");
        Assert.assertEquals(1,caracsFound.size());

        caracsFound = caracteristiqueRepository.findByProduitAndMotCle
                (ProduitRepositoryTests.TEMP_INSERT, "editeur");
        Assert.assertEquals(1,caracsFound.size());

        caracsFound = caracteristiqueRepository.findByProduitAndMotCle
                (ProduitRepositoryTests.TEMP_INSERT, "broché");
        Assert.assertEquals(1,caracsFound.size());
    }
}

