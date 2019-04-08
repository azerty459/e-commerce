package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

@SpringBootTest
public class TypeCaracteristiqueTransformerTests {

	private static final TypeCaracteristiqueDTO TYPE_CARACTERISTIQUE_DTO1;
	private static final TypeCaracteristiqueDTO TYPE_CARACTERISTIQUE_DTO2;
	private static final TypeCaracteristique TYPE_CARACTERISTIQUE_1;
	private static final TypeCaracteristique TYPE_CARACTERISTIQUE_2;
	
	static {
		TYPE_CARACTERISTIQUE_DTO1 = new TypeCaracteristiqueDTO();
		TYPE_CARACTERISTIQUE_DTO1.setId(1);
		TYPE_CARACTERISTIQUE_DTO1.setNomType("Editeur");
		
		TYPE_CARACTERISTIQUE_DTO2 = new TypeCaracteristiqueDTO();
		TYPE_CARACTERISTIQUE_DTO2.setId(2);
		TYPE_CARACTERISTIQUE_DTO2.setNomType("Langue");
		
		TYPE_CARACTERISTIQUE_1 = new TypeCaracteristique();
		TYPE_CARACTERISTIQUE_1.setIdTypeCaracteristique(1);
		TYPE_CARACTERISTIQUE_1.setNomTypeCaracteristique("Editeur");
		
		TYPE_CARACTERISTIQUE_2 = new TypeCaracteristique();
		TYPE_CARACTERISTIQUE_2.setIdTypeCaracteristique(2);
		TYPE_CARACTERISTIQUE_2.setNomTypeCaracteristique("Langue");
	}
	
	@Test
    public void singleDtoToEntity() {
        TypeCaracteristique typeCaracteristique = TypeCaracteristiqueTransformer.dtoToEntity(TYPE_CARACTERISTIQUE_DTO1);

        assertDataTypeCaracteristique(TYPE_CARACTERISTIQUE_DTO1, typeCaracteristique);
    }
	
	@Test
    public void singleEntityToDto() {
        TypeCaracteristiqueDTO typeCaracteristiqueDto = TypeCaracteristiqueTransformer.entityToDto(TYPE_CARACTERISTIQUE_1);

        assertDataTypeCaracteristique(typeCaracteristiqueDto, TYPE_CARACTERISTIQUE_1);
    }
	
	@Test
    public void ListeDtoToEntity() {
        List<TypeCaracteristiqueDTO> typeCaracteristiqueDTOList = new ArrayList<>();
        typeCaracteristiqueDTOList.add(TYPE_CARACTERISTIQUE_DTO1);
        typeCaracteristiqueDTOList.add(TYPE_CARACTERISTIQUE_DTO2);

        List<TypeCaracteristique> typeCaracteristiqueList = new ArrayList<>(TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTOList));

        Assert.assertNotNull(typeCaracteristiqueList);
        this.assertDataTypeCaracteristique(typeCaracteristiqueDTOList.get(0), typeCaracteristiqueList.get(0));
        this.assertDataTypeCaracteristique(typeCaracteristiqueDTOList.get(1), typeCaracteristiqueList.get(1));
    }
	
	@Test
    public void ListeEntityToDto() {
        List<TypeCaracteristique> typeCaracteristiqueList = new ArrayList<>();
        typeCaracteristiqueList.add(TYPE_CARACTERISTIQUE_1);
        typeCaracteristiqueList.add(TYPE_CARACTERISTIQUE_2);

        List<TypeCaracteristiqueDTO> typeCaracteristiqueDtoList = new ArrayList<>(TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueList));

        Assert.assertNotNull(typeCaracteristiqueList);
        this.assertDataTypeCaracteristique(typeCaracteristiqueDtoList.get(0), typeCaracteristiqueList.get(0));
        this.assertDataTypeCaracteristique(typeCaracteristiqueDtoList.get(1), typeCaracteristiqueList.get(1));
    }

	private void assertDataTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristiqueDto, TypeCaracteristique typeCaracteristique) {
		Assert.assertNotNull(typeCaracteristique);
		Assert.assertNotNull(typeCaracteristiqueDto);
        Assert.assertEquals(typeCaracteristiqueDto.getId(), typeCaracteristique.getIdTypeCaracteristique());
        Assert.assertEquals(typeCaracteristiqueDto.getNomType(), typeCaracteristique.getNomTypeCaracteristique());
	}
}
