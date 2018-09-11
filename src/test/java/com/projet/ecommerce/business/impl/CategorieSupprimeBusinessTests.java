package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.transformer.CategorieTransformer;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.CategorieSupprime;
import com.projet.ecommerce.persistance.entity.Produit;
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

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategorieSupprimeBusinessTests {

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
    private CategorieRepository categorieRepository;

    @Mock
    private CategorieSupprimeRepository categorieSupprimeRepository;


    @Mock
    private CategorieRepositoryCustom categorieRepositoryCustom;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Mock
    private CategorieBusiness categorieBusiness;

    @InjectMocks
    private CategorieSupprimeBusiness categorieSupprimeBusiness;

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
    public void saveInCategorieSupprimeNotNullWithoutProduits() {
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);
        CategorieSupprime categorieSupprime1 = new CategorieSupprime();
        categorieSupprime1.setNomCategorie("Transport");
        categorieSupprime1.setBorneGauche(1);
        categorieSupprime1.setBorneDroit(4);
        categorieSupprime1.setLevel(1);
        Mockito.when(categorieSupprimeRepository.save(Mockito.any())).thenReturn(categorieSupprime1);
        boolean retour1 = categorieSupprimeBusiness.saveInCategorieSupprime(categorie1);

        Assert.assertNotNull(retour1);
        Assert.assertEquals(retour1, true);
    }

    @Test
    public void saveInCategorieSupprimeNotNullWithProduits() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A");
        produit.setNom("produit");
        produit.setDescription("description");
        produit.setPrixHT(5);
        produit.setPhotos(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);
        List<Produit> produits = new ArrayList<>();
        produits.add(produit);
        categorie1.setProduits(produits);
        CategorieSupprime categorieSupprime1 = new CategorieSupprime();
        categorieSupprime1.setNomCategorie("Transport");
        categorieSupprime1.setBorneGauche(1);
        categorieSupprime1.setBorneDroit(4);
        categorieSupprime1.setLevel(1);

        Mockito.when(categorieSupprimeRepository.save(Mockito.any())).thenReturn(categorieSupprime1);
        boolean retour1 = categorieSupprimeBusiness.saveInCategorieSupprime(categorie1);

        Assert.assertTrue(retour1);
    }

    @Test
    public void saveInCategorieSupprimeNull() {
        Categorie categorie1 = null;
        CategorieSupprime categorieSupprime1 = new CategorieSupprime();
        Mockito.when(categorieSupprimeRepository.save(Mockito.any())).thenReturn(categorieSupprime1);
        boolean retour1 = categorieSupprimeBusiness.saveInCategorieSupprime(categorie1);
        Assert.assertFalse(retour1);
    }

    @Test
    public void restoreLastDeletedCategorie() {
        int idNouveauParent = 1;
        int idNouvelleCategorie = 3;
        // Initialisation
        CategorieSupprime categorieSupprime2 = new CategorieSupprime();
        categorieSupprime2.setNomCategorie("Transport2");
        categorieSupprime2.setBorneGauche(2);
        categorieSupprime2.setBorneDroit(3);
        categorieSupprime2.setLevel(2);
        CategorieSupprime categorieSupprime1 = new CategorieSupprime();
        categorieSupprime1.setNomCategorie("Transport");
        categorieSupprime1.setBorneGauche(1);
        categorieSupprime1.setBorneDroit(4);
        categorieSupprime1.setLevel(1);
        List<CategorieSupprime> categoriesADeplacer = new ArrayList<>();
        categoriesADeplacer.add(categorieSupprime1);
        categoriesADeplacer.add(categorieSupprime2);

        Categorie categorie2 = new Categorie();
        categorie2.setNomCategorie("Transport2");
        categorie2.setBorneGauche(2);
        categorie2.setBorneDroit(3);
        categorie2.setLevel(2);
        categorie2.setIdCategorie(idNouvelleCategorie);
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        // on s'assure que la categorie 1 a un lvl plus grand que la categorie 2
        categorie1.setLevel(categorie2.getLevel() + 1);
        categorie1.setIdCategorie(458);
        List<Categorie> categories = new ArrayList<>();
        categories.add(categorie1);
        categories.add(categorie2);
        List<CategorieDTO> categoriesDTO = new ArrayList<>();
        categoriesDTO.add(CategorieTransformer.entityToDto(categorie1));
        categoriesDTO.add(CategorieTransformer.entityToDto(categorie2));

        Mockito.when(categorieSupprimeRepository.findAll()).thenReturn(categoriesADeplacer);
        Mockito.when(categorieRepository.saveAll(Mockito.any())).thenReturn(categories);
        Mockito.when(categorieBusiness.moveCategorie(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        Mockito.when(categorieBusiness.getCategorie(idNouvelleCategorie, null, true, true)).thenReturn(categoriesDTO);

        List<CategorieDTO> retour1 = categorieSupprimeBusiness.restoreLastDeletedCategorie(idNouveauParent);

        Assert.assertEquals(categoriesDTO, retour1);

    }

}