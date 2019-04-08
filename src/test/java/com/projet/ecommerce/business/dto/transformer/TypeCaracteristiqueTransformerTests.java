package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

public class TypeCaracteristiqueTransformerTests {
	
	private TypeCaracteristiqueDTO getTypeCaracteristiqueDTO() {
		TypeCaracteristiqueDTO typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
		typeCaracteristiqueDTO.setIdTypeCaracteristique(new Random().nextInt(1000));
		typeCaracteristiqueDTO.setTypeCaracteristique("longueur");
		
		return typeCaracteristiqueDTO;
	}
	
	private TypeCaracteristique getTypeCaracteristique() {
		TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
		typeCaracteristique.setIdTypeCaracteristique(new Random().nextInt(1000));
		typeCaracteristique.setTypeCaracteristique("longueur");
		
		return typeCaracteristique;
	}
	
	private void assertData(TypeCaracteristique typeCaracteristique, TypeCaracteristiqueDTO typeCaracteristiqueDTO){
        Assert.assertNotNull(typeCaracteristique);
        Assert.assertNotNull(typeCaracteristiqueDTO);
        Assert.assertEquals(typeCaracteristique.getIdTypeCaracteristique(), typeCaracteristiqueDTO.getIdTypeCaracteristique());
        Assert.assertEquals(typeCaracteristique.getTypeCaracteristique(), typeCaracteristiqueDTO.getTypeCaracteristique());
    }

	private List<TypeCaracteristiqueDTO> getListeTypeCaracteristiqueDTO() {
		List<TypeCaracteristiqueDTO> listeTypeCaracteristiqueDTO = new ArrayList<>();
		listeTypeCaracteristiqueDTO.add(this.getTypeCaracteristiqueDTO());
		listeTypeCaracteristiqueDTO.add(this.getTypeCaracteristiqueDTO());
        return listeTypeCaracteristiqueDTO;
	}
	
	private List<TypeCaracteristique> getListeTypeCaracteristique() {
		List<TypeCaracteristique> listeCaracteristique = new ArrayList<>();
		listeCaracteristique.add(this.getTypeCaracteristique());
		listeCaracteristique.add(this.getTypeCaracteristique());
        return listeCaracteristique;
	}
	
	@Test
    public void singleDtoToEntity() {
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = this.getTypeCaracteristiqueDTO();
        TypeCaracteristique typeCaracteristique = TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTO);

        this.assertData(typeCaracteristique, typeCaracteristiqueDTO);
    }
	
	@Test
    public void singleEntiyToDto() {
        TypeCaracteristique typeCaracteristique = this.getTypeCaracteristique();
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = TypeCaracteristiqueTransformer.entityToDto(typeCaracteristique);

        this.assertData(typeCaracteristique, typeCaracteristiqueDTO);
    }

	@Test
    public void severalDtoToEntity() {
        List<TypeCaracteristiqueDTO> listeTypeCaracteristiqueDTO = this.getListeTypeCaracteristiqueDTO();
        List<TypeCaracteristique> listetypeCaracteristique = new ArrayList<>(TypeCaracteristiqueTransformer.listeDtoToEntity(listeTypeCaracteristiqueDTO));

        Assert.assertNotNull(listetypeCaracteristique);
        this.assertData(listetypeCaracteristique.get(0), listeTypeCaracteristiqueDTO.get(0));
        this.assertData(listetypeCaracteristique.get(1), listeTypeCaracteristiqueDTO.get(1));
    }

    @Test
    public void severalEntityToDto() {
        List<TypeCaracteristique> listetypeCaracteristique = this.getListeTypeCaracteristique();
        List<TypeCaracteristiqueDTO> listeTypeCaracteristiqueDTO = new ArrayList<>(TypeCaracteristiqueTransformer.listeEntityToDto(listetypeCaracteristique));

        Assert.assertNotNull(listeTypeCaracteristiqueDTO);
        this.assertData(listetypeCaracteristique.get(0), listeTypeCaracteristiqueDTO.get(0));
        this.assertData(listetypeCaracteristique.get(1), listeTypeCaracteristiqueDTO.get(1));
    }
	
}
