package com.projet.ecommerce.business.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

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
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CaracteristiqueBusinessTests {
	
	@Mock
    private ProduitRepository produitRepository;
	
	@Mock
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;
	
	@Mock
    private CaracteristiqueRepository caracteristiqueRepository;
	
	@InjectMocks
    private CaracteristiqueBusiness caracteristiqueBusiness;
	
	private TypeCaracteristiqueDTO typeCaracteristiqueDTO;
    private CaracteristiqueDTO caracteristiqueDTO;
    private Produit produit;
    
	
	@Before
    public void setUp() {
		
		MockitoAnnotations.initMocks(this);
        
        typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
        typeCaracteristiqueDTO.setId(1);
        typeCaracteristiqueDTO.setTypeCarac("Broché");
        
        caracteristiqueDTO  = new CaracteristiqueDTO();
        caracteristiqueDTO.setId(1);
        caracteristiqueDTO.setTypeCaracteristiqueDTO(typeCaracteristiqueDTO);
        caracteristiqueDTO.setValeur("223 pages");
        
        produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());
		
	}
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
    public void add() {
		
		Mockito.when(caracteristiqueRepository.save(Mockito.any())).thenReturn(CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO));
		Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
		Mockito.when(typeCaracteristiqueRepository.findById(Mockito.any())).thenReturn(Optional.of(TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTO)));
		
		CaracteristiqueDTO retour = caracteristiqueBusiness.add(caracteristiqueDTO, "A05A01");
		Assert.assertNotNull(retour);
		Assert.assertEquals(retour.getId(), caracteristiqueDTO.getId());
		Assert.assertEquals(retour.getTypeCaracteristiqueDTO().getId(), caracteristiqueDTO.getTypeCaracteristiqueDTO().getId());
		Assert.assertEquals(retour.getTypeCaracteristiqueDTO().getTypeCarac(), caracteristiqueDTO.getTypeCaracteristiqueDTO().getTypeCarac());
		Assert.assertEquals(retour.getValeur(), caracteristiqueDTO.getValeur());
		
	}
	
	@Test
	public void addAnExistingOne() {
		Mockito.when(caracteristiqueRepository.findById(Mockito.anyString())).thenReturn(Optional.of(CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO)));
		
        thrown.expect(GraphQLCustomException.class);
		CaracteristiqueDTO retour = caracteristiqueBusiness.add(caracteristiqueDTO, "A05A01");
		Assert.assertNull(retour);
	}
	
	@Test
	public void addOnANonExistingProject() {
		
		 thrown.expect(GraphQLCustomException.class);
		CaracteristiqueDTO retour = caracteristiqueBusiness.add(caracteristiqueDTO, "A05A01");
		Assert.assertNull(retour);
	}
	
	@Test
	public void addWithANonExistingType() {
		
		Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
		
		thrown.expect(GraphQLCustomException.class);
		CaracteristiqueDTO retour = caracteristiqueBusiness.add(caracteristiqueDTO, "A05A01");
		Assert.assertNull(retour);
	}
	
}
