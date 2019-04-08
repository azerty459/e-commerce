package com.projet.ecommerce.business.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class TypeCaracteristiqueBusiness {

	@Mock
	private TypeCaracteristiqueBusiness typeCaracterisqueService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

//	@Rule
//	public ExpectedException thrown = ExpectedException.none();

	/**
	 * Test de la recherche de tous les types de caractéristiques
	 */
//	@Test
//	public void getAllTypeCaracteristiqueTest() {
//		Collection<TypeCaracteristique> typeCaracteristiqueCollection = getAllTypeCaracteristique();
//
//		Mockito.when(typeCaracterisqueService.getToutLesTypesCaracteristiques()).thenReturn(typeCaracteristiqueCollection);
//		
//		typeCaracterisqueService
//
//		Mockito.verify(typeCaracterisqueRepo, Mockito.times(1)).findById(1);
//
//	}

	private Collection<TypeCaracteristique> getAllTypeCaracteristique() {
		Collection<TypeCaracteristique> typeCaracteristiqueCollection = new ArrayList<TypeCaracteristique>();
		typeCaracteristiqueCollection.add(getTypeCaracteristique());
		return typeCaracteristiqueCollection;
	}
	
	private TypeCaracteristique getTypeCaracteristique() {
		TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
		typeCaracteristique.setIdTypeCaracteristique(1);
		typeCaracteristique.setNomTypeCaracteristique("Editeur");
		return typeCaracteristique;
	}
	
	private void assertDataTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristiqueDto, TypeCaracteristique typeCaracteristique) {
		Assert.assertNotNull(typeCaracteristique);
		Assert.assertNotNull(typeCaracteristiqueDto);
        Assert.assertEquals(typeCaracteristiqueDto.getId(), typeCaracteristique.getIdTypeCaracteristique());
        Assert.assertEquals(typeCaracteristiqueDto.getNomType(), typeCaracteristique.getNomTypeCaracteristique());
	}
}
