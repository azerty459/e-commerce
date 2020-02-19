package com.projet.ecommerce.persistance.repository;

import javax.transaction.Transactional;

import com.projet.ecommerce.persistance.entity.Libelle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

//TODO supprimer cette classe qui ne sert qu'à checker le comportement du framework
//XXX - détails sur ces annotations ?
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class LibelleRepositoryUnnecessaryTests {
    //XXX - est ce un probleme de les mettre en public ?
    public static final Libelle TEMP_INSERT_1;
    public static final Libelle TEMP_INSERT_2;
    public static final Libelle TEMP_INSERT_3;
    public static final Libelle TEMP_INSERT_4;

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
        
        TEMP_INSERT_1 = new Libelle();
        TEMP_INSERT_1.setNom("Editeur");

        TEMP_INSERT_2 = new Libelle();
        TEMP_INSERT_2.setNom("Broché");

        TEMP_INSERT_3 = new Libelle();
        TEMP_INSERT_3.setNom("Poids");

        TEMP_INSERT_4 = new Libelle();
        TEMP_INSERT_4.setNom("Puissance fiscale (en CV)");
    }

    @Autowired
    private LibelleRepository libelleRepo;

    @Before
    public void insertLibelles() {
        libelleRepo.save(TEMP_INSERT_1);
        libelleRepo.save(TEMP_INSERT_2);
        libelleRepo.save(TEMP_INSERT_3);
        libelleRepo.save(TEMP_INSERT_4);

    }

    @Test
    public void findByNomIgnoringCase() {
        Collection<Libelle> poids = libelleRepo.findByNomIgnoreCase("poids");
        Assert.assertEquals(1,poids.size());
    }

    @Test
    public void findByMotCle() {
        Collection<Libelle> foundLs = libelleRepo.findByNomContainingIgnoreCase("FISCALE");
        Assert.assertEquals(1,foundLs.size());
    }

    @Test
    public void findByMotCleWhenEmptyMotCle() {
        Collection<Libelle> foundLs = libelleRepo.findByNomContainingIgnoreCase("");
        Assert.assertEquals(4,foundLs.size());
    }

}



