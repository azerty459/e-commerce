package com.projet.ecommerce.persistance.repository;


import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
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
public class CaracteristiqueRepositoryTests {

    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;

    @Autowired
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;

    @Autowired
    private ProduitRepository produitRepository;

    private static final String REFERENCE_PRODUIT = "A05A01";
    private static Produit PRODUIT;
    private static Caracteristique CARACTERISTIQUE;
    private static TypeCaracteristique TYPECARACTERISTIQUE;

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
        PRODUIT = new Produit();
        PRODUIT.setReferenceProduit("A05A01");
        PRODUIT.setPrixHT(8.7f);
        PRODUIT.setDescription("joli produit");
        PRODUIT.setCaracteristiques(new ArrayList<>());

        CARACTERISTIQUE = new Caracteristique();
        CARACTERISTIQUE.setProduit(PRODUIT);
        CARACTERISTIQUE.setValeur("test n#1");

        TYPECARACTERISTIQUE = new TypeCaracteristique();
        TYPECARACTERISTIQUE.setType("test type");
        CARACTERISTIQUE.setType(TYPECARACTERISTIQUE);
    }

    @Test
    public void updateCaracteristique() {
        produitRepository.save(PRODUIT);
        typeCaracteristiqueRepository.save(TYPECARACTERISTIQUE);
        Caracteristique saved = caracteristiqueRepository.save(CARACTERISTIQUE);
        Caracteristique c = caracteristiqueRepository.findById(saved.getIdCaracteristique()).get();
        Assert.assertNotNull(c);
        c.setValeur("test test");
        Assert.assertNotNull(caracteristiqueRepository.save(c));
        Assert.assertEquals(c.getType().getIdTypeCaracteristique(), typeCaracteristiqueRepository.findById(c.getType().getIdTypeCaracteristique()).get().getIdTypeCaracteristique());
    }

    @Test
    public void insertCaracteristique() {
        produitRepository.save(PRODUIT);
        Produit p = produitRepository.findByReferenceProduit(REFERENCE_PRODUIT);
        Assert.assertNotNull(p);
        TypeCaracteristique t= new TypeCaracteristique();
        t.setType("test type");
        Caracteristique c= new Caracteristique();
        c.setValeur("test");
        TypeCaracteristique savedT = typeCaracteristiqueRepository.save(t);
        Assert.assertNotNull(savedT);
        c.setType(savedT);
        c.setProduit(p);
        p.getCaracteristiques().add(c);
        c = caracteristiqueRepository.save(c);
        Assert.assertNotNull(c);
        Assert.assertTrue(produitRepository.findByReferenceProduit(REFERENCE_PRODUIT).getCaracteristiques().contains(c));
    }




}
