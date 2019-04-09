package com.projet.ecommerce.business.dto.transformer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

@SpringBootTest
public class CaracteristiqueTransformerTests {

	@Test
	public void  dtoToEntity() {
		CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
		caracteristiqueDTO.setLibelle("couleur");
		
		Caracteristique caracteristique = CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO);
	
		assertEquals(caracteristique.getLibelle(), caracteristiqueDTO.getLibelle());
	}
	
	@Test
	public void entityToDto() {
		Caracteristique caracteristique = new Caracteristique();
		caracteristique.setLibelle("couleur");
		
		CaracteristiqueDTO caracteristiqueDTO = CaracteristiqueTransformer.entityToDto(caracteristique);
		
		assertEquals(caracteristiqueDTO.getLibelle(), caracteristique.getLibelle());	
	}
}
