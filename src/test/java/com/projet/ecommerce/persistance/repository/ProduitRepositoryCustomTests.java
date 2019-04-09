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
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProduitRepositoryCustomTests {

    private static final Produit TEMP_INSERT;
    private static final Produit TEMP_INSERT2;
    private static final Categorie TEMP_CATEGORIE;
    private static final Categorie TEMP_CATEGORIE2;
    private static final AvisClient TEMP_AVIS_CLIENT;
    private static final AvisClient TEMP_AVIS_CLIENT2;
    private static final AvisClient TEMP_AVIS_CLIENT3;
    private static final AvisClient TEMP_AVIS_CLIENT4;

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT = new Produit();
        TEMP_INSERT.setReferenceProduit("A05A87");
        TEMP_INSERT.setPrixHT(8.7f);
        TEMP_INSERT.setDescription("joli produit");
        TEMP_INSERT.setCategories(new ArrayList<>());
        TEMP_INSERT.setNom("Test");

        TEMP_AVIS_CLIENT = new AvisClient();
        TEMP_AVIS_CLIENT.setId(1);
        TEMP_AVIS_CLIENT.setNote(5);
        TEMP_AVIS_CLIENT.setProduit(TEMP_INSERT);

        TEMP_AVIS_CLIENT2 = new AvisClient();
        TEMP_AVIS_CLIENT2.setId(2);
        TEMP_AVIS_CLIENT2.setNote(6);
        TEMP_AVIS_CLIENT2.setProduit(TEMP_INSERT);

        TEMP_CATEGORIE = new Categorie();
        TEMP_CATEGORIE.setNomCategorie("Livre");
        TEMP_CATEGORIE.setBorneGauche(1);
        TEMP_CATEGORIE.setBorneDroit(2);
        TEMP_CATEGORIE.setLevel(1);
        TEMP_CATEGORIE.setProduits(new ArrayList<>());

        TEMP_INSERT2 = new Produit();
        TEMP_INSERT2.setReferenceProduit("A05A89");
        TEMP_INSERT2.setPrixHT(8.0f);
        TEMP_INSERT2.setDescription("beau produit");
        TEMP_INSERT2.setCategories(new ArrayList<>());
        TEMP_INSERT2.setNom("Test produit");

        TEMP_AVIS_CLIENT3 = new AvisClient();
        TEMP_AVIS_CLIENT3.setId(3);
        TEMP_AVIS_CLIENT3.setNote(7);
        TEMP_AVIS_CLIENT3.setProduit(TEMP_INSERT2);

        TEMP_AVIS_CLIENT4 = new AvisClient();
        TEMP_AVIS_CLIENT4.setId(4);
        TEMP_AVIS_CLIENT4.setNote(6);
        TEMP_AVIS_CLIENT4.setProduit(TEMP_INSERT2);

        TEMP_CATEGORIE2 = new Categorie();
        TEMP_CATEGORIE2.setNomCategorie("Jouet");
        TEMP_CATEGORIE2.setBorneGauche(1);
        TEMP_CATEGORIE2.setBorneDroit(2);
        TEMP_CATEGORIE2.setLevel(1);
        TEMP_CATEGORIE2.setProduits(new ArrayList<>());

        Collection<Categorie> categorieCollection = TEMP_INSERT.getCategories();
        categorieCollection.add(TEMP_CATEGORIE);
        categorieCollection.add(TEMP_CATEGORIE2);
        TEMP_INSERT.setCategories(new ArrayList<>(categorieCollection));

        List<AvisClient> avisClientsCollection = new ArrayList<>();
        avisClientsCollection.add(TEMP_AVIS_CLIENT);
        avisClientsCollection.add(TEMP_AVIS_CLIENT2);
        TEMP_INSERT.setAvisClients(avisClientsCollection);

        Collection<Categorie> categorieCollection2 = TEMP_INSERT2.getCategories();
        categorieCollection2.add(TEMP_CATEGORIE);
        TEMP_INSERT2.setCategories(new ArrayList<>(categorieCollection2));

        List<AvisClient> avisClientsCollection2 = new ArrayList<>();
        avisClientsCollection2.add(TEMP_AVIS_CLIENT3);
        avisClientsCollection2.add(TEMP_AVIS_CLIENT4);
        TEMP_INSERT2.setAvisClients(avisClientsCollection2);
    }

    @Before
    public void insertProduit() {
        produitRepository.save(TEMP_INSERT);
        produitRepository.save(TEMP_INSERT2);
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

    /*@Test
    @Transactional
    public void findAllWithCriteriaByCat() {
        List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findAllWithCriteria(null, "Livre"));
        Assert.assertEquals(2, retourProduitCollection.size());
        Assert.assertEquals(retourProduitCollection.get(0).getReferenceProduit(), TEMP_INSERT.getReferenceProduit());
        Assert.assertEquals(retourProduitCollection.get(0).getNom(), TEMP_INSERT.getNom());
        Assert.assertEquals(retourProduitCollection.get(0).getPrixHT(), TEMP_INSERT.getPrixHT(), 0);
        Assert.assertEquals(retourProduitCollection.get(0).getCategories().get(0).getNomCategorie(), TEMP_CATEGORIE.getNomCategorie());

        Assert.assertEquals(0, produitRepository.findAllWithCriteria("Toto", null).size());
    }*/

    @Test
    public void findProduitsWithNoCriteria(){
        List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findProduitsWithCriteria(null, null,null, null, null));
        Assert.assertEquals(2,retourProduitCollection.size());
    }

    @Test
    public void findProduitsWithNomProduitCriteria(){
        List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findProduitsWithCriteria(null,null,"Test", null, null));
        Assert.assertEquals(1,retourProduitCollection.size());
        Assert.assertEquals("Test",retourProduitCollection.get(0).getNom());
    }

    @Test
    public void findProduitsWithPartieNomProduitCriteria(){
        List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findProduitsWithCriteria(null,null, null,"es", null));
        Assert.assertEquals(2,retourProduitCollection.size());
        Assert.assertEquals("Test",retourProduitCollection.get(0).getNom());
    }

    @Test
    public void findProduitsWithCategorieCriteria(){
        List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findProduitsWithCriteria(null,null,null, null, "Livre"));
        Assert.assertEquals(2,retourProduitCollection.size());
        Assert.assertEquals(1,retourProduitCollection.get(0).getCategories().stream().map(Categorie::getNomCategorie).filter(x -> x.equals("Livre")).count());
        Assert.assertEquals(1,retourProduitCollection.get(0).getCategories().stream().map(Categorie::getNomCategorie).filter(x -> x.equals("Jouet")).count());
        Assert.assertEquals(1,retourProduitCollection.get(1).getCategories().stream().map(Categorie::getNomCategorie).filter(x -> x.equals("Livre")).count());
        Assert.assertEquals(0,retourProduitCollection.get(1).getCategories().stream().map(Categorie::getNomCategorie).filter(x -> x.equals("Jouet")).count());
    }

    @Test
    public void findProduitsWithBorneSupAvisClientCriteria(){
        List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findProduitsWithCriteria(5.5,10.0,null, null, null));
        Assert.assertEquals(1,retourProduitCollection.size());
        Assert.assertEquals(true,retourProduitCollection.get(0).getAvisClients().stream().mapToDouble(AvisClient::getNote).average().getAsDouble() > 5.5);
        Assert.assertEquals(true,retourProduitCollection.get(0).getAvisClients().stream().mapToDouble(AvisClient::getNote).average().getAsDouble() < 10.0);
    }

    @Test
    public void findProduitsWithAllCriteria(){
        List<Produit> retourProduitCollection = new ArrayList<>(produitRepository.findProduitsWithCriteria(5.5,10.0,"Test produit", "es", "Livre"));
        Assert.assertEquals(1,retourProduitCollection.size());
    }

}
