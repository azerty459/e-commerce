package com.projet.ecommerce.business.impl;

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
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;

import graphql.GraphQLException;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CaracteristiqueBusinessTests {

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
		Caracteristique caracteristique = buildCaracteristique();
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
	
	
	@NotNull
	private Caracteristique buildCaracteristique() {
		Caracteristique caracteristique = new Caracteristique();
		caracteristique.setLibelle("couleur");
		
		return caracteristique;	
	}
	
	@NotNull
	private CaracteristiqueDTO buildCaracteristiqueDTO() {
		CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
		caracteristiqueDTO.setLibelle("couleur");
		
		return caracteristiqueDTO;	
	}
}
