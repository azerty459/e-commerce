package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.LibelleDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class LibelleBusinessTests {
    private static LibelleDTO lDTOEditeur;
    private static LibelleDTO lDTOBroche;
    private static LibelleDTO lDTODimensions;


    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");
    }

    @Before
    public void setUp() {
        lDTOEditeur = libelleBusiness.add("Editeur");
        lDTOBroche = libelleBusiness.add("Broché");
        lDTODimensions = libelleBusiness.add("Dimensions");
    }

    @Autowired
    private LibelleBusiness libelleBusiness;


    /**
     * Méthodes à tester :
     * add(String nom) : cas normal, cas null, cas nom vide, cas libellé de ce nom déja existant
     * get(int idLibelle) : cas normal, cas non trouvé
     * getAll(String motCle) : cas normal, cas vide, cas param null
     * update(int idLibelleExistant, String nouveauNom) : cas normal, cas pas de libellé préexistant, cas nouveauNom null ou vide,
     * delete(int idLibelle): cas normal, cas libellé non trouvé
     */

    @Test
    public void add() {
        //Cas normal
        LibelleDTO l_puissanceFiscale_DTO = libelleBusiness.add("Puissance fiscale en CV");
        assertEquals("Puissance fiscale en CV", l_puissanceFiscale_DTO.getNom());
        assertEquals(4,libelleBusiness.getAll(null).size());


        //Cas null
        LibelleDTO lAddNull = libelleBusiness.add(null);
        assertNull(lAddNull);
        assertEquals(4,libelleBusiness.getAll(null).size());

        boolean erreurDeclenchee = false;
        //Cas nom vide
        try {
            libelleBusiness.add("       ");
        } catch (Exception e) {
            erreurDeclenchee = true;
            String expectedMessage = "Le nom du libellé ne peut être vide";
            String actualMessage = e.getMessage();
            assertEquals(expectedMessage,actualMessage);
            assertEquals(GraphQLCustomException.class, e.getClass());
        }
        assertTrue(erreurDeclenchee);
        assertEquals(4,libelleBusiness.getAll(null).size());

        erreurDeclenchee = false;
        //Cas existe déja
        try {
            libelleBusiness.add("puissance fiscale en cv");
        } catch (Exception e) {
            erreurDeclenchee = true;
            String expectedMessage = "Un libellé de ce nom existe déja";
            String actualMessage = e.getMessage();
            assertEquals(expectedMessage,actualMessage);
            assertEquals(GraphQLCustomException.class, e.getClass());
        }
        assertTrue(erreurDeclenchee);
        assertEquals(4,libelleBusiness.getAll(null).size());

    }

    @Test
    public void get() {
        //Cas normal
        LibelleDTO foundLDto = libelleBusiness.get(lDTOEditeur.getId());
        assertEquals("Editeur", foundLDto.getNom());

        boolean erreurDeclenchee = false;
        //Cas non trouvé
        try {
            libelleBusiness.get(789401);
        } catch (Exception e) {
            erreurDeclenchee = true;
            String expectedMessage = "Pas de libellé trouvé pour l'id : " + 789401;
            String actualMessage = e.getMessage();
            assertEquals(expectedMessage,actualMessage);
            assertEquals(GraphQLCustomException.class, e.getClass());
        }
        assertTrue(erreurDeclenchee);
    }

    @Test
    public void getAll() {
        //cas normal
        Collection<LibelleDTO> foundLs = libelleBusiness.getAll("di");
        assertEquals(2,foundLs.size());

        foundLs = libelleBusiness.getAll("DI");
        assertEquals(2, foundLs.size());

        foundLs = libelleBusiness.getAll("puissance");
        assertEquals(0, foundLs.size());

        // cas vide
        foundLs = libelleBusiness.getAll("");
        assertEquals(3,foundLs.size());

        // cas param null
        foundLs = libelleBusiness.getAll(null);
        assertEquals(3, foundLs.size());
    }

    //TODO : update et delete
    //update(int idLibelleExistant, String nouveauNom) : cas normal, cas pas de libellé préexistant, cas nouveauNom null ou vide,
    //delete(int idLibelle): cas normal, cas libellé non trouvé
}
