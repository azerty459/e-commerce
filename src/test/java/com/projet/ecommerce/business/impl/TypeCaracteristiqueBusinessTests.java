//package com.projet.ecommerce.business.impl;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.projet.ecommerce.business.ITypeCaracteristiqueBusiness;
//import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
//import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
//import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
//import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;
//
//@RunWith(MockitoJUnitRunner.Silent.class)
//@SpringBootTest
//public class TypeCaracteristiqueBusinessTests {
//
//	@Mock
//	private ITypeCaracteristiqueBusiness typeCaracterisqueService;
//	
//	@Mock
//	private TypeCaracteristiqueRepository typeCaractRepo;
//
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//	}
//
////	@Rule
////	public ExpectedException thrown = ExpectedException.none();
//
//	/**
//	 * Test de la recherche de tous les types de caractéristiques
//	 */
//	@Test
//	public void getAllTypeCaracteristiqueTest() {
//		Collection<TypeCaracteristique> typeCaracteristiqueCollection = getAllTypeCaracteristique();
//
//		Mockito.when(typeCaractRepo.findAll()).thenReturn(typeCaracteristiqueCollection);
//		
//		Collection<TypeCaracteristiqueDTO> retour = typeCaracterisqueService.getToutLesTypesCaracteristiques();
//
////		Mockito.verify(typeCaractRepo, Mockito.times(1)).findAll();
//		
//		Assert.assertNotNull(retour);
//		Assert.assertEquals(1, retour.size());
//
//	}
//
//	private Collection<TypeCaracteristique> getAllTypeCaracteristique() {
//		Collection<TypeCaracteristique> typeCaracteristiqueCollection = new ArrayList<TypeCaracteristique>();
//		typeCaracteristiqueCollection.add(getTypeCaracteristique());
//		return typeCaracteristiqueCollection;
//	}
//	
//	private TypeCaracteristique getTypeCaracteristique() {
//		TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
//		typeCaracteristique.setIdTypeCaracteristique(1);
//		typeCaracteristique.setNomTypeCaracteristique("Editeur");
//		return typeCaracteristique;
//	}
//	
//	private void assertDataTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristiqueDto, TypeCaracteristique typeCaracteristique) {
//		Assert.assertNotNull(typeCaracteristique);
//		Assert.assertNotNull(typeCaracteristiqueDto);
//        Assert.assertEquals(typeCaracteristiqueDto.getId(), typeCaracteristique.getIdTypeCaracteristique());
//        Assert.assertEquals(typeCaracteristiqueDto.getNomType(), typeCaracteristique.getNomTypeCaracteristique());
//	}
//}
