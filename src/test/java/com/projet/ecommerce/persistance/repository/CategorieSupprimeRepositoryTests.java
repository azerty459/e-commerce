package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.CategorieSupprime;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CategorieSupprimeRepositoryTests {
    private static final CategorieSupprime TEMP_INSERT = new CategorieSupprime();
    private static final CategorieSupprime TEMP_DELETE = new CategorieSupprime();
    private static final CategorieSupprime TEMP_UPDATE = new CategorieSupprime();
    private static final CategorieSupprime TEMP_GET = new CategorieSupprime();

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
    private CategorieSupprimeRepository categorieRepository;

    @Test
    public void insertCategorieSupprime() {
        // On stocke le retour du save pour avoir l'id de la catégorie qui a été sauvegardé
        CategorieSupprime save = categorieRepository.save(TEMP_INSERT);
        Assert.assertNotNull(save);

        CategorieSupprime temp = categorieRepository.findById(save.getIdCategorie()).get();
        Assert.assertNotNull(temp);
        // TODO tester les champs
    }

    @Test
    public void getAllCategorieSupprime() {
        categorieRepository.deleteAll();

        Collection<CategorieSupprime> categorieCollection = categorieRepository.findAll();
        Assert.assertEquals(0, categorieCollection.size());

        categorieRepository.save(TEMP_INSERT);
        categorieCollection = categorieRepository.findAll();
        Assert.assertEquals(1, categorieCollection.size());
    }

    @Test
    public void findById() {
        // On stocke le retour du save pour avoir l'id de la catégorie qui a été sauvegardé
        CategorieSupprime save = categorieRepository.save(TEMP_GET);
        Assert.assertNotNull(save);

        CategorieSupprime temp = categorieRepository.findById(save.getIdCategorie()).orElse(null);
        Assert.assertNotNull("Produit ne peut pas être null", temp);
        Assert.assertEquals(TEMP_GET.getBorneDroit(), temp.getBorneDroit());
        Assert.assertEquals(TEMP_GET.getBorneGauche(), temp.getBorneGauche());
        Assert.assertEquals(TEMP_GET.getNomCategorie(), temp.getNomCategorie());
    }


    @Test
    public void updateCategorieSupprime() {
        categorieRepository.save(TEMP_UPDATE);
        CategorieSupprime retour = categorieRepository.findById(TEMP_UPDATE.getIdCategorie()).orElse(null);
        Assert.assertNotNull(retour.getNomCategorie());
        Assert.assertEquals(TEMP_UPDATE.getNomCategorie(), retour.getNomCategorie());
        Assert.assertEquals(TEMP_UPDATE.getBorneGauche(), retour.getBorneGauche());
        Assert.assertEquals(TEMP_UPDATE.getBorneDroit(), retour.getBorneDroit());

        TEMP_UPDATE.setBorneGauche(6);
        TEMP_UPDATE.setBorneDroit(7);
        TEMP_UPDATE.setNomCategorie("Test");
        Assert.assertNotNull(categorieRepository.save(TEMP_UPDATE));

        retour = categorieRepository.findById(TEMP_UPDATE.getIdCategorie()).orElse(null);
        Assert.assertNotNull(retour.getNomCategorie());
        Assert.assertEquals(TEMP_UPDATE.getNomCategorie(), retour.getNomCategorie());
        Assert.assertEquals(TEMP_UPDATE.getBorneDroit(), retour.getBorneDroit());
        Assert.assertEquals(TEMP_UPDATE.getBorneGauche(), retour.getBorneGauche());
    }

    @Test
    public void deleteCategorieSupprime() {
        Assert.assertNotNull(categorieRepository.save(TEMP_DELETE));
        categorieRepository.delete(TEMP_DELETE);
        Assert.assertFalse(categorieRepository.findById(TEMP_DELETE.getIdCategorie()).isPresent());
    }

    @After
    public void end() {
        categorieRepository.deleteAll();
    }
}
