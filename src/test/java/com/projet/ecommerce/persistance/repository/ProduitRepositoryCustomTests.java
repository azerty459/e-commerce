package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.impl.CategorieRepositoryCustomImpl;
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

    private static Produit TEMP_INSERT;
    private static Categorie TEMP_CATEGORIE;
    private static Caracteristique TEMP_CARACTERISTIQUE;
    private static TypeCaracteristique TEMP_TYPE;


    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        constructProduitTestCase();
    }

    private static void constructProduitTestCase() {
        TEMP_INSERT = new Produit();
        TEMP_INSERT.setReferenceProduit("A05A87");
        TEMP_INSERT.setNom("mon produit");
        TEMP_INSERT.setPrixHT(8.7f);
        TEMP_INSERT.setDescription("joli produit");
        TEMP_INSERT.setCategories(new ArrayList<>());

        TEMP_CATEGORIE = new Categorie();
        TEMP_CATEGORIE.setNomCategorie("Livre");
        TEMP_CATEGORIE.setBorneGauche(1);
        TEMP_CATEGORIE.setBorneDroit(2);
        TEMP_CATEGORIE.setLevel(1);
        TEMP_CATEGORIE.setProduits(new ArrayList<>());

        TEMP_CARACTERISTIQUE = new Caracteristique();
        TEMP_TYPE = new TypeCaracteristique();
        TEMP_TYPE.setType("note");
        TEMP_CARACTERISTIQUE.setType(TEMP_TYPE);
        TEMP_CARACTERISTIQUE.setValeur("5");
        TEMP_CARACTERISTIQUE.setProduit(TEMP_INSERT);
        TEMP_INSERT.setCaracteristiques(new ArrayList<>());
        List<Caracteristique> caracteristiques = new ArrayList<>();
        caracteristiques.add(TEMP_CARACTERISTIQUE);
        TEMP_INSERT.setCaracteristiques(caracteristiques);

        Collection<Categorie> categorieCollection = TEMP_INSERT.getCategories();
        categorieCollection.add(TEMP_CATEGORIE);
        TEMP_INSERT.setCategories(new ArrayList<>(categorieCollection));
    }

    @Before
    public void insertProduit() {
        typeCaracteristiqueRepository.save(TEMP_TYPE);
        produitRepository.save(TEMP_INSERT);
    }
    @Autowired
    TypeCaracteristiqueRepository typeCaracteristiqueRepository;
    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CategorieRepositoryCustomImpl categorieRepositoryCustom;

    @Test
    public void testFindAllProduitNotNullAndHasElements() {
        constructProduitTestCase();
        List<Produit> result = produitRepository.findAllProduit();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void testFindAllProduitByAverageNote() {
        constructProduitTestCase();
        final float noteA = 2;
        final float noteB = 6;
        List<Produit> result = produitRepository.findAllProduitByNote(noteA, noteB);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() == 1);
    }

    @Test
    public void testFindAllProduitByAverageNom() {
        constructProduitTestCase();
        List<Produit> result = produitRepository.findAllProduitByNom("mon produit");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() == 1);
    }

    @Test
    public void testFindAllProduitLikeNom() {
        constructProduitTestCase();
        List<Produit> result = produitRepository.findAllProduitLikeNom("mon produit");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() == 1);
    }

    @Test
    public void testFindAllProduitByCategorie() {
        constructProduitTestCase();
        List<Produit> result = produitRepository.findAllProduitByCategorie(TEMP_CATEGORIE);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() > 0);
    }

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
}
