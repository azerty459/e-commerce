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
public class TypeCaracteristiqueTransformerTests {

	@Test
    public void singleDtoToEntity() {
		TypeCaracteristiqueDTO typeCaracteristiqueDTO = getTypeCaracteristiqueDTO();
		TypeCaracteristique typeCaracteristique = TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTO);
		
		assertData(typeCaracteristique, typeCaracteristiqueDTO);
	}
	
	@Test
    public void singleEntityToDto() {
		TypeCaracteristique typeCaracteristique = getTypeCaracteristique();
		TypeCaracteristiqueDTO typeCaracteristiqueDTO = TypeCaracteristiqueTransformer.entityToDto(typeCaracteristique);
		
		assertData(typeCaracteristique, typeCaracteristiqueDTO);
	}
	
	@Test
    public void severalDtoToEntity() {
		List<TypeCaracteristiqueDTO> typeCaracteristiquesDTO = getListeTypeCaracteristiqueDTO();
		List<TypeCaracteristique> typeCaracteristiques = (List<TypeCaracteristique>) TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiquesDTO);
		
		assertData(typeCaracteristiques.get(0), typeCaracteristiquesDTO.get(0));
		assertData(typeCaracteristiques.get(1), typeCaracteristiquesDTO.get(1));
	}
	
	@Test
    public void severalEntityToDto() {
		List<TypeCaracteristique> typeCaracteristiques = getListeTypeCaracteristique();
		List<TypeCaracteristiqueDTO> typeCaracteristiquesDTO = (List<TypeCaracteristiqueDTO>) TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiques);
		
		assertData(typeCaracteristiques.get(0), typeCaracteristiquesDTO.get(0));
		assertData(typeCaracteristiques.get(1), typeCaracteristiquesDTO.get(1));
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
	
	private TypeCaracteristique getTypeCaracteristique() {
		TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
		typeCaracteristique.setIdTypeCaracteristique(new Random().nextInt(100));
		typeCaracteristique.setType("Type");
		
		return typeCaracteristique;
	}
	
	private TypeCaracteristiqueDTO getTypeCaracteristiqueDTO() {
		TypeCaracteristiqueDTO typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
		typeCaracteristiqueDTO.setId(new Random().nextInt(100));
		typeCaracteristiqueDTO.setTypeCarac("Type");
		
		return typeCaracteristiqueDTO;
	}
	
	private List<TypeCaracteristique> getListeTypeCaracteristique(){
        List<TypeCaracteristique> liste = new ArrayList<>();
        liste.add(this.getTypeCaracteristique());
        liste.add(this.getTypeCaracteristique());
        return liste;
    }
	
	private List<TypeCaracteristiqueDTO> getListeTypeCaracteristiqueDTO(){
        List<TypeCaracteristiqueDTO> liste = new ArrayList<>();
        liste.add(this.getTypeCaracteristiqueDTO());
        liste.add(this.getTypeCaracteristiqueDTO());
        return liste;
    }
	
    private void assertData(TypeCaracteristique typeCaracteristique, TypeCaracteristiqueDTO typeCaracteristiqueDTO){
        Assert.assertNotNull(typeCaracteristique);
        Assert.assertNotNull(typeCaracteristiqueDTO);
        Assert.assertEquals(typeCaracteristique.getIdTypeCaracteristique(),typeCaracteristiqueDTO.getId());
        Assert.assertEquals(typeCaracteristique.getType(),typeCaracteristiqueDTO.getTypeCarac());
    }
}