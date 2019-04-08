/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CaracteristiqueRepositoryTest {
    
    private static final Caracteristique TEMP_INSERT = new Caracteristique();
    private static final Caracteristique TEMP_DELETE = new Caracteristique();
    private static final Caracteristique TEMP_UPDATE = new Caracteristique();
    private static final Caracteristique TEMP_GET = new Caracteristique();

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
        
        TEMP_INSERT.setIdCaracteristique(2);
        TEMP_INSERT.setLibelle("Luminosité");
        
        TEMP_GET.setIdCaracteristique(8);
        TEMP_GET.setLibelle("Odeur");

        /*
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
        */
    }
    
    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;
    
    @Before
    public void setup() {
        caracteristiqueRepository.deleteAll();
    }
    
    @Test
    public void testInsert() {
        
    }
    
    @Test
    public void testUpdate() {
        
    }
    
    @Test
    public void testDelete() {
        
    }
    
    @Test
    public void testFindById() {
        
    }
    
    @Test
    public void testFindByLibelle() {
        caracteristiqueRepository.save(TEMP_GET);
        caracteristiqueRepository.save(TEMP_INSERT);
        
        Collection<Caracteristique> caracteristiqueCollection = caracteristiqueRepository.findByLibelle(TEMP_GET.getLibelle());
        Assert.assertEquals(1, caracteristiqueCollection.size());
    }
    
}
