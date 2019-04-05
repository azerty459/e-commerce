package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TypeCaracteristiqueBusinessTests {

    @Mock
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;

    @InjectMocks
    private TypeCaracteristiqueBusiness typeCaracteristiqueBusiness;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private void assertData(TypeCaracteristique typeCaracteristique, TypeCaracteristiqueDTO typeCaracteristiqueDTO){
        Assert.assertNotNull(typeCaracteristique);
        Assert.assertNotNull(typeCaracteristiqueDTO);
        Assert.assertEquals(typeCaracteristique.getLibelle(), typeCaracteristiqueDTO.getLibelle());
        Assert.assertEquals(typeCaracteristique.getIdTypeCaracteristique(), typeCaracteristiqueDTO.getIdTypeCaracteristique());
    }

    private void assertData(TypeCaracteristiqueDTO typeCaracteristiqueDTO1, TypeCaracteristiqueDTO typeCaracteristiqueDTO2){
        Assert.assertNotNull(typeCaracteristiqueDTO1);
        Assert.assertNotNull(typeCaracteristiqueDTO2);
        Assert.assertEquals(typeCaracteristiqueDTO1.getLibelle(), typeCaracteristiqueDTO2.getLibelle());
        Assert.assertEquals(typeCaracteristiqueDTO1.getIdTypeCaracteristique(), typeCaracteristiqueDTO2.getIdTypeCaracteristique());
    }

    @Test
    public void can_getAll_no_data() {
        Mockito.when(typeCaracteristiqueRepository.findAll()).thenReturn(new ArrayList<>());
        Collection<TypeCaracteristiqueDTO> typeCaracteristiqueDTOS = typeCaracteristiqueBusiness.getAll();
        Assert.assertNotNull(typeCaracteristiqueDTOS);
        Assert.assertEquals(typeCaracteristiqueDTOS.size(), 0);
        Mockito.verify(typeCaracteristiqueRepository, Mockito.times(1)).findAll();
    }

    @Test(expected = GraphQLCustomException.class)
    public void can_t_update_type_caracteristique_not_found() {
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
        typeCaracteristiqueDTO.setIdTypeCaracteristique(1);
        Mockito.when(typeCaracteristiqueRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        typeCaracteristiqueBusiness.update(typeCaracteristiqueDTO);
    }

    @Test
    public void cant_update_type_caracteristique_null() {
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = typeCaracteristiqueBusiness.update(null);
        Assert.assertNull(typeCaracteristiqueDTO);
    }

    @Test(expected = GraphQLCustomException.class)
    public void cant_delete_type_caracteristique_not_found() {
        TypeCaracteristique typeCaracteristiqueToDelete = new TypeCaracteristique();
        typeCaracteristiqueToDelete.setIdTypeCaracteristique(1);
        Mockito.when(typeCaracteristiqueRepository.findById(typeCaracteristiqueToDelete.getIdTypeCaracteristique())).thenReturn(Optional.empty());
        typeCaracteristiqueBusiness.delete(typeCaracteristiqueToDelete.getIdTypeCaracteristique());
    }

    @Test
    public void can_delete_type_caracteristique() {
        TypeCaracteristique typeCaracteristiqueToDelete = new TypeCaracteristique();
        typeCaracteristiqueToDelete.setIdTypeCaracteristique(1);
        Mockito.when(typeCaracteristiqueRepository.findById(typeCaracteristiqueToDelete.getIdTypeCaracteristique())).thenReturn(Optional.of(typeCaracteristiqueToDelete));
        typeCaracteristiqueBusiness.delete(typeCaracteristiqueToDelete.getIdTypeCaracteristique());
        Mockito.verify(typeCaracteristiqueRepository, Mockito.times(1)).deleteById(typeCaracteristiqueToDelete.getIdTypeCaracteristique());
    }

    @Test
    public void can_update_type_caracteristique() {
        TypeCaracteristique oldTypeCaracteristique = new TypeCaracteristique();
        oldTypeCaracteristique.setIdTypeCaracteristique(1);
        oldTypeCaracteristique.setLibelle("libelle");

        TypeCaracteristiqueDTO newTypeCaracteristique = new TypeCaracteristiqueDTO();
        newTypeCaracteristique.setIdTypeCaracteristique(oldTypeCaracteristique.getIdTypeCaracteristique());
        newTypeCaracteristique.setLibelle("Modification libelle");

        Mockito.when(typeCaracteristiqueRepository.findById(newTypeCaracteristique.getIdTypeCaracteristique())).thenReturn(Optional.of(oldTypeCaracteristique));
        Mockito.when(typeCaracteristiqueRepository.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());

        TypeCaracteristiqueDTO typeCaracteristiqueModified = typeCaracteristiqueBusiness.update(newTypeCaracteristique);
        this.assertData(newTypeCaracteristique, typeCaracteristiqueModified);
    }



}
