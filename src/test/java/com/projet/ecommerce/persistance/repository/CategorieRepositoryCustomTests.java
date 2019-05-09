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

    private static Categorie LIVRE = new Categorie();
    private static Categorie ROMAN = new Categorie();
    private static Categorie BIO = new Categorie();
    private static Categorie FRANCE = new Categorie();
    private static Categorie US = new Categorie();
    private static Categorie CINEMA = new Categorie();
    private static Categorie DRAME = new Categorie();

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
        LIVRE.setIdCategorie(1);

        ROMAN.setNomCategorie("Roman");
        ROMAN.setBorneGauche(2);
        ROMAN.setBorneDroit(7);
        ROMAN.setLevel(2);
        ROMAN.setIdCategorie(2);

        BIO.setNomCategorie("Bio");
        BIO.setBorneGauche(8);
        BIO.setBorneDroit(9);
        BIO.setLevel(2);
        BIO.setIdCategorie(3);

        FRANCE.setIdCategorie(4);
        FRANCE.setNomCategorie("France");
        FRANCE.setBorneGauche(3);
        FRANCE.setBorneDroit(4);
        FRANCE.setLevel(3);

        US.setNomCategorie("US");
        US.setBorneGauche(5);
        US.setBorneDroit(6);
        US.setLevel(3);
        US.setIdCategorie(5);

        CINEMA.setNomCategorie("Ciné");
        CINEMA.setBorneGauche(11);
        CINEMA.setBorneDroit(14);
        CINEMA.setLevel(1);
        CINEMA.setIdCategorie(6);

        DRAME.setNomCategorie("Drame");
        DRAME.setBorneGauche(12);
        DRAME.setBorneDroit(13);
        DRAME.setLevel(2);
        DRAME.setIdCategorie(7);
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

        compareCategorie(ROMAN, liste.get(0));
        compareCategorie(FRANCE, liste.get(1));
        compareCategorie(US, liste.get(2));

    }

    private void compareCategorie(Categorie attendue, Categorie actuelle) {
        Assert.assertEquals(actuelle.getNomCategorie(), attendue.getNomCategorie());
        Assert.assertEquals(actuelle.getBorneDroit(), attendue.getBorneDroit());
        Assert.assertEquals(actuelle.getBorneGauche(), attendue.getBorneGauche());
        Assert.assertEquals(actuelle.getLevel(), attendue.getLevel());

        // TODO a faire marcher Assert.assertEquals(attendue, actuelle);
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

//    @Test
    //TODO Refaire le test
//    public void ecarterBornes() {
//
//        // Ecartement de bones de 2
//        categorieRepository.ecarterBornes(DRAME.getBorneGauche(), 2);
//
//        Categorie newDrame = findACat("Drame");
//
//        Assert.assertEquals(newDrame.getNomCategorie(), "Drame");
//        Assert.assertEquals(newDrame.getLevel(), 2);
//        Assert.assertEquals(newDrame.getBorneGauche(), 12);
//        Assert.assertEquals(newDrame.getBorneDroit(), 13);
//
//        Categorie newCinema = findACat("Ciné");
//        Assert.assertEquals(newCinema.getBorneGauche(), 11);
//        Assert.assertEquals(newCinema.getBorneDroit(), 14);
//    }

    // TODO: à finir
    @Test
    public void testRearrangerBornes() {

        // Supprimer la catégorie Roman et ses enfants (3 catégories supprimées donc intervalle de 6)
        int bgRoman = categorieRepository.findByNomCategorie("Roman").iterator().next().getBorneGauche();
        int intervalleSupprime = 6;

        categorieRepository.delete(categorieRepository.findByNomCategorie("Roman").iterator().next());
        categorieRepository.delete(categorieRepository.findByNomCategorie("France").iterator().next());
        categorieRepository.delete(categorieRepository.findByNomCategorie("US").iterator().next());

        // categorieRepository.deleteById(categorieRepository.findByNomCategorie("Roman").iterator().next().getIdCategorie());

        // Vérifier la suppression
        Assert.assertTrue(categorieRepository.findByNomCategorie("Roman").isEmpty());
        Assert.assertTrue(categorieRepository.findByNomCategorie("France").isEmpty());
        Assert.assertTrue(categorieRepository.findByNomCategorie("US").isEmpty());

        categorieRepository.rearrangerBornes(bgRoman, intervalleSupprime);


        // Vérifier que les bornes ont été réarrangées
//        Categorie l = categorieRepository.findByNomCategorie("Livre").iterator().next();
//        Assert.assertEquals(1, l.getBorneGauche());
//        Assert.assertEquals(4, l.getBorneDroit());

//        Categorie b = categorieRepository.findByNomCategorie("Bio").iterator().next();
//        Assert.assertEquals(2, b.getBorneGauche());
//        Assert.assertEquals(3, b.getBorneDroit());


    } 

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

//    @Test
//    public void findBorneMax() {
//
//        // Aller chercher la vraie borne maximale de la BDD
//        ArrayList<Categorie> categories = new ArrayList<Categorie>(categorieRepository.findAll());
//
//        int bornemax = 0;
//
//        for (Categorie c : categories) {
//            if (c.getBorneDroit() > bornemax) {
//                bornemax = c.getBorneDroit();
//            }
//        }
//
//        // Faire de même avec la méthode à tester
//        int res = categorieRepository.findBorneMax();
//
//        Assert.assertEquals(res, bornemax);
//
//    }

    /**
     * Helper method. Va chercher toutes les catégories et retourne celle dont le nom correspond au paramètre.
     *
     * @param name le nom de la catégorie qu'on cherche
     * @return la catégorie recherchée, ou null si elle n'a pas été trouvée
     */
    private Categorie findACat(String name) {

        Collection<Categorie> res = categorieRepository.findAll();
        ArrayList<Categorie> newcatList = new ArrayList<Categorie>(res);

        Categorie result = null;

        for (Categorie cat : newcatList) {
            if (cat.getNomCategorie().equals(name)) {
                result = cat;
            }
        }
        return result;
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
