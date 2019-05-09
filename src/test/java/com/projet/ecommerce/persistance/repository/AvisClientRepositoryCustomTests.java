package com.projet.ecommerce.persistance.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Produit;

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

    @Before
    public void save() {

        Produit produit1 = buildProduit("A05A01", 1, 2, 3);
        Produit produit2 = buildProduit("A05A02", 5, 4, 3);
        produitRepository.save(produit1);
        produitRepository.save(produit2);
    }

    @Test
    public void averageAvisClientByReferenceProduit1() {
        double res = avisClientRepository.averageByReferenceProduit("A05A01");
        Assert.assertNotNull(res);
        Assert.assertEquals(2.0, res, 0.1);
    }

    @Test
    public void averageAvisClientByReferenceProduit2() {
        double res = avisClientRepository.averageByReferenceProduit("A05A02");
        Assert.assertNotNull(res);
        Assert.assertEquals(4.0, res, 0.1);
    }

    @Test
    public void averageAvisClientByBlankReference() {
        double res = avisClientRepository.averageByReferenceProduit("");
        Assert.assertEquals(0.0, res, 0.1);
    }

    @Test
    public void averageAvisClientByBlankReference2() {
        double res = avisClientRepository.averageByReferenceProduit(" ");
        Assert.assertEquals(0.0, res, 0.1);
    }

    @Test
    public void averageAvisClientByNullReference() {
        double res = avisClientRepository.averageByReferenceProduit(null);
        Assert.assertEquals(0.0, res, 0.1);
    }

    @NotNull
    private Produit buildProduit(String ref, int note1, int note2, int note3) {

        AvisClient avisClient1 = new AvisClient();
        avisClient1.setDescription("bien");
        avisClient1.setNote(note1);

        AvisClient avisClient2 = new AvisClient();
        avisClient2.setDescription("assez bien");
        avisClient2.setNote(note2);

        AvisClient avisClient3 = new AvisClient();
        avisClient3.setDescription("null");
        avisClient3.setNote(note3);

        ArrayList<AvisClient> avisClients = new ArrayList<>();
        avisClients.add(avisClient1);
        avisClients.add(avisClient2);
        avisClients.add(avisClient3);

        Produit produit = new Produit();
        produit.setReferenceProduit(ref);
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setPhotos(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
        produit.setAvisClients(avisClients);

        avisClient1.setProduit(produit);
        avisClient2.setProduit(produit);
        avisClient3.setProduit(produit);

        return produit;
    }
}
