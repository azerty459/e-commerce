package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.CategorieSupprime;
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
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CategorieSupprimeRepositoryCustomTests {

    private static final CategorieSupprime LIVRE_SUPPRIME = new CategorieSupprime();
    private static final CategorieSupprime ROMAN_SUPPRIME = new CategorieSupprime();
    private static final CategorieSupprime BIO_SUPPRIME = new CategorieSupprime();
    private static final CategorieSupprime FRANCE_SUPPRIME = new CategorieSupprime();
    private static final CategorieSupprime US = new CategorieSupprime();
    private static final CategorieSupprime CINEMA = new CategorieSupprime();
    private static final CategorieSupprime DRAME = new CategorieSupprime();

    static {

        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
        buildCategories();
    }

    private static void buildCategories() {
        // Arbre des catégories
        LIVRE_SUPPRIME.setNomCategorie("Livre");
        LIVRE_SUPPRIME.setBorneGauche(1);
        LIVRE_SUPPRIME.setBorneDroit(10);
        LIVRE_SUPPRIME.setLevel(1);

        ROMAN_SUPPRIME.setNomCategorie("Roman");
        ROMAN_SUPPRIME.setBorneGauche(2);
        ROMAN_SUPPRIME.setBorneDroit(7);
        ROMAN_SUPPRIME.setLevel(2);

        BIO_SUPPRIME.setNomCategorie("Bio");
        BIO_SUPPRIME.setBorneGauche(8);
        BIO_SUPPRIME.setBorneDroit(9);
        BIO_SUPPRIME.setLevel(2);

        FRANCE_SUPPRIME.setNomCategorie("France");
        FRANCE_SUPPRIME.setBorneGauche(3);
        FRANCE_SUPPRIME.setBorneDroit(4);
        FRANCE_SUPPRIME.setLevel(3);

        US.setNomCategorie("US");
        US.setBorneGauche(5);
        US.setBorneDroit(6);
        US.setLevel(3);

        CINEMA.setNomCategorie("Ciné");
        CINEMA.setBorneGauche(11);
        CINEMA.setBorneDroit(14);
        CINEMA.setLevel(1);

        DRAME.setNomCategorie("Drame");
        DRAME.setBorneGauche(12);
        DRAME.setBorneDroit(13);
        DRAME.setLevel(2);
    }

    @Before
    public void insertCategorie() {
        categorieSupprimeRepository.save(LIVRE_SUPPRIME);
        categorieSupprimeRepository.save(ROMAN_SUPPRIME);
        categorieSupprimeRepository.save(BIO_SUPPRIME);
        categorieSupprimeRepository.save(FRANCE_SUPPRIME);
        categorieSupprimeRepository.save(US);
        categorieSupprimeRepository.save(CINEMA);
        categorieSupprimeRepository.save(DRAME);

    }

    @Autowired
    private CategorieSupprimeRepository categorieSupprimeRepository;

    @Test
    public void findParents() {


        // Création du HashMap d'entrée
        HashMap<Integer, CategorieSupprime> entree = new HashMap<>();
        entree.put(1, ROMAN_SUPPRIME);

        // Exécution de la méthode findParents
        Collection<CategorieSupprime> resultat = categorieSupprimeRepository.findParents(entree);

        Assert.assertNotNull(resultat);
        Assert.assertEquals(3, resultat.size());

        ArrayList<CategorieSupprime> liste = new ArrayList<>(resultat);

        Assert.assertEquals(liste.get(0).getNomCategorie(), ROMAN_SUPPRIME.getNomCategorie());
        Assert.assertEquals(liste.get(0).getBorneDroit(), ROMAN_SUPPRIME.getBorneDroit());
        Assert.assertEquals(liste.get(0).getBorneGauche(), ROMAN_SUPPRIME.getBorneGauche());
        Assert.assertEquals(liste.get(0).getLevel(), ROMAN_SUPPRIME.getLevel());

        Assert.assertEquals(liste.get(1).getNomCategorie(), FRANCE_SUPPRIME.getNomCategorie());
        Assert.assertEquals(liste.get(1).getBorneDroit(), FRANCE_SUPPRIME.getBorneDroit());
        Assert.assertEquals(liste.get(1).getBorneGauche(), FRANCE_SUPPRIME.getBorneGauche());
        Assert.assertEquals(liste.get(1).getLevel(), FRANCE_SUPPRIME.getLevel());

        Assert.assertEquals(liste.get(2).getNomCategorie(), US.getNomCategorie());
        Assert.assertEquals(liste.get(2).getBorneDroit(), US.getBorneDroit());
        Assert.assertEquals(liste.get(2).getBorneGauche(), US.getBorneGauche());
        Assert.assertEquals(liste.get(2).getLevel(), US.getLevel());

    }


    @Test
    public void findBorneMax() {

        // Aller chercher la vraie borne maximale de la BDD
        ArrayList<CategorieSupprime> categories = new ArrayList<>(categorieSupprimeRepository.findAll());

        int bornemax = 0;

        for (CategorieSupprime c : categories) {
            if (c.getBorneDroit() > bornemax) {
                bornemax = c.getBorneDroit();
            }
        }

        // Faire de même avec la méthode à tester
        int res = categorieSupprimeRepository.findBorneMax();

        Assert.assertEquals(res, bornemax);

    }

    /**
     * Va chercher toutes les catégories et retourne celle dont le nom correspond au paramères.
     *
     * @param name le nom de la catégorie qu'on cherche
     * @return la catégorie recherchée, ou null si elle n'a pas été trouvée
     */
    private CategorieSupprime findACat(String name) {

        Collection<CategorieSupprime> res = categorieSupprimeRepository.findAll();
        ArrayList<CategorieSupprime> newcatList = new ArrayList<CategorieSupprime>(res);

        for (CategorieSupprime cat : newcatList) {
            if (cat.getNomCategorie().equals(name)) {
                return cat;
            }
        }

        return null;
    }

}
