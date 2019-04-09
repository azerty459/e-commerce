package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import java.util.Collection;
import java.util.Optional;
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
        
        TEMP_INSERT.setIdCaracteristique(8);
        TEMP_INSERT.setLibelle("Luminosité");
        
        TEMP_UPDATE.setIdCaracteristique(16);
        TEMP_UPDATE.setLibelle("Forme");
        
        TEMP_DELETE.setIdCaracteristique(32);
        TEMP_DELETE.setLibelle("Puissance");
        
        TEMP_GET.setIdCaracteristique(64);
        TEMP_GET.setLibelle("Odeur");

    }
    
    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;
    
    @Before
    public void setup() {
        caracteristiqueRepository.deleteAll();
    }
    
    @Test
    public void testInsert() {
        Caracteristique insert = caracteristiqueRepository.save(TEMP_INSERT);
        Assert.assertNotNull(insert);
        
        Optional<Caracteristique> optionalCaracteristique = caracteristiqueRepository.findById(insert.getIdCaracteristique());
        Assert.assertTrue(optionalCaracteristique.isPresent());
        Assert.assertEquals(insert.getIdCaracteristique(), optionalCaracteristique.get().getIdCaracteristique());
        Assert.assertEquals(insert.getLibelle(), optionalCaracteristique.get().getLibelle());
    }

    @Test
    public void testUpdate() {
        Caracteristique insert = caracteristiqueRepository.save(TEMP_INSERT);
        
        TEMP_UPDATE.setIdCaracteristique(insert.getIdCaracteristique());
        Caracteristique update = caracteristiqueRepository.save(TEMP_UPDATE);
        Assert.assertNotNull(update);
        Assert.assertEquals(TEMP_UPDATE.getIdCaracteristique(), TEMP_UPDATE.getIdCaracteristique());
        Assert.assertEquals(TEMP_UPDATE.getLibelle(), update.getLibelle());
        
        Optional<Caracteristique> optionalCaracteristique = caracteristiqueRepository.findById(update.getIdCaracteristique());
        Assert.assertTrue(optionalCaracteristique.isPresent());
        Assert.assertEquals(update.getIdCaracteristique(), optionalCaracteristique.get().getIdCaracteristique());
        Assert.assertEquals(update.getLibelle(), optionalCaracteristique.get().getLibelle());
    }
    
    @Test
    public void testDelete() {
        Caracteristique insert = caracteristiqueRepository.save(TEMP_DELETE);
        caracteristiqueRepository.delete(insert);
        Assert.assertFalse(caracteristiqueRepository.findById(insert.getIdCaracteristique()).isPresent());
        
        insert = caracteristiqueRepository.save(TEMP_DELETE);
        caracteristiqueRepository.deleteById(insert.getIdCaracteristique());
        Assert.assertFalse(caracteristiqueRepository.findById(insert.getIdCaracteristique()).isPresent());
    }
    
    @Test
    public void testFindById() {
        Caracteristique insert = caracteristiqueRepository.save(TEMP_GET);
        
        Optional<Caracteristique> optionalCaracteristique = caracteristiqueRepository.findById(insert.getIdCaracteristique());
        Assert.assertTrue(optionalCaracteristique.isPresent());
        Caracteristique caracteristique = optionalCaracteristique.get();
        Assert.assertEquals(insert.getIdCaracteristique(), caracteristique.getIdCaracteristique());
        Assert.assertEquals(insert.getLibelle(), caracteristique.getLibelle());
    }
    
    @Test
    public void testFindAll() {
        Collection<Caracteristique> all = caracteristiqueRepository.findAll();
        Assert.assertEquals(0, all.size());
        
        Caracteristique caracteristique1 = caracteristiqueRepository.save(TEMP_GET);
        Caracteristique caracteristique2 = caracteristiqueRepository.save(TEMP_INSERT);
        all = caracteristiqueRepository.findAll();
        Assert.assertEquals(2, all.size());
        for(Caracteristique c : all) {
            if(c.getIdCaracteristique() != caracteristique1.getIdCaracteristique() && c.getIdCaracteristique() != caracteristique2.getIdCaracteristique()) {
                Assert.fail("La caracteristique ne correspond pas à une ajoutée");
            }
        }
        
        caracteristiqueRepository.delete(caracteristique1);
        all = caracteristiqueRepository.findAll();
        Assert.assertEquals(1, all.size());
        for(Caracteristique c : all) {
            Assert.assertEquals(caracteristique2.getIdCaracteristique(), c.getIdCaracteristique());
            Assert.assertEquals(caracteristique2.getLibelle(), c.getLibelle());
        }
    }
    
    @Test
    public void testFindByLibelle() {
        caracteristiqueRepository.save(TEMP_GET);
        caracteristiqueRepository.save(TEMP_INSERT);
        
        Optional<Caracteristique> caracteristiqueOptional = caracteristiqueRepository.findByLibelle(TEMP_GET.getLibelle());
        Assert.assertTrue(caracteristiqueOptional.isPresent());
        System.out.println(caracteristiqueOptional.get());
        Assert.assertEquals(TEMP_GET.getLibelle(), caracteristiqueOptional.get().getLibelle());
    }
    
}
