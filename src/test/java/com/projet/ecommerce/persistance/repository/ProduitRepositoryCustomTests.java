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
    private static final AvisClient TEMP_AVIS_1;
    private static final AvisClient TEMP_AVIS_2;

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT = new Produit();
        TEMP_INSERT.setReferenceProduit("A05A87");
        TEMP_INSERT.setPrixHT(8.7f);
        TEMP_INSERT.setDescription("joli produit");
        TEMP_INSERT.setCategories(new ArrayList<>());
        TEMP_INSERT.setNom("Un super nom");

        TEMP_CATEGORIE = new Categorie();
        TEMP_CATEGORIE.setNomCategorie("Livre");
        TEMP_CATEGORIE.setBorneGauche(1);
        TEMP_CATEGORIE.setBorneDroit(2);
        TEMP_CATEGORIE.setLevel(1);
        TEMP_CATEGORIE.setProduits(new ArrayList<>());

        TEMP_AVIS_1 = new AvisClient();
        TEMP_AVIS_1.setProduit(TEMP_INSERT);
        TEMP_AVIS_1.setDescription("Super produit !!");
        TEMP_AVIS_1.setNote(4);


        TEMP_AVIS_2 = new AvisClient();
        TEMP_AVIS_2.setProduit(TEMP_INSERT);
        TEMP_AVIS_2.setDescription("Pas terrible ...");
        TEMP_AVIS_2.setNote(2);

        List<AvisClient> listeAvis = new ArrayList<>();
        listeAvis.add(TEMP_AVIS_1);
        listeAvis.add(TEMP_AVIS_2);
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
    public void findAllWithJPACriteriaBuilder() {

        produitRepository.deleteAll();
        Collection<Produit> produitCollection = produitRepository.findAllWithJPACriteriaBuilder();
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(0, produitCollection.size());

        produitRepository.save(TEMP_INSERT);
        produitCollection = produitRepository.findAllWithJPACriteriaBuilder();
        Assert.assertEquals(1, produitCollection.size());
    }

    @Test
    public void findByNomWithJPACriteriaBuilder(){

        // Tests sur nom = X
        Collection<Produit> produitCollection = produitRepository.findByNomWithJPACriteriaBuilder("Test aucuns resultats", false);
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(0, produitCollection.size());

        produitCollection = produitRepository.findByNomWithJPACriteriaBuilder(TEMP_INSERT.getNom(), false);
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(1, produitCollection.size());


        // Tests sur nom like X
        produitCollection = produitRepository.findByNomWithJPACriteriaBuilder("%Test%", true);
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(0, produitCollection.size());

        produitCollection = produitRepository.findByNomWithJPACriteriaBuilder("%super%", true);
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(1, produitCollection.size());

    }

    @Test
    public void findByCategorieWithJPACriteriaBuilder(){


        Produit p = new Produit();
        p.setReferenceProduit("AZERTY");
        p.setPrixHT(5f);
        p.setDescription("AZERTYyyyyy");
        p.setCategories(new ArrayList<>());
        p.setNom("Un super nom");

        Produit p2 = new Produit();
        p2.setReferenceProduit("QSDFGH");
        p2.setPrixHT(5f);
        p2.setDescription("QSDFGhhhh");
        p2.setCategories(new ArrayList<>());
        p2.setNom("lorem ipsum");

        Categorie c1 = new Categorie();
        c1.setNomCategorie("Video");
        c1.setBorneGauche(1);
        c1.setBorneDroit(2);
        c1.setLevel(1);
        c1.setProduits(new ArrayList<>());

        Categorie c2 = new Categorie();
        c2.setNomCategorie("Audio");
        c2.setBorneGauche(4);
        c2.setBorneDroit(6);
        c2.setLevel(2);
        c2.setProduits(new ArrayList<>());

        Categorie c3 = new Categorie();
        c3.setNomCategorie("Autre");
        c3.setBorneGauche(3);
        c3.setBorneDroit(5);
        c3.setLevel(2);
        c3.setProduits(new ArrayList<>());

        Categorie c4 = new Categorie();
        c4.setNomCategorie("Livre");
        c4.setBorneGauche(3);
        c4.setBorneDroit(5);
        c4.setLevel(2);
        c4.setProduits(new ArrayList<>());

        Collection<Categorie> categorieCollection = p.getCategories();
        categorieCollection.add(c1);
        categorieCollection.add(c2);
        categorieCollection.add(c4);
        p.setCategories(new ArrayList<>(categorieCollection));

        categorieCollection = p2.getCategories();
        categorieCollection.add(c2);
        categorieCollection.add(c3);
        categorieCollection.add(c4);
        p2.setCategories(new ArrayList<>(categorieCollection));

        produitRepository.save(p);
        produitRepository.save(p2);


        Collection<Produit> produitCollection = produitRepository.findByCategorieWithJPACriteriaBuilder("Test aucuns resultats");
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(0, produitCollection.size());

        produitCollection = produitRepository.findByCategorieWithJPACriteriaBuilder("Livre");
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(3, produitCollection.size());

        produitCollection = produitRepository.findByCategorieWithJPACriteriaBuilder("Audio");
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(2, produitCollection.size());

        produitCollection = produitRepository.findByCategorieWithJPACriteriaBuilder("Video");
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(1, produitCollection.size());

        produitCollection = produitRepository.findByCategorieWithJPACriteriaBuilder("Autre");
        Assert.assertNotNull(produitCollection);
        Assert.assertEquals(1, produitCollection.size());

    }

    @Test
    public void findByNoteWithJPACriteriaBuilder(){

//        produitRepository.findByNoteWithJPACriteriaBuilder(0,0);
//
//        produitRepository.findByNoteWithJPACriteriaBuilder(0,5);
//
//        produitRepository.findByNoteWithJPACriteriaBuilder(5,0);
//
//        produitRepository.findByNoteWithJPACriteriaBuilder(3,3);

    }



}
