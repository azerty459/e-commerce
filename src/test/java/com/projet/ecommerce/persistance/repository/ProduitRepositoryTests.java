package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProduitRepositoryTests {

    private static final Produit TEMP_INSERT = new Produit();
    private static final Produit TEMP_DELETE = new Produit();
    private static final Produit TEMP_UPDATE = new Produit();
    private static final Produit TEMP_GET = new Produit();

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT.setReferenceProduit("A05A87");
        TEMP_INSERT.setPrixHT(8.7f);
        TEMP_INSERT.setDescription("joli produit");

        TEMP_DELETE.setReferenceProduit("A05A88");
        TEMP_DELETE.setPrixHT(11.7f);
        TEMP_DELETE.setDescription("produit");

        TEMP_UPDATE.setReferenceProduit("A05A89");
        TEMP_UPDATE.setPrixHT(10.875f);
        TEMP_UPDATE.setDescription("joli truc");

        TEMP_GET.setReferenceProduit("A05A90");
        TEMP_GET.setPrixHT(9.214f);
        TEMP_GET.setDescription("bas de gamme");
    }

    @Autowired
    private ProduitRepository produitRepository;

    @Test
    public void insertProduit() {
        Assert.assertNotNull(produitRepository.save(TEMP_INSERT));
        Produit temp = produitRepository.findById(TEMP_INSERT.getReferenceProduit()).orElse(null);
        Assert.assertNotNull(temp);
    }

    @Test
    public void getProduit() {
        produitRepository.deleteAll();
        Collection<Produit> produitCollection = produitRepository.findAll();
        Assert.assertEquals(0, produitCollection.size());

        produitRepository.save(TEMP_INSERT);
        produitCollection = produitRepository.findAll();
        Assert.assertEquals(1, produitCollection.size());
    }

    @Test
    public void getProduitByID() {
        Assert.assertNotNull(produitRepository.save(TEMP_GET));
        Produit temp = produitRepository.findById(TEMP_GET.getReferenceProduit()).orElse(null);
        Assert.assertNotNull("Produit ne peut pas être null", temp);
        Assert.assertEquals(TEMP_GET.getReferenceProduit(), temp.getReferenceProduit());
        Assert.assertEquals(TEMP_GET.getPrixHT(), temp.getPrixHT(), 0);
        Assert.assertEquals(TEMP_GET.getDescription(), temp.getDescription());
    }

    @Test
    public void updateProduit() {
        produitRepository.save(TEMP_UPDATE);
        Produit temp = produitRepository.findById(TEMP_UPDATE.getReferenceProduit()).orElse(null);
        Assert.assertEquals(TEMP_UPDATE.getReferenceProduit(), temp.getReferenceProduit());
        Assert.assertEquals(TEMP_UPDATE.getPrixHT(), temp.getPrixHT(), 0);
        Assert.assertEquals(TEMP_UPDATE.getDescription(), temp.getDescription());

        TEMP_UPDATE.setReferenceProduit("A05A100");
        TEMP_UPDATE.setPrixHT(15.5f);
        TEMP_UPDATE.setDescription("joli chose");
        Assert.assertNotNull(produitRepository.save(TEMP_UPDATE));
        temp = produitRepository.findById(TEMP_UPDATE.getReferenceProduit()).orElse(null);
        Assert.assertEquals("joli chose", temp.getDescription());
        Assert.assertEquals(TEMP_UPDATE.getPrixHT(), temp.getPrixHT(), 0);
        Assert.assertEquals(TEMP_UPDATE.getReferenceProduit(), temp.getReferenceProduit());
    }

    @Test
    public void deleteProduit() {
        Assert.assertNotNull(produitRepository.save(TEMP_DELETE));
        produitRepository.delete(TEMP_DELETE);
        Assert.assertFalse(produitRepository.findById(TEMP_DELETE.getReferenceProduit()).isPresent());
    }
}
