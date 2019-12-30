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
    private static final AvisClient TEMP_AVIS_INSERT;
    private static final AvisClient TEMP_AVIS_INSERT2;

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT = new Produit();
        TEMP_INSERT.setNom("Mon livre de Test");
        TEMP_INSERT.setReferenceProduit("A05A87");
        TEMP_INSERT.setPrixHT(8.7f);
        TEMP_INSERT.setDescription("joli produit");
        List<Categorie> categories =new ArrayList<>();
        TEMP_INSERT.setCategories(categories);

        TEMP_AVIS_INSERT = new AvisClient();
        TEMP_AVIS_INSERT.setProduit(TEMP_INSERT);
        TEMP_AVIS_INSERT.setNote(5);

        TEMP_AVIS_INSERT2 = new AvisClient();
        TEMP_AVIS_INSERT2.setProduit(TEMP_INSERT);
        TEMP_AVIS_INSERT2.setNote(4);

        TEMP_CATEGORIE = new Categorie();
        TEMP_CATEGORIE.setNomCategorie("Livre");
        TEMP_CATEGORIE.setBorneGauche(1);
        TEMP_CATEGORIE.setBorneDroit(2);
        TEMP_CATEGORIE.setLevel(1);
        TEMP_CATEGORIE.setProduits(new ArrayList<>());

        Collection<Categorie> categorieCollection = TEMP_INSERT.getCategories();
        categorieCollection.add(TEMP_CATEGORIE);
        TEMP_INSERT.setCategories(new ArrayList<>(categorieCollection));
    }

    @Before
    public void insertProduit() {

        produitRepository.save(TEMP_INSERT);
        avisClientRepository.save(TEMP_AVIS_INSERT);
        avisClientRepository.save(TEMP_AVIS_INSERT2);

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
    public void findAllWithOptionnalFilter() {
        Collection<Produit> produitListWithNullValue = produitRepository.findAllWithOptionnalFilter(null, null, null, null, null);
        Assert.assertEquals(1,produitListWithNullValue.size());

        Collection<Produit> produitListWithMiniOnly = produitRepository.findAllWithOptionnalFilter(1D, null, null, null, null);
        Assert.assertEquals(1,produitListWithMiniOnly.size());

        Collection<Produit> produitListWithMaxiOnly = produitRepository.findAllWithOptionnalFilter(null, 5D, null, null, null);
        Assert.assertEquals(1,produitListWithMaxiOnly.size());

        Collection<Produit> produitListWithMinAndMaxi = produitRepository.findAllWithOptionnalFilter(1D, 5D, null, null, null);
        Assert.assertEquals(1,produitListWithMinAndMaxi.size());

        Collection<Produit> produitListWithExactName = produitRepository.findAllWithOptionnalFilter(null, null, "Mon Livre de Test", null, null);
        Assert.assertEquals(1,produitListWithExactName.size());

        Collection<Produit> produitListWithExactWrongName = produitRepository.findAllWithOptionnalFilter(null, null, "Mon Livre Test", null, null);
        Assert.assertEquals(0,produitListWithExactWrongName.size());

        Collection<Produit> produitListContainName = produitRepository.findAllWithOptionnalFilter(null, null, null, "mon", null);
        Assert.assertEquals(1,produitListContainName.size());

        Collection<Produit> produitListWithCategorieId1 = produitRepository.findAllWithOptionnalFilter(null, null, null, null, 1);
        Assert.assertEquals(1,produitListWithCategorieId1.size());

    }

}
