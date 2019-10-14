package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.CaracteristiqueId;
import org.junit.Assert;
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
public class CaracteristiqueRepositoryTests {

    private static final Caracteristique CARACTERISTIQUE = new Caracteristique();

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        CARACTERISTIQUE.setReferenceProduit("reference");
        CARACTERISTIQUE.setTypeCaracteristique("type");
        CARACTERISTIQUE.setValeurCaracteristique("valeur");
    }

    @Autowired
    private CaracteristiqueRepository repository;

    @Test
    public void addCaracteristique() {
        Caracteristique caracteristique = repository.save(CARACTERISTIQUE);
        Assert.assertNotNull(caracteristique);
        Assert.assertEquals(caracteristique.getReferenceProduit(), CARACTERISTIQUE.getReferenceProduit());
        Assert.assertEquals(caracteristique.getTypeCaracteristique(), CARACTERISTIQUE.getTypeCaracteristique());
        Assert.assertEquals(caracteristique.getValeurCaracteristique(), CARACTERISTIQUE.getValeurCaracteristique());
    }

    @Test
    public void deleteCaracteristique() {
        Assert.assertNotNull(repository.save(CARACTERISTIQUE));
        repository.delete(CARACTERISTIQUE);
        CaracteristiqueId caracteristiqueId = new CaracteristiqueId(CARACTERISTIQUE.getReferenceProduit(), CARACTERISTIQUE.getTypeCaracteristique());
        Assert.assertFalse(repository.findById(caracteristiqueId).isPresent());
    }
}
