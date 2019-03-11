package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class CaracteristiqueTransformerTests {

	@Test
    public void singleDtoToEntity() {
		CaracteristiqueDTO caracteristiqueDTO = getCaracteristiqueDTO();
		Caracteristique caracteristique = CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO);
		
		assertData(caracteristique, caracteristiqueDTO);
	}
	
	@Test
    public void singleEntityToDto() {
		Caracteristique caracteristique = getCaracteristique();
		CaracteristiqueDTO caracteristiqueDTO = CaracteristiqueTransformer.entityToDto(caracteristique);
		
		assertData(caracteristique, caracteristiqueDTO);
	}
	
	@Test
    public void severalDtoToEntity() {
		List<CaracteristiqueDTO> caracteristiquesDTO = getListeCaracteristiqueDTO();
		List<Caracteristique> caracteristiques = (List<Caracteristique>) CaracteristiqueTransformer.dtoToEntity(caracteristiquesDTO);
		
		assertData(caracteristiques.get(0), caracteristiquesDTO.get(0));
		assertData(caracteristiques.get(1), caracteristiquesDTO.get(1));
	}
	
	@Test
    public void severalEntityToDto() {
		List<Caracteristique> caracteristiques = getListeCaracteristique();
		List<CaracteristiqueDTO> caracteristiquesDTO = (List<CaracteristiqueDTO>) CaracteristiqueTransformer.entityToDto(caracteristiques);
		
		assertData(caracteristiques.get(0), caracteristiquesDTO.get(0));
		assertData(caracteristiques.get(1), caracteristiquesDTO.get(1));
	}
	
	@Test
    public void severalEntityToDto_null() {
        List<CaracteristiqueDTO> caracteristiqueDTO = new ArrayList<>(CaracteristiqueTransformer.entityToDto(new ArrayList<Caracteristique>()));
        Assert.assertTrue(caracteristiqueDTO.isEmpty());
    }
	
	@Test
    public void severalDtoToEntity_null() {
        List<Caracteristique> caracteristique = new ArrayList<>(CaracteristiqueTransformer.dtoToEntity(new ArrayList<CaracteristiqueDTO>()));
        Assert.assertTrue(caracteristique.isEmpty());
    }
	
	private Caracteristique getCaracteristique() {
		TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
		typeCaracteristique.setIdTypeCaracteristique(new Random().nextInt(100));
		typeCaracteristique.setType("Type");
		
		Caracteristique caracteristique = new Caracteristique();
		caracteristique.setIdCaracteristique(new Random().nextInt(100));
		caracteristique.setTypeCaracteristique(typeCaracteristique);
		caracteristique.setValeur("valeur");
		
		return caracteristique;
	}
	
	private CaracteristiqueDTO getCaracteristiqueDTO() {
		TypeCaracteristiqueDTO typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
		typeCaracteristiqueDTO.setId(new Random().nextInt(100));
		typeCaracteristiqueDTO.setTypeCarac("Type");
		
		CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
		caracteristiqueDTO.setId(new Random().nextInt(100));
		caracteristiqueDTO.setTypeCaracteristiqueDTO(typeCaracteristiqueDTO);
		caracteristiqueDTO.setValeur("valeur");
		
		return caracteristiqueDTO;
	}
	
	private List<Caracteristique> getListeCaracteristique(){
        List<Caracteristique> liste = new ArrayList<>();
        liste.add(this.getCaracteristique());
        liste.add(this.getCaracteristique());
        return liste;
    }
	
	private List<CaracteristiqueDTO> getListeCaracteristiqueDTO(){
        List<CaracteristiqueDTO> liste = new ArrayList<>();
        liste.add(this.getCaracteristiqueDTO());
        liste.add(this.getCaracteristiqueDTO());
        return liste;
    }
	
    private void assertData(Caracteristique caracteristique, CaracteristiqueDTO caracteristiqueDTO){
        Assert.assertNotNull(caracteristique);
        Assert.assertNotNull(caracteristiqueDTO);
        Assert.assertEquals(caracteristique.getValeur(),caracteristiqueDTO.getValeur());
        Assert.assertEquals(caracteristique.getIdCaracteristique(),caracteristiqueDTO.getId());
        Assert.assertEquals(caracteristique.getTypeCaracteristique().getIdTypeCaracteristique(),caracteristiqueDTO.getTypeCaracteristiqueDTO().getId());
        Assert.assertEquals(caracteristique.getTypeCaracteristique().getType(),caracteristiqueDTO.getTypeCaracteristiqueDTO().getTypeCarac());
    }
}