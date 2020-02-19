package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Libelle;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CaracteristiqueBusinessTests {
    public static final Caracteristique TEMP_INSERT_1 = new Caracteristique();
    public static final Caracteristique TEMP_INSERT_2 = new Caracteristique();
    public static final Caracteristique TEMP_INSERT_3 = new Caracteristique();
    public static final Caracteristique TEMP_INSERT_4 = new Caracteristique();

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        //XXX - why?
        System.setProperty("spring.config.location", "classpath:application-test.properties");
    }

    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    LibelleRepository libelleRepository;
    @Autowired
    CaracteristiqueRepository caracteristiqueRepository;
    @Autowired
    CaracteristiqueBusiness caracteristiqueBusiness;

    @Before
    public void populateDatabase() {
        Produit p1 = produitRepository.save(ProduitRepositoryTests.TEMP_INSERT);
        Produit p2 = produitRepository.save(ProduitRepositoryTests.TEMP_GET);
        Produit p3 = produitRepository.save(ProduitRepositoryTests.TEMP_DELETE);
        Produit p4 = produitRepository.save(ProduitRepositoryTests.TEMP_UPDATE);

        Libelle l1 = libelleRepository.save(LibelleRepositoryUnnecessaryTests.TEMP_INSERT_1);
        Libelle l2 = libelleRepository.save(LibelleRepositoryUnnecessaryTests.TEMP_INSERT_2);
        Libelle l3 = libelleRepository.save(LibelleRepositoryUnnecessaryTests.TEMP_INSERT_3);
        Libelle l4 = libelleRepository.save(LibelleRepositoryUnnecessaryTests.TEMP_INSERT_4);

        TEMP_INSERT_1.setProduit(p1);
        TEMP_INSERT_1.setLibelle(l1);
        TEMP_INSERT_1.setValeur("Interessant");

        TEMP_INSERT_2.setProduit(p1);
        TEMP_INSERT_2.setLibelle(l2);
        TEMP_INSERT_2.setValeur("De mieux en mieux");

        TEMP_INSERT_3.setProduit(p2);
        TEMP_INSERT_3.setLibelle(l3);
        TEMP_INSERT_3.setValeur("Quoi d'autre ?");

        caracteristiqueRepository.save(TEMP_INSERT_1);
        caracteristiqueRepository.save(TEMP_INSERT_2);
        caracteristiqueRepository.save(TEMP_INSERT_3);
    }

    //CaracteristiqueDTO addCaracteristique(String refProduit, int idLibelle, String valeurCaracteristique);
    @Test
    public void addCaracteristique() {
        //TODO cas valeurCaracteristique null ou vide, refProduit null, refProduit ou idLibelle inexistant

        //cas normal
        String refProduit = TEMP_INSERT_1.getProduit().getReferenceProduit();
        int idLibelle = TEMP_INSERT_3.getLibelle().getIdLibelle();

        int countBeforeAdd = caracteristiqueBusiness.getAllCaracteristiques(refProduit).size();

        CaracteristiqueDTO returnedCaracDto = caracteristiqueBusiness.addCaracteristique(refProduit, idLibelle, "test");
        Assert.assertEquals("test",returnedCaracDto.getValeur());
        Assert.assertEquals(idLibelle, returnedCaracDto.getLibelle().getId());

        int countAfterAdd = caracteristiqueBusiness.getAllCaracteristiques(refProduit).size();
        Assert.assertEquals(countBeforeAdd+1, countAfterAdd);

        //cas deja existant
        boolean erreurDeclenchee = false;
        try {
            caracteristiqueBusiness.addCaracteristique(refProduit, idLibelle, "test2");
        } catch (Exception e) {
            e.printStackTrace();
            erreurDeclenchee = true;
            Assert.assertEquals(GraphQLCustomException.class, e.getClass());
            System.out.println(e.getMessage());
        }
        Assert.assertTrue(erreurDeclenchee);

        int countAfterError = caracteristiqueBusiness.getAllCaracteristiques(refProduit).size();
        Assert.assertEquals(countAfterAdd,countAfterError);
    }

    //TODO :
    //    Collection<CaracteristiqueDTO> getAllCaracteristiques(String refProduit);
    //    Collection<CaracteristiqueDTO> getAllCaracteristique(String refProduit, String motCle);
    //    CaracteristiqueDTO getCaracteristique(String refProduit, int idLibelle);
    //    CaracteristiqueDTO updateCaracteristique(String refProduit, int idLibelle, String nouvelleValeur);
    //    void deleteCaracteristique(String refProduit, int idLibelle);

}
