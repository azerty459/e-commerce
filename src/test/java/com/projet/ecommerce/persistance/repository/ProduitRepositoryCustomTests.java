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

    private static final Produit TEMP_PRODUCT_1;
    private static final Produit TEMP_PRODUCT_2;
    private static final Produit TEMP_PRODUCT_3;
    private static final Produit TEMP_PRODUCT_4;
    private static final Produit TEMP_PRODUCT_5;

    // TODO fix les catégories, qu'est ce que 'borne sup' et 'borne inf' ?

    private static final Categorie TEMP_CATEGORIE_1;
    private static final Categorie TEMP_CATEGORIE_2;
    private static final Categorie TEMP_CATEGORIE_3;
    private static final Categorie TEMP_CATEGORIE_4;

    private static final AvisClient TEMP_AVIS_CLIENT_1;
    private static final AvisClient TEMP_AVIS_CLIENT_2;
    private static final AvisClient TEMP_AVIS_CLIENT_3;
    private static final AvisClient TEMP_AVIS_CLIENT_4;


    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_PRODUCT_1 = new Produit();
        TEMP_PRODUCT_1.setReferenceProduit("A05A87");
        TEMP_PRODUCT_1.setNom("Crayon");
        TEMP_PRODUCT_1.setPrixHT(10.5f);
        TEMP_PRODUCT_1.setDescription("super stylo");
        TEMP_PRODUCT_1.setCategories(new ArrayList<>());

        TEMP_PRODUCT_2 = new Produit();
        TEMP_PRODUCT_2.setReferenceProduit("485695");
        TEMP_PRODUCT_2.setNom("Crayon de bois");
        TEMP_PRODUCT_2.setPrixHT(9.6f);
        TEMP_PRODUCT_2.setDescription("super produit");
        TEMP_PRODUCT_2.setCategories(new ArrayList<>());

        TEMP_PRODUCT_3 = new Produit();
        TEMP_PRODUCT_3.setReferenceProduit("784596");
        TEMP_PRODUCT_3.setNom("Harry Potter 1");
        TEMP_PRODUCT_3.setPrixHT(3.7f);
        TEMP_PRODUCT_3.setDescription("beau livre");
        TEMP_PRODUCT_3.setCategories(new ArrayList<>());

        TEMP_PRODUCT_4 = new Produit();
        TEMP_PRODUCT_4.setReferenceProduit("548485");
        TEMP_PRODUCT_4.setNom("Ordinateur portable");
        TEMP_PRODUCT_4.setPrixHT(50.1f);
        TEMP_PRODUCT_4.setDescription("pc portable");
        TEMP_PRODUCT_4.setCategories(new ArrayList<>());

        TEMP_PRODUCT_5 = new Produit();
        TEMP_PRODUCT_5.setReferenceProduit("659959");
        TEMP_PRODUCT_5.setNom("Ordinateur fixe");
        TEMP_PRODUCT_5.setPrixHT(7f);
        TEMP_PRODUCT_5.setDescription("pc fixe");
        TEMP_PRODUCT_5.setCategories(new ArrayList<>());

        TEMP_CATEGORIE_1 = new Categorie();
        TEMP_CATEGORIE_1.setNomCategorie("Fournitures");
        TEMP_CATEGORIE_1.setBorneGauche(1);
        TEMP_CATEGORIE_1.setBorneDroit(2);
        TEMP_CATEGORIE_1.setLevel(1);
        TEMP_CATEGORIE_1.setProduits(new ArrayList<>());

        TEMP_CATEGORIE_2 = new Categorie();
        TEMP_CATEGORIE_2.setNomCategorie("Livre");
        TEMP_CATEGORIE_2.setBorneGauche(1);
        TEMP_CATEGORIE_2.setBorneDroit(2);
        TEMP_CATEGORIE_2.setLevel(1);
        TEMP_CATEGORIE_2.setProduits(new ArrayList<>());

        TEMP_CATEGORIE_3 = new Categorie();
        TEMP_CATEGORIE_3.setNomCategorie("Appareil éléctronique");
        TEMP_CATEGORIE_3.setBorneGauche(1);
        TEMP_CATEGORIE_3.setBorneDroit(2);
        TEMP_CATEGORIE_3.setLevel(1);
        TEMP_CATEGORIE_3.setProduits(new ArrayList<>());

        TEMP_CATEGORIE_4 = new Categorie();
        TEMP_CATEGORIE_4.setNomCategorie("Ordinateur");
        TEMP_CATEGORIE_4.setBorneGauche(1);
        TEMP_CATEGORIE_4.setBorneDroit(3);
        TEMP_CATEGORIE_4.setLevel(2);
        TEMP_CATEGORIE_4.setProduits(new ArrayList<>());

        // TODO ne pas set la catégorie mais l'inclure en BDD?

        TEMP_AVIS_CLIENT_1 = new AvisClient();
        TEMP_AVIS_CLIENT_1.setNote(5);
        TEMP_AVIS_CLIENT_1.setProduit(TEMP_PRODUCT_1);

        TEMP_AVIS_CLIENT_2 = new AvisClient();
        TEMP_AVIS_CLIENT_2.setNote(5);
        TEMP_AVIS_CLIENT_2.setProduit(TEMP_PRODUCT_1);


        TEMP_AVIS_CLIENT_3 = new AvisClient();
        TEMP_AVIS_CLIENT_3.setNote(5);
        TEMP_AVIS_CLIENT_3.setProduit(TEMP_PRODUCT_1);


        TEMP_AVIS_CLIENT_4 = new AvisClient();
        TEMP_AVIS_CLIENT_4.setNote(5);
        TEMP_AVIS_CLIENT_4.setProduit(TEMP_PRODUCT_1);

        // TEMP_PRODUCT_1 produit Crayon => TEMP_CATEGORIE_1 catégorie Fournitures
        Collection<Categorie> categorieCollection = TEMP_PRODUCT_1.getCategories();
        categorieCollection.add(TEMP_CATEGORIE_1);
        TEMP_PRODUCT_1.setCategories(new ArrayList<>(categorieCollection));

        // TEMP_PRODUCT_2 produit Crayon de bois => TEMP_CATEGORIE_1 catégorie Fournitures
        categorieCollection = TEMP_PRODUCT_2.getCategories();
        categorieCollection.add(TEMP_CATEGORIE_1);
        TEMP_PRODUCT_2.setCategories(new ArrayList<>(categorieCollection));

        // TEMP_PRODUCT_3 produit Harry Potter 1 => TEMP_CATEGORIE_2 catégorie Livre
        categorieCollection = TEMP_PRODUCT_3.getCategories();
        categorieCollection.add(TEMP_CATEGORIE_2);
        TEMP_PRODUCT_3.setCategories(new ArrayList<>(categorieCollection));

        // TEMP_PRODUCT_4 produit Ordinateur portable => TEMP_CATEGORIE_4 catégorie Appareil éléctronique
        categorieCollection = TEMP_PRODUCT_4.getCategories();
        categorieCollection.add(TEMP_CATEGORIE_4);
        TEMP_PRODUCT_4.setCategories(new ArrayList<>(categorieCollection));

        // TEMP_PRODUCT_5 produit Ordinateur fixe => TEMP_CATEGORIE_4 catégorie Appareil éléctronique
        categorieCollection = TEMP_PRODUCT_5.getCategories();
        categorieCollection.add(TEMP_CATEGORIE_4);
        TEMP_PRODUCT_5.setCategories(new ArrayList<>(categorieCollection));
    }

    @Before
    public void insertProduit() {
        produitRepository.save(TEMP_PRODUCT_1);
        produitRepository.save(TEMP_PRODUCT_2);
        produitRepository.save(TEMP_PRODUCT_3);
        produitRepository.save(TEMP_PRODUCT_4);
        produitRepository.save(TEMP_PRODUCT_5);

        avisClientRepository.save(TEMP_AVIS_CLIENT_1);
        avisClientRepository.save(TEMP_AVIS_CLIENT_2);
        avisClientRepository.save(TEMP_AVIS_CLIENT_3);
        avisClientRepository.save(TEMP_AVIS_CLIENT_4);
    }

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private AvisClientRepository avisClientRepository;

    @Test
    // Je teste que la méthode si l'on l'appelle avec null dans les deux paramètres, elle retourne une collection de produit.
    public void findAllWithCriteriaByNull() {
        Collection<Produit> produitCollection = produitRepository.findAllWithCriteria(null, null);
        Assert.assertNotNull(produitCollection);
    }

    @Test
    public void findAllWithCriteriaByRef() {
        Collection<Produit> produitCollection = produitRepository.findAllWithCriteria("A05A87", null);
        Assert.assertEquals(1, produitCollection.size());
        Assert.assertEquals(0, produitRepository.findAllWithCriteria("A05A88", null).size());
    }

    @Test
    @Transactional
    public void findAllWithCriteriaByCat() {
        List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findAllWithCriteria(null, "Livre"));
        Assert.assertEquals(1, retourProduitCollection.size());
        Assert.assertEquals(retourProduitCollection.get(0).getReferenceProduit(), TEMP_PRODUCT_1.getReferenceProduit());
        Assert.assertEquals(retourProduitCollection.get(0).getNom(), TEMP_PRODUCT_1.getNom());
        Assert.assertEquals(retourProduitCollection.get(0).getPrixHT(), TEMP_PRODUCT_1.getPrixHT(), 0);
        Assert.assertEquals(retourProduitCollection.get(0).getCategories().get(0).getNomCategorie(), TEMP_CATEGORIE_3.getNomCategorie());

        Assert.assertEquals(0, produitRepository.findAllWithCriteria("Toto", null).size());
    }

    @Test
    public void findWithFiltersAverageLowerBoundWithCriteria() {
        Collection<Produit> produitCollection = produitRepository.findWithFiltersWithCriteria(
                2.0f,
                null,
                null,
                null,
                null,
                null);
        Assert.assertEquals(1, produitCollection.size());
    }

    @Test
    public void findWithFiltersAverageUppperBoundWithCriteria() {
        Collection<Produit> produitCollection = produitRepository.findWithFiltersWithCriteria(
                null,
                4.0f,
                null,
                null,
                null,
                null);
        Assert.assertEquals(1, produitCollection.size());
    }

    @Test
    public void findWithFiltersFullNameWithCriteria() {
        Collection<Produit> produitCollection = produitRepository.findWithFiltersWithCriteria(
                null,
                null,
                "Ordinateur portable",
                 null,
                 null,
                 null);
        Assert.assertEquals(1, produitCollection.size());
    }

    @Test
    public void findWithFiltersPartNameWithCriteria() {
        Collection<Produit> produitCollection = produitRepository.findWithFiltersWithCriteria(
                null,
                null,
                null,
                "Crayon",
                null,
                null);
        Assert.assertEquals(2, produitCollection.size());
    }

    @Test
    public void findWithFiltersSameCatWithCriteria() {
        Collection<Produit> produitCollection = produitRepository.findWithFiltersWithCriteria(
                null,
                null,
                null,
                null,
                "Fournitures",
                null);
        Assert.assertEquals(2, produitCollection.size());
    }


}
