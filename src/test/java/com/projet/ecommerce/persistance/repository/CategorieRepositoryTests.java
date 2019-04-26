package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CategorieRepositoryTests {
    private static final Categorie TEMP_INSERT = new Categorie();
    private static final Categorie TEMP_DELETE = new Categorie();
    private static final Categorie TEMP_UPDATE = new Categorie();
    private static final Categorie TEMP_GET = new Categorie();

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT.setNomCategorie("Transport");
        TEMP_INSERT.setBorneGauche(1);
        TEMP_INSERT.setBorneDroit(8);

        TEMP_DELETE.setNomCategorie("Aérien");
        TEMP_DELETE.setBorneGauche(2);
        TEMP_DELETE.setBorneDroit(7);

        TEMP_GET.setNomCategorie("Avion");
        TEMP_GET.setBorneGauche(3);
        TEMP_GET.setBorneDroit(4);

        TEMP_UPDATE.setNomCategorie("Fusée");
        TEMP_UPDATE.setBorneGauche(5);
        TEMP_UPDATE.setBorneDroit(6);
    }

    @Autowired
    private CategorieRepository categorieRepository;

    @Test
    public void testEcarterBornes() {
        Categorie test = new Categorie();
        test.setNomCategorie("Test");
        test.setBorneGauche(1);
        test.setBorneDroit(2);
        test.setLevel(1);

        categorieRepository.save(test);
        categorieRepository.ecarterBornes(1, 4);
        Optional<Categorie> res = categorieRepository.findById(test.getIdCategorie());

        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(6, res.get().getBorneDroit());
    }


    @Test
    public void insertCategorie() {
        // On stocke le retour du save pour avoir l'id de la catégorie qui a été sauvegardé
        Categorie save = categorieRepository.save(TEMP_INSERT);
        Assert.assertNotNull(save);

        Optional<Categorie> temp = categorieRepository.findById(save.getIdCategorie());
        Assert.assertTrue(temp.isPresent());
    }

    @Test
    public void getAllCategorie() {
        categorieRepository.deleteAll();

        Collection<Categorie> categorieCollection = categorieRepository.findAll();
        Assert.assertEquals(0, categorieCollection.size());

        categorieRepository.save(TEMP_INSERT);
        categorieCollection = categorieRepository.findAll();
        Assert.assertEquals(1, categorieCollection.size());
    }

    @Test
    public void findById() {
        // On stocke le retour du save pour avoir l'id de la catégorie qui a été sauvegardé
        Categorie save = categorieRepository.save(TEMP_GET);
        Assert.assertNotNull(save);

        Categorie temp = categorieRepository.findById(save.getIdCategorie()).orElse(null);
        Assert.assertNotNull("Produit ne peut pas être null", temp);
        Assert.assertEquals(TEMP_GET.getBorneDroit(), temp.getBorneDroit());
        Assert.assertEquals(TEMP_GET.getBorneGauche(), temp.getBorneGauche());
        Assert.assertEquals(TEMP_GET.getNomCategorie(), temp.getNomCategorie());
    }

    @Test
    public void findByIdCategorieWithSousCat() {
        // On sauvegarde tout pour avoir des données dans la BDD
        categorieRepository.save(TEMP_GET);
        categorieRepository.save(TEMP_DELETE);
        categorieRepository.save(TEMP_UPDATE);
        // On stocke le retour du save pour avoir l'id de la catégorie qui a été sauvegardé
        Categorie save = categorieRepository.save(TEMP_INSERT);
        Collection<Categorie> categorieCollection = categorieRepository.findByIdCategorieWithSousCat(save.getIdCategorie());
        Assert.assertEquals(4, categorieCollection.size());
    }

    @Test
    public void findByNomCategorie() {
        categorieRepository.save(TEMP_GET);
        Collection<Categorie> categorieCollection = categorieRepository.findByNomCategorie(TEMP_GET.getNomCategorie());
        Assert.assertEquals(1, categorieCollection.size());
    }

    @Test
    public void findByNomCategorieWithSousCat() {
        categorieRepository.save(TEMP_GET);
        categorieRepository.save(TEMP_DELETE);
        categorieRepository.save(TEMP_INSERT);
        categorieRepository.save(TEMP_UPDATE);
        Collection<Categorie> categorieCollection = categorieRepository.findByNomCategorieWithSousCat(TEMP_INSERT.getNomCategorie());
        Assert.assertEquals(4, categorieCollection.size());
    }

    @Test
    public void updateCategorie() {
        categorieRepository.save(TEMP_UPDATE);

        Optional<Categorie> retourOptional = categorieRepository.findById(TEMP_UPDATE.getIdCategorie());
        Assert.assertTrue(retourOptional.isPresent());
        Categorie retour = retourOptional.get();
        Assert.assertNotNull(retour.getNomCategorie());
        Assert.assertEquals(TEMP_UPDATE.getNomCategorie(), retour.getNomCategorie());
        Assert.assertEquals(TEMP_UPDATE.getBorneGauche(), retour.getBorneGauche());
        Assert.assertEquals(TEMP_UPDATE.getBorneDroit(), retour.getBorneDroit());

        TEMP_UPDATE.setBorneGauche(6);
        TEMP_UPDATE.setBorneDroit(7);
        TEMP_UPDATE.setNomCategorie("Test");
        Assert.assertNotNull(categorieRepository.save(TEMP_UPDATE));

        Optional<Categorie> retourOptional2 = categorieRepository.findById(TEMP_UPDATE.getIdCategorie());
        Assert.assertTrue(retourOptional2.isPresent());
        Categorie retour2 = retourOptional2.get();
        Assert.assertNotNull(retour2.getNomCategorie());
        Assert.assertEquals(TEMP_UPDATE.getNomCategorie(), retour2.getNomCategorie());
        Assert.assertEquals(TEMP_UPDATE.getBorneDroit(), retour2.getBorneDroit());
        Assert.assertEquals(TEMP_UPDATE.getBorneGauche(), retour2.getBorneGauche());
    }

    @Test
    public void deleteCategorie() {
        Assert.assertNotNull(categorieRepository.save(TEMP_DELETE));
        categorieRepository.delete(TEMP_DELETE);
        Assert.assertFalse(categorieRepository.findById(TEMP_DELETE.getIdCategorie()).isPresent());
    }

    @Test
    public void countCategories() {
        long resultat = categorieRepository.countCategories();
        Assert.assertEquals(0, resultat);
        categorieRepository.save(TEMP_GET);
        categorieRepository.save(TEMP_INSERT);
        resultat = categorieRepository.countCategories();
        Assert.assertEquals(2, resultat);
    }

    @After
    public void end() {
        categorieRepository.deleteAll();
    }

}
