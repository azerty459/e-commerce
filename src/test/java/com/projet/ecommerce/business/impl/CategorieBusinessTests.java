package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.CategorieRepositoryCustom;
import com.projet.ecommerce.persistance.repository.CategorieSupprimeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategorieBusinessTests {

    private static final Categorie LIVRE = new Categorie();
    private static final Categorie ROMAN = new Categorie();
    private static final Categorie BIO = new Categorie();
    private static final Categorie FRANCE = new Categorie();
    private static final Categorie US = new Categorie();
    private static final Categorie CINEMA = new Categorie();
    private static final Categorie DRAME = new Categorie();

    private static Collection<Categorie> romanEtEnfants = null;

    private static Collection<Categorie> toutesLesCategories;

    private static Collection<Categorie> nouveauParent;

    @Mock
    private CategorieRepository categorieRepository;

    @InjectMocks
    private CategorieBusiness categorieBusiness;


    static {

        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        buildCategories();

    }

    private static void buildCategories() {
        // Arbre des catégories
        LIVRE.setIdCategorie(1);
        LIVRE.setNomCategorie("Livre");
        LIVRE.setBorneGauche(1);
        LIVRE.setBorneDroit(10);
        LIVRE.setLevel(1);

        ROMAN.setIdCategorie(2);
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

        DRAME.setIdCategorie(7);
        DRAME.setNomCategorie("Drame");
        DRAME.setBorneGauche(12);
        DRAME.setBorneDroit(13);
        DRAME.setLevel(2);
    }

    @Mock
    private CategorieSupprimeRepository categorieSupprimeRepository;


    @Mock
    private CategorieRepositoryCustom categorieRepositoryCustom;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Construire un ArrayList avec toutes les catégories
        toutesLesCategories = new ArrayList<>();
        toutesLesCategories.add(LIVRE);
        toutesLesCategories.add(ROMAN);
        toutesLesCategories.add(BIO);
        toutesLesCategories.add(FRANCE);
        toutesLesCategories.add(US);
        toutesLesCategories.add(CINEMA);
        toutesLesCategories.add(DRAME);

        // Construire un ArrayList des catégories à déplacer si on veut bouger ROMAN
        romanEtEnfants = new ArrayList<>();
        romanEtEnfants.add(ROMAN);
        romanEtEnfants.add(FRANCE);
        romanEtEnfants.add(US);

        // Array ne contenant que le nouveau parent, c'est à dire DRAME
        nouveauParent = new ArrayList<>();
        nouveauParent.add(DRAME);


    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testMoveCategorieSansMove() {
        Assert.assertFalse(categorieBusiness.moveCategorie(2, 2));
    }

    @Test
    public void testMoveCategorieVersParent() {

        List<Categorie> liste = new ArrayList<>();
        liste.add(ROMAN);
        liste.add(FRANCE);
        liste.add(US);

        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(DRAME));
        Mockito.when(categorieRepository.findByIdCategorieWithSousCat(Mockito.anyInt())).thenReturn(liste);
        Mockito.when(categorieRepository.rearrangerBornes(Mockito.anyInt(), Mockito.anyInt())).thenReturn(4);

        Assert.assertTrue(categorieBusiness.moveCategorie(2, 7));
    }

    // TODO: à faire
    @Test
    public void testMoveCategorieSansParent() {

    }


    @Test
    public void updateCategorieIdOk() {

        Mockito.when(categorieRepository.findById(1)).thenReturn(Optional.of(LIVRE));
        Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(LIVRE);

        CategorieDTO retour = categorieBusiness.updateCategorie(LIVRE.getIdCategorie(), "Bouquin");

        Assert.assertEquals(retour.getNom(), "Bouquin");

    }

    @Test(expected = GraphQLCustomException.class)
    public void updateCategorieIdKO() {
        categorieBusiness.updateCategorie(1000, "Truc");
    }

    @Test
    public void insertParent() {
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);

        Collection<Categorie> categorieCollection = new ArrayList<>();
        categorieCollection.add(categorie1);

        Mockito.when(categorieRepository.findAll()).thenReturn(categorieCollection);
        Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(categorie1);
        CategorieDTO retour1 = categorieBusiness.addParent("Test");

        Assert.assertNotNull(retour1);
        Assert.assertEquals(categorie1.getNomCategorie(), retour1.getNom());
    }


    @Test
    public void insertEnfant() {
        Categorie parent = new Categorie();
        parent.setNomCategorie("Transport");
        parent.setBorneGauche(1);
        parent.setBorneDroit(4);
        parent.setLevel(1);

        Categorie enfant = new Categorie();

        Collection<Categorie> categorieCollection = new ArrayList<>();
        categorieCollection.add(parent);

        Mockito.when(categorieRepository.findById(parent.getIdCategorie())).thenReturn(Optional.of(parent));

        Mockito.when(categorieRepository.findAll()).thenReturn(categorieCollection);
        Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(parent);
        CategorieDTO retour1 = categorieBusiness.addEnfant("Test", parent.getIdCategorie());

        Assert.assertNotNull(retour1);
        Assert.assertEquals(parent.getNomCategorie(), retour1.getNom());
    }


    @Test
    public void deleteNull() {
        Assert.assertFalse(categorieBusiness.delete(1));
    }

    @Test
    public void getCategorie_getAll() {
        List<Categorie> categories = new ArrayList<>();
        Assert.assertEquals(categorieBusiness.getCategorie(0, null, false, false).size(), 0);

        // Création des catégories et ajout dans la liste.
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport1");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);
        categorie1.setProduits(new ArrayList<>());

        Categorie categorie2 = new Categorie();
        categorie2.setNomCategorie("Transport1");
        categorie2.setBorneGauche(2);
        categorie2.setBorneDroit(3);
        categorie2.setLevel(2);
        categorie2.setProduits(new ArrayList<>());

        categories.add(categorie1);
        categories.add(categorie2);

        // Tests
        Mockito.when(categorieRepository.findAllByOrderByNomCategorie()).thenReturn(categories);
        List<CategorieDTO> categorieDTOList = categorieBusiness.getCategorie(0, null, false, false);

        Assert.assertEquals(2, categories.size());

        CategorieDTO retour = categorieDTOList.get(0);
        Assert.assertEquals(categorie1.getNomCategorie(), retour.getNom());

    }

    @Test
    public void getCategorie_findByNomCategorie() {
        List<Categorie> categories = new ArrayList<>();

        // Création des catégories et ajout dans la liste.
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport1");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);
        categorie1.setProduits(new ArrayList<>());

        Categorie categorie2 = new Categorie();
        categorie2.setNomCategorie("Transport2");
        categorie2.setBorneGauche(2);
        categorie2.setBorneDroit(3);
        categorie2.setLevel(2);
        categorie2.setProduits(new ArrayList<>());

        categories.add(categorie1);
        categories.add(categorie2);

        // Tests
        Mockito.when(categorieRepository.findByNomCategorie(Mockito.anyString())).thenReturn(categories);
        List<CategorieDTO> categorieDTOList = categorieBusiness.getCategorie(0, "Transport1", false, false);

        Assert.assertEquals(2, categorieDTOList.size());
    }

    @Test
    public void getCategorie_findByNomCategorieWithSousCat() {
        List<Categorie> categories = new ArrayList<>();

        // Création des catégories et ajout dans la liste.
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport1");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);
        categorie1.setProduits(new ArrayList<>());

        Categorie categorie2 = new Categorie();
        categorie2.setNomCategorie("Transport2");
        categorie2.setBorneGauche(2);
        categorie2.setBorneDroit(3);
        categorie2.setLevel(2);
        categorie2.setProduits(new ArrayList<>());

        categories.add(categorie1);
        categories.add(categorie2);

        // Tests
        Mockito.when(categorieRepository.findByNomCategorieWithSousCat(Mockito.anyString())).thenReturn(categories);
        List<CategorieDTO> categorieDTOList = categorieBusiness.getCategorie(0, "Transport1", true, false);

        Assert.assertEquals(1, categorieDTOList.size());
        Assert.assertEquals(1, categorieDTOList.get(0).getSousCategories().size());
    }

    @Test
    public void getCategorie_getFindById() {
        // Création des catégories et ajout dans la liste.
        Categorie categorie = new Categorie();
        categorie.setIdCategorie(1);
        categorie.setNomCategorie("Transport1");
        categorie.setBorneGauche(1);
        categorie.setBorneDroit(4);
        categorie.setLevel(1);
        categorie.setProduits(new ArrayList<>());

        // Tests
        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(categorie));
        List<CategorieDTO> categorieDTOList = categorieBusiness.getCategorie(1, null, false, false);

        Assert.assertEquals(1, categorieDTOList.size());
    }

    @Test
    public void getCategorie_getFindByIdWithSousCat() {
        List<Categorie> categories = new ArrayList<>();

        // Création des catégories et ajout dans la liste.
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport1");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);
        categorie1.setProduits(new ArrayList<>());

        Categorie categorie2 = new Categorie();
        categorie2.setNomCategorie("Transport2");
        categorie2.setBorneGauche(2);
        categorie2.setBorneDroit(3);
        categorie2.setLevel(2);
        categorie2.setProduits(new ArrayList<>());

        categories.add(categorie1);
        categories.add(categorie2);

        // Tests
        Mockito.when(categorieRepository.findByIdCategorieWithSousCat(Mockito.anyInt())).thenReturn(categories);
        List<CategorieDTO> categorieDTOList = categorieBusiness.getCategorie(1, null, true, false);

        Assert.assertEquals(1, categorieDTOList.size());
        Assert.assertEquals(1, categorieDTOList.get(0).getSousCategories().size());
    }


    @Test
    public void construireAssociationEnfantsChemins() {

        // Création des catégories
        Collection<Categorie> categories = new ArrayList<>();

        Categorie cat1 = new Categorie();
        cat1.setLevel(1);
        cat1.setBorneDroit(10);
        cat1.setBorneGauche(1);
        cat1.setNomCategorie("Transport");

        Categorie cat2 = new Categorie();
        cat2.setLevel(2);
        cat2.setBorneDroit(7);
        cat2.setBorneGauche(2);
        cat2.setNomCategorie("Aérien");

        Categorie cat3 = new Categorie();
        cat3.setLevel(3);
        cat3.setBorneDroit(6);
        cat3.setBorneGauche(3);
        cat3.setNomCategorie("Avion");

        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);

        Mockito.when(categorieRepository.findParents(Mockito.any())).thenReturn(categories);

        Map<Categorie, Collection<Categorie>> resultat;
        resultat = categorieBusiness.construireAssociationEnfantsChemins(categories);

        // Tests
        Assert.assertEquals(resultat.get(cat1), "");
        Assert.assertEquals(resultat.get(cat2), "Transport");
        Assert.assertEquals(resultat.get(cat3), "Transport > Aérien");

    }


}