package com.projet.ecommerce.integration;

import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.CategorieSupprimeBusiness;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.CategorieSupprimeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CategorieRestaurationTests {

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
    }

    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CategorieBusiness categorieBusiness;

    @Autowired
    private CategorieSupprimeBusiness categorieSupprimeBusiness;

    @Autowired
    private CategorieSupprimeRepository categorieSupprimeRepository;

    @Test
    public void addMoveRestore() {
        categorieBusiness.addParent("cat1");
        Categorie cat1 = new Categorie();
        cat1.setLevel(1);
        cat1.setBorneGauche(1);
        cat1.setBorneDroit(2);
        cat1.setNomCategorie("cat1");
        cat1.setProduits(new ArrayList<>());
        ArrayList<Categorie> categories = new ArrayList<>(categorieRepository.findAll());
        Assert.assertEquals(1, categories.size());
        compareCategorieWithoutId(cat1, categories.get(0));


    }

    private void compareCategorieWithoutId(Categorie categorie1, Categorie categorie2) {
        Assert.assertEquals(categorie2.getBorneDroit(), categorie1.getBorneDroit());
        Assert.assertEquals(categorie2.getNomCategorie(), categorie1.getNomCategorie());
        Assert.assertEquals(categorie2.getBorneGauche(), categorie1.getBorneGauche());
        Assert.assertEquals(categorie2.getLevel(), categorie1.getLevel());
        Assert.assertEquals(categorie2.getProduits(), categorie1.getProduits());
    }

    private void compareCategorieWithId(Categorie categorie1, Categorie categorie2) {
        Assert.assertEquals(categorie2.getBorneDroit(), categorie1.getBorneDroit());
        Assert.assertEquals(categorie2.getNomCategorie(), categorie1.getNomCategorie());
        Assert.assertEquals(categorie2.getBorneGauche(), categorie1.getBorneGauche());
        Assert.assertEquals(categorie2.getLevel(), categorie1.getLevel());
        Assert.assertEquals(categorie2.getProduits(), categorie1.getProduits());
        Assert.assertEquals(categorie2.getIdCategorie(), categorie1.getIdCategorie());
    }
}
