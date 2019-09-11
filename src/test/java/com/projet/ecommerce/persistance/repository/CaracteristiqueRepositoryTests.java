package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.CaracteristiqueID;
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

    private static final Caracteristique CARACT = new Caracteristique();

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        CARACT.setReferenceProduit("R");
        CARACT.setType("T");
        CARACT.setValeur("V");
    }

    @Autowired
    private CaracteristiqueRepository repository;

    @Test
    public void insertCaracteristique() {
        Caracteristique caracteristique = repository.save(CARACT);
        Assert.assertNotNull(caracteristique);
        Assert.assertEquals(caracteristique.getReferenceProduit(), CARACT.getReferenceProduit());
        Assert.assertEquals(caracteristique.getType(), CARACT.getType());
        Assert.assertEquals(caracteristique.getValeur(), CARACT.getValeur());
    }

    @Test
    public void deleteCaracteristique() {
        Assert.assertNotNull(repository.save(CARACT));
        repository.delete(CARACT);
        CaracteristiqueID caractId = new CaracteristiqueID(CARACT.getReferenceProduit(), CARACT.getType());
        Assert.assertFalse(repository.findById(caractId).isPresent());
    }
}
