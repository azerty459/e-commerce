/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CaracteristiqueBusinessTests {
    
    @Mock
    private CaracteristiqueRepository caracteristiqueRepository;
    
    @InjectMocks
    private CaracteristiqueBusiness caracteristiqueBusiness;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void testAdd() {
        Caracteristique caracteristique = buildCaracteristique();
        Mockito.when(caracteristiqueRepository.save(Mockito.any())).thenReturn(caracteristique); 
        
        CaracteristiqueDTO retour = caracteristiqueBusiness.add(buildCaracteristiqueDTO());
        Assert.assertNotNull(retour);
        Assert.assertEquals(caracteristique.getIdCaracteristique(), retour.getId());
        Assert.assertEquals(caracteristique.getLibelle(), retour.getLibelle());
        //Test des exceptions
        thrown.expect(GraphQLCustomException.class);
        //Ajoute null
        retour = caracteristiqueBusiness.add(null);
        Assert.assertNull(retour);
        //Ajoute sans libelle
        retour = caracteristiqueBusiness.add(new CaracteristiqueDTO(8, "\t"));
        Assert.assertNull(retour);
    }
    
    @Test
    public void testUpdate() {
        Caracteristique caracteristique = buildCaracteristique();
        Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caracteristique));
        Mockito.when(caracteristiqueRepository.save(Mockito.any())).thenReturn(caracteristique);
        

        CaracteristiqueDTO retour = caracteristiqueBusiness.update(buildCaracteristiqueDTO());
        Assert.assertNotNull(retour);
        Assert.assertEquals(caracteristique.getIdCaracteristique(), retour.getId());
        Assert.assertEquals(caracteristique.getLibelle(), retour.getLibelle());
        //Test des exceptions
        thrown.expect(GraphQLCustomException.class);
        //Update null
        retour = caracteristiqueBusiness.add(null);
        Assert.assertNull(retour);
        //Update sans libelle
        retour = caracteristiqueBusiness.add(new CaracteristiqueDTO(8, "\t"));
        Assert.assertNull(retour);
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testUpdateNew() {
        Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        
        caracteristiqueBusiness.update(buildCaracteristiqueDTO());
    }
    
    @Test
    public void testDelete() {
        Caracteristique caracteristique = buildCaracteristique();
        Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caracteristique));
        
        Assert.assertTrue(caracteristiqueBusiness.delete(8));
    }
    
    @Test
    public void testDeleteBadId() {
        Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        
        Assert.assertFalse(caracteristiqueBusiness.delete(8));
    }
    
    @NotNull
    private Caracteristique buildCaracteristique() {
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setIdCaracteristique(8);
        caracteristique.setLibelle("Test");
        return caracteristique;
    }
    
    @NotNull
    private CaracteristiqueDTO buildCaracteristiqueDTO() {
        return new CaracteristiqueDTO(8, "Test");
    }
    
}
