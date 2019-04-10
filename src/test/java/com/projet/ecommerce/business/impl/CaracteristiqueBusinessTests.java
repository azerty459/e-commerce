package com.projet.ecommerce.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.graph.GraphBuilder;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;

import graphql.GraphQLException;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CaracteristiqueBusinessTests {

	private Caracteristique caracteristique;
	private CaracteristiqueDTO caracteristiqueDTO;
	
	@Mock
	private CaracteristiqueRepository caracteristiqueRepository;

	@InjectMocks
	private CaracteristiqueBusiness caracteristiqueBusiness;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void create() {		
		caracteristique = buildCaracteristique();
		Mockito.when(caracteristiqueRepository.save(Mockito.any())).thenReturn(caracteristique);
		
		CaracteristiqueDTO caracteristiqueDTO = buildCaracteristiqueDTO();
		CaracteristiqueDTO retour = caracteristiqueBusiness.createCaracteristique(caracteristiqueDTO);
		
		Assert.assertNotNull(retour);
		Assert.assertEquals(caracteristique.getLibelle(), retour.getLibelle());
	}
	
	@Test(expected = GraphQLException.class)
	public void createWithError(){
		CaracteristiqueDTO caracteristiqueDTO = buildCaracteristiqueDTO();
		caracteristiqueDTO.setLibelle("");
		caracteristiqueBusiness.createCaracteristique(caracteristiqueDTO);
	}
	
	@Test
	public void getAllCaracteristique() {
		List<Caracteristique> caracteristiqueList = new ArrayList<>();
		Mockito.when(caracteristiqueRepository.findAll()).thenReturn(caracteristiqueList);
		Assert.assertEquals(0, caracteristiqueBusiness.getAllCaracteristique().size());
		
		Caracteristique caracteristique = buildCaracteristique();
		caracteristiqueList.add(caracteristique);
		Assert.assertEquals(1, caracteristiqueBusiness.getAllCaracteristique().size());
		
		List<CaracteristiqueDTO> caracteristiqueDTOList = caracteristiqueBusiness.getAllCaracteristique();
        Assert.assertEquals(caracteristiqueBusiness.getAllCaracteristique().size(), 1);
	
        CaracteristiqueDTO retour = caracteristiqueDTOList.get(0);
        Assert.assertEquals(caracteristique.getLibelle(), retour.getLibelle());
	}
	
	
	@Test
	public void deleteCaracteristique() {
		caracteristique = buildCaracteristique();
		Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caracteristique));
		
		Assert.assertTrue(caracteristiqueBusiness.deleteCaracteristique(Mockito.anyInt()));
	}
	
	@Test
	public void updateCaracteristique() {
		caracteristique = buildCaracteristique();
		Mockito.when(caracteristiqueRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(caracteristique));
		Mockito.when(caracteristiqueRepository.save(Mockito.any())).thenReturn(caracteristique);
		
		CaracteristiqueDTO retourNull = null;
		Assert.assertNull(caracteristiqueBusiness.updateCaracteristique(retourNull));
		
		caracteristiqueDTO = buildCaracteristiqueDTO();
		Assert.assertNotNull(caracteristiqueBusiness.updateCaracteristique(caracteristiqueDTO));
	
		thrown.expect(GraphQLException.class);
		caracteristiqueDTO.setLibelle("");
		caracteristiqueBusiness.updateCaracteristique(caracteristiqueDTO);	
	}
	
	@NotNull
	private Caracteristique buildCaracteristique() {
		caracteristique = new Caracteristique();
		caracteristique.setLibelle("couleur");
		
		return caracteristique;	
	}
	
	@NotNull
	private CaracteristiqueDTO buildCaracteristiqueDTO() {
		caracteristiqueDTO = new CaracteristiqueDTO();
		caracteristiqueDTO.setLibelle("couleur");
		
		return caracteristiqueDTO;	
	}
}
