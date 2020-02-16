package com.projet.ecommerce.persistance.repository;

import javax.transaction.Transactional;

import com.projet.ecommerce.persistance.entity.Libelle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

//XXX - détails sur ces annotations ?
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class LibelleRepositoryCustomTests {
    private static final Libelle TEMP_INSERT_1;
    private static final Libelle TEMP_INSERT_2;
    private static final Libelle TEMP_INSERT_3;
    
    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
        
        TEMP_INSERT_1 = new Libelle();
        TEMP_INSERT_1.setNom("Editeur");

        TEMP_INSERT_2 = new Libelle();
        TEMP_INSERT_2.setNom("Broché");

        TEMP_INSERT_3 = new Libelle();
        TEMP_INSERT_3.setNom("Poids");
    }

    @Autowired
    private LibelleRepository libelleRepo;

    @Before
    public void insertLibelles() {
        libelleRepo.save(TEMP_INSERT_1);
        libelleRepo.save(TEMP_INSERT_2);
        libelleRepo.save(TEMP_INSERT_3);
    }

    @Test
    public void findByNomIgnoringCase() {
        libelleRepo.findByNomIgnoringCase("poids");
        int i = 0;
    }

}



