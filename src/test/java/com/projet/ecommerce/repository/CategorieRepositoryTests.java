package com.projet.ecommerce.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategorieRepositoryTests {
    private static final Categorie TEMP_INSERT = new Categorie();
    private static final Categorie TEMP_DELETE = new Categorie();
    private static final Categorie TEMP_UPDATE = new Categorie();
    private static final Categorie TEMP_GET = new Categorie();

    static {
        //Permet d'écraser la config application.properties par application-test.properties pour que la base de données en mémoire soit prise en compte et non celle de postgreSQL.
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
    public void insertCategorie() {
        Categorie save = categorieRepository.save(TEMP_INSERT);
        Assert.assertNotNull(save);

        Categorie temp = categorieRepository.findById(TEMP_INSERT.getNomCategorie()).orElse(null);
        Assert.assertNotNull(temp);
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
    public void getCategorieByID() {
        Assert.assertNotNull(categorieRepository.save(TEMP_GET));

        Categorie temp = categorieRepository.findById(TEMP_GET.getNomCategorie()).orElse(null);
        Assert.assertNotNull("Produit ne peut pas être null", temp);
        Assert.assertEquals(TEMP_GET.getBorneDroit(), temp.getBorneDroit());
        Assert.assertEquals(TEMP_GET.getBorneGauche(), temp.getBorneGauche());
        Assert.assertEquals(TEMP_GET.getNomCategorie(), temp.getNomCategorie());
    }

    @Test
    public void updateCategorie() {
        categorieRepository.save(TEMP_UPDATE);
        Categorie retour = categorieRepository.findById(TEMP_UPDATE.getNomCategorie()).orElse(null);
        Assert.assertEquals(TEMP_UPDATE.getNomCategorie(), retour.getNomCategorie());
        Assert.assertEquals(TEMP_UPDATE.getBorneGauche(), retour.getBorneGauche());
        Assert.assertEquals(TEMP_UPDATE.getBorneDroit(), retour.getBorneDroit());

        TEMP_UPDATE.setBorneGauche(6);
        TEMP_UPDATE.setBorneDroit(7);
        TEMP_UPDATE.setNomCategorie("Test");
        Assert.assertNotNull(categorieRepository.save(TEMP_UPDATE));

        retour = categorieRepository.findById(TEMP_UPDATE.getNomCategorie()).orElse(null);
        Assert.assertEquals(TEMP_UPDATE.getNomCategorie(), retour.getNomCategorie());
        Assert.assertEquals(TEMP_UPDATE.getBorneDroit(), retour.getBorneDroit());
        Assert.assertEquals(TEMP_UPDATE.getBorneGauche(), retour.getBorneGauche());
    }

    @Test
    public void deleteCategorie() {
        Assert.assertNotNull(categorieRepository.save(TEMP_DELETE));
        categorieRepository.delete(TEMP_DELETE);
        Assert.assertFalse(categorieRepository.findById(TEMP_DELETE.getNomCategorie()).isPresent());
    }

    @After
    public void end() {
        categorieRepository.delete(TEMP_DELETE);
        categorieRepository.delete(TEMP_GET);
        categorieRepository.delete(TEMP_INSERT);
        categorieRepository.delete(TEMP_UPDATE);
    }
}
