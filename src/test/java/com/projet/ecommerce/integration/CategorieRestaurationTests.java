package com.projet.ecommerce.integration;

import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.CategorieSupprimeBusiness;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.CategorieSupprime;
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
import java.util.Collection;

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
    public void addMoveSwapAndRestore() {

        // AJOUT PARENT
        categorieBusiness.addParent("cat parent");
        Categorie catParent = new Categorie();
        catParent.setLevel(1);
        catParent.setBorneGauche(1);
        catParent.setBorneDroit(2);
        catParent.setNomCategorie("cat parent");
        catParent.setProduits(new ArrayList<>());
        ArrayList<Categorie> categories = new ArrayList<>(categorieRepository.findAll());
        Assert.assertEquals(1, categories.size());
        compareCategorieWithoutId(catParent, categories.get(0));

        //AJOUT ENFANT
        categorieBusiness.addEnfant("cat enfant", categories.get(0).getIdCategorie());
        Categorie catEnfant = new Categorie();
        catEnfant.setLevel(2);
        catEnfant.setBorneGauche(2);
        catEnfant.setBorneDroit(3);
        catParent.setBorneDroit(4);
        catEnfant.setNomCategorie("cat enfant");
        catEnfant.setProduits(new ArrayList<>());
        ArrayList<Categorie> categoriesEnfant = new ArrayList<>(categorieRepository.findByNomCategorie("cat enfant"));
        ArrayList<Categorie> categoriesParent = new ArrayList<>(categorieRepository.findByNomCategorie("cat parent"));
        Assert.assertEquals(1, categoriesEnfant.size());
        Assert.assertEquals(1, categoriesParent.size());
        compareCategorieWithoutId(catParent, categoriesParent.get(0));
        compareCategorieWithoutId(catEnfant, categoriesEnfant.get(0));

        //DEPLACEMENT ENFANT VERS LVL1
        categorieBusiness.moveCategorie(categoriesEnfant.get(0).getIdCategorie(), 0);
        catEnfant.setLevel(1);
        catEnfant.setBorneGauche(3);
        catEnfant.setBorneDroit(4);
        catParent.setBorneDroit(2);
        categoriesEnfant = new ArrayList<>(categorieRepository.findByNomCategorie("cat enfant"));
        categoriesParent = new ArrayList<>(categorieRepository.findByNomCategorie("cat parent"));
        Assert.assertEquals(1, categoriesEnfant.size());
        Assert.assertEquals(1, categoriesParent.size());
        compareCategorieWithoutId(catParent, categoriesParent.get(0));
        compareCategorieWithoutId(catEnfant, categoriesEnfant.get(0));

        //DEPLACEMENT catParent dans catEnfant
        categorieBusiness.moveCategorie(categoriesParent.get(0).getIdCategorie(), categoriesEnfant.get(0).getIdCategorie());
        catParent.setLevel(2);
        catParent.setBorneGauche(2);
        catParent.setBorneDroit(3);
        catEnfant.setBorneGauche(1);
        catEnfant.setBorneDroit(4);
        categoriesEnfant = new ArrayList<>(categorieRepository.findByNomCategorie("cat enfant"));
        categoriesParent = new ArrayList<>(categorieRepository.findByNomCategorie("cat parent"));
        Assert.assertEquals(1, categoriesEnfant.size());
        Assert.assertEquals(1, categoriesParent.size());
        compareCategorieWithoutId(catParent, categoriesParent.get(0));
        compareCategorieWithoutId(catEnfant, categoriesEnfant.get(0));

        //SUPPRESSION catEnfant qui contient catParent
        categorieBusiness.delete(categoriesEnfant.get(0).getIdCategorie());
        categories = new ArrayList<>(categorieRepository.findAll());
        Assert.assertEquals(0, categories.size());
        ArrayList<CategorieSupprime> categoriesSupprime = new ArrayList<>(categorieSupprimeRepository.findAll());
        Assert.assertEquals(2, categoriesSupprime.size());

        //RESTAURATION
        categorieSupprimeBusiness.restoreLastDeletedCategorie(0);
        Assert.assertEquals(1, categoriesEnfant.size());
        Assert.assertEquals(1, categoriesParent.size());
        compareCategorieWithoutId(catParent, categoriesParent.get(0));
        compareCategorieWithoutId(catEnfant, categoriesEnfant.get(0));
    }

    @Test(expected = GraphQLCustomException.class)
    public void addNomEmpty() {
        categorieBusiness.addParent("");
        ArrayList<Categorie> categories = new ArrayList<>(categorieRepository.findAll());
        Assert.assertEquals(0, categories.size());
    }

    @Test
    public void addSameNom() {
        categorieBusiness.addParent("nom");
        categorieBusiness.addParent("nom");
        categorieBusiness.addParent("nom");
        categorieBusiness.addParent("nom");
        categorieBusiness.addEnfant("nom", 1);
        categorieBusiness.addEnfant("nom", 1);
        categorieBusiness.addEnfant("nom", 1);
        Collection<Categorie> categories = categorieRepository.findAll();
        Assert.assertEquals(7, categories.size());

    }

    private void compareCategorieWithoutId(Categorie categorie2, Categorie categorie1) {
        Assert.assertEquals(categorie2.getBorneDroit(), categorie1.getBorneDroit());
        Assert.assertEquals(categorie2.getNomCategorie(), categorie1.getNomCategorie());
        Assert.assertEquals(categorie2.getBorneGauche(), categorie1.getBorneGauche());
        Assert.assertEquals(categorie2.getLevel(), categorie1.getLevel());
        Assert.assertEquals(categorie2.getProduits().size(), categorie1.getProduits().size());
    }

    private void compareCategorieWithId(Categorie categorie1, Categorie categorie2) {
        Assert.assertEquals(categorie2.getBorneDroit(), categorie1.getBorneDroit());
        Assert.assertEquals(categorie2.getNomCategorie(), categorie1.getNomCategorie());
        Assert.assertEquals(categorie2.getBorneGauche(), categorie1.getBorneGauche());
        Assert.assertEquals(categorie2.getLevel(), categorie1.getLevel());
        Assert.assertEquals(categorie2.getProduits().size(), categorie1.getProduits().size());
        Assert.assertEquals(categorie2.getIdCategorie(), categorie1.getIdCategorie());
    }
}
