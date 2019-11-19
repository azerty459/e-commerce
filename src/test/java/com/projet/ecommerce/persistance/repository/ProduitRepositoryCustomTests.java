package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import org.jetbrains.annotations.NotNull;
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
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProduitRepositoryCustomTests {

    private static Categorie TEMP_CATEGORIE;
    private static Produit TEMP_INSERT;

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

    }

    @Before
    public void insertProduit() {
        for (int i=1; i<=4; i++) {
            produitRepository.save(buildProduit(i));
        }
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
        TEMP_CATEGORIE = buildCategorie();
        List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findAllWithCriteria(null, "Livre"));
        Assert.assertEquals(1, retourProduitCollection.size());
        Assert.assertEquals(retourProduitCollection.get(0).getReferenceProduit(), TEMP_INSERT.getReferenceProduit());
        Assert.assertEquals(retourProduitCollection.get(0).getNom(), TEMP_INSERT.getNom());
        Assert.assertEquals(retourProduitCollection.get(0).getPrixHT(), TEMP_INSERT.getPrixHT(), 0);
        Assert.assertEquals(retourProduitCollection.get(0).getCategories().get(0).getNomCategorie(), TEMP_CATEGORIE.getNomCategorie());

        Assert.assertEquals(0, produitRepository.findAllWithCriteria("Toto", null).size());
    }

    @Test
    public void findAllWithParams() {
        List<Produit> produitList = produitRepository.findAllWithParams(null, null, 8.0, 9.0, null);
        //Assert.assertEquals(1, produitList.size());
        System.out.println("====================================================================================================");
        System.out.println(produitList.toString());
        //produitList.stream().forEach(System.out::println);
        System.out.println("====================================================================================================");
    }

    //Entity Builders
    @NotNull
    private static Produit buildProduit(int numb) {
        Produit produit = new Produit();
        //Propduct
        produit.setReferenceProduit("A05A8" + numb);
        produit.setDescription(randomAlphabetic(10));
        produit.setNom("Livre :: " + numb);
        produit.setPrixHT(numb * 10);
        //Category list
        produit.setCategories(new ArrayList<>());
        //Avis list
        produit.setAvisClients(new ArrayList<>());

        //Create category list
        List<Categorie> categorieList = new ArrayList<>();
        categorieList.add(buildCategorie());
        produit.setCategories(categorieList);
        //Create avis list
        List<AvisClient> avisClientList = new ArrayList<>();
        for (int i=0; i<2; i++) {
            avisClientList.add(buildAvisClient(produit));
        }
        produit.setAvisClients(avisClientList);
        return produit;
    }

    /**
     * Category builder
     *
     * @return
     */
    @NotNull
    private static Categorie buildCategorie() {
        Categorie categorie = new Categorie();
        categorie.setNomCategorie("Livre cat");
        categorie.setBorneGauche(Integer.parseInt(randomNumeric(1)));
        categorie.setBorneDroit(Integer.parseInt(randomNumeric(1)));
        categorie.setLevel(Integer.parseInt(randomNumeric(1)));
        return categorie;
    }

    /**
     * Avis client builder
     *
     * @return
     */
    @NotNull
    private static AvisClient buildAvisClient(Produit produit) {
        AvisClient avisClient = new AvisClient();
        avisClient.setDescription(randomAlphabetic(10));
        avisClient.setNote(Integer.parseInt(randomNumeric(1)));
        avisClient.setProduit(produit);
        return avisClient;
    }
}
