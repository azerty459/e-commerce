package com.projet.ecommerce.persistance.repository;


import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
    private static final Logger logger = LoggerFactory.getLogger(CaracteristiqueRepositoryTests.class);

    @Test
    public void insertCaracteristique() {
        Produit p = produitRepository.findByReferenceProduit(REFERENCE_PRODUIT);
        TypeCaracteristique t= new TypeCaracteristique();
        t.setType("test type");
        Caracteristique c= new Caracteristique();
        c.setValeur("test");
        TypeCaracteristique savedT = typeCaracteristiqueRepository.save(t);
        Assert.assertNotNull(savedT);
        c.setType(savedT);
        c.setProduit(p);
        Assert.assertNotNull(caracteristiqueRepository.save(c));
        Assert.assertTrue(p.getCaracteristiques().contains(c));
    }

    @Test
    public void updateCaracteristique() {
        Caracteristique c = caracteristiqueRepository.findById(1).get();
        Assert.assertNotNull(c);
        c.setValeur("test test");
        Assert.assertNotNull(caracteristiqueRepository.save(c));
        Assert.assertEquals(c.getType().getIdTypeCaracteristique(), typeCaracteristiqueRepository.findById(c.getType().getIdTypeCaracteristique()).get().getIdTypeCaracteristique());
    }


}
