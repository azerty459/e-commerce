package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
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
public class CategorieRepositoryCustomTests {

    private static final Categorie LIVRE = new Categorie();
    private static final Categorie ROMAN = new Categorie();
    private static final Categorie BIO = new Categorie();
    private static final Categorie FRANCE = new Categorie();
    private static final Categorie US = new Categorie();
    private static final Categorie CINEMA = new Categorie();
    private static final Categorie DRAME = new Categorie();

    static {

        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
        buildCategories();
    }

    private static void buildCategories() {
        // Arbre des catégories
        LIVRE.setNomCategorie("Livre");
        LIVRE.setBorneGauche(1);
        LIVRE.setBorneDroit(10);
        LIVRE.setLevel(1);

        ROMAN.setNomCategorie("Roman");
        ROMAN.setBorneGauche(2);
        ROMAN.setBorneDroit(7);
        ROMAN.setLevel(2);

        BIO.setNomCategorie("Bio");
        BIO.setBorneGauche(8);
        BIO.setBorneDroit(9);
        BIO.setLevel(2);

        FRANCE.setNomCategorie("France");
        FRANCE.setBorneGauche(3);
        FRANCE.setBorneDroit(4);
        FRANCE.setLevel(3);

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
        categorieRepository.save(LIVRE);
        categorieRepository.save(ROMAN);
        categorieRepository.save(BIO);
        categorieRepository.save(FRANCE);
        categorieRepository.save(US);
        categorieRepository.save(CINEMA);
        categorieRepository.save(DRAME);

    }

    @Autowired
    private CategorieRepository categorieRepository;

    @Test
    public void findParents() {


        // Création du HashMap d'entrée
        HashMap<Integer, Categorie> entree = new HashMap<Integer, Categorie>();
        entree.put(1, ROMAN);

        // Exécution de la méthode findParents
        Collection<Categorie> resultat = categorieRepository.findParents(entree);

        Assert.assertNotNull(resultat);
        Assert.assertEquals(3, resultat.size());

        ArrayList<Categorie> liste = new ArrayList<Categorie>(resultat);

        Assert.assertEquals(liste.get(0).getNomCategorie(), ROMAN.getNomCategorie());
        Assert.assertEquals(liste.get(0).getBorneDroit(), ROMAN.getBorneDroit());
        Assert.assertEquals(liste.get(0).getBorneGauche(), ROMAN.getBorneGauche());
        Assert.assertEquals(liste.get(0).getLevel(), ROMAN.getLevel());

        Assert.assertEquals(liste.get(1).getNomCategorie(), FRANCE.getNomCategorie());
        Assert.assertEquals(liste.get(1).getBorneDroit(), FRANCE.getBorneDroit());
        Assert.assertEquals(liste.get(1).getBorneGauche(), FRANCE.getBorneGauche());
        Assert.assertEquals(liste.get(1).getLevel(), FRANCE.getLevel());

        Assert.assertEquals(liste.get(2).getNomCategorie(), US.getNomCategorie());
        Assert.assertEquals(liste.get(2).getBorneDroit(), US.getBorneDroit());
        Assert.assertEquals(liste.get(2).getBorneGauche(), US.getBorneGauche());
        Assert.assertEquals(liste.get(2).getLevel(), US.getLevel());

    }

    @Test
    public void findDirectParent() {

        // Exécution de la méthode
        Categorie resultat = categorieRepository.findDirectParent(ROMAN);

        // Test du résultat
        Assert.assertNotNull(resultat);
        Assert.assertEquals(resultat.getLevel(), 1);
        Assert.assertEquals(resultat.getNomCategorie(), "Livre");

    }

    @Test
    //TODO Refaire le test
    public void ecarterBornes() {

        // Ecartement de bones de 2
        categorieRepository.ecarterBornes(DRAME.getBorneGauche(), 2);

        Categorie newDrame = findACat("Drame");

        Assert.assertEquals(newDrame.getNomCategorie(), "Drame");
        Assert.assertEquals(newDrame.getLevel(), 2);
        Assert.assertEquals(newDrame.getBorneGauche(), 12);
        Assert.assertEquals(newDrame.getBorneDroit(), 13);

        Categorie newCinema = findACat("Ciné");
        Assert.assertEquals(newCinema.getBorneGauche(), 11);
        Assert.assertEquals(newCinema.getBorneDroit(), 14);
    }

    //TODO Refaire le test
//    @Test
//    public void rearrangerBornes() {
//
//        // trouver un id
//        int id = findACat("US").getIdCategorie();
//
//        // Supprimer une categorie (US)
//        categorieRepository.deleteById(id);
//
//        // La rechercher
//        Assert.assertNull(findACat("US"));
//
//        // Réorganiser les bornes
//        categorieRepository.rearrangerBornes(5, 6, 2);
//
//        // Vérification des modifications
//        Assert.assertTrue(checkBornes("Livre", 1, 8));
//        Assert.assertTrue(checkBornes("Roman", 2, 5));
//        Assert.assertTrue(checkBornes("Bio", 6, 7));
//        Assert.assertTrue(checkBornes("France", 3, 4));
//        Assert.assertTrue(checkBornes("Ciné", 9, 12));
//        Assert.assertTrue(checkBornes("Drame", 10, 11));
//
//
//    }

    //TODO Refaire le test
    // CategorieRepositoryCustomTests.rearrangerBornesAvecSuppressionsMultiples:197 expected null, but was:<com.projet.ecommerce.persistance.entity.Categorie@3fc024c6>
//    @Test
//    public void rearrangerBornesAvecSuppressionsMultiples() {
//
//        int id = findACat("Roman").getIdCategorie();
//        int bg = findACat("Roman").getBorneGauche();
//        int bd = findACat("Roman").getBorneDroit();
//
//
//        categorieRepository.delete(ROMAN);
//        categorieRepository.delete(FRANCE);
//        categorieRepository.delete(US);
//
//        Assert.assertNull(findACat("Roman"));
//        Assert.assertNull(findACat("France"));
//        Assert.assertNull(findACat("US"));
//
//        // Réorganiser les bornes
//        categorieRepository.rearrangerBornes(bg, bd, bd - bg + 1);
//
//        // Vérification des modifications
//        Assert.assertTrue(checkBornes("Livre", 1, 4));
//        Assert.assertTrue(checkBornes("Bio", 2, 3));
//
//
//    }

    @Test
    public void findBorneMax() {

        // Aller chercher la vraie borne maximale de la BDD
        ArrayList<Categorie> categories = new ArrayList<Categorie>(categorieRepository.findAll());

        int bornemax = 0;

        for (Categorie c : categories) {
            if (c.getBorneDroit() > bornemax) {
                bornemax = c.getBorneDroit();
            }
        }

        // Faire de même avec la méthode à tester
        int res = categorieRepository.findBorneMax();

        Assert.assertEquals(res, bornemax);

    }

    /**
     * Va chercher toutes les catégories et retourne celle dont le nom correspond au paramères.
     *
     * @param name le nom de la catégorie qu'on cherche
     * @return la catégorie recherchée, ou null si elle n'a pas été trouvée
     */
    private Categorie findACat(String name) {

        Collection<Categorie> res = categorieRepository.findAll();
        ArrayList<Categorie> newcatList = new ArrayList<Categorie>(res);

        for (Categorie cat : newcatList) {
            if (cat.getNomCategorie().equals(name)) {
                return cat;
            }
        }

        return null;
    }

    /**
     * Vérifie que la catégorie de nom donné a bien les bornes données
     *
     * @param bg     borne gauche
     * @param bd     borne droite
     * @param nomCat catégorie à vérifier
     * @return true si la catégorie a bien les bornes données, false sinon
     */
    private boolean checkBornes(String nomCat, int bg, int bd) {

        Categorie cat = findACat(nomCat);

        return cat.getBorneGauche() == bg && cat.getBorneDroit() == bd;
    }
}
