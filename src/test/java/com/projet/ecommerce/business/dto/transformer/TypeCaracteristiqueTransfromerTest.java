package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TypeCaracteristiqueTransfromerTest {

    @Test
    public void singleEntityToDto() {
        TypeCaracteristique typeCaracteristique = buildTypeCaracteristique();
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = TypeCaracteristiqueTransformer.entityToDto(typeCaracteristique);
        assertEquals(typeCaracteristique, typeCaracteristiqueDTO);

    }

    @Test
    public void singleDtoToEntity() {
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = buildTypeCaracteristiqueDTO();
        TypeCaracteristique typeCaracteristique = TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDTO);
        assertEquals(typeCaracteristique, typeCaracteristiqueDTO);

    }


    private TypeCaracteristique buildTypeCaracteristique() {

        return new TypeCaracteristique(4, "autheur");

    }

    private TypeCaracteristiqueDTO buildTypeCaracteristiqueDTO() {

        return new TypeCaracteristiqueDTO(4, "autheur");

    }


    public void assertEquals(TypeCaracteristique typeCaracteristique,TypeCaracteristiqueDTO typeCaracteristiqueDTO){
        Assert.assertEquals(typeCaracteristique.getLibelle(),typeCaracteristiqueDTO.getLibelle());
        Assert.assertEquals(typeCaracteristique.getId_type_caracteristique(),typeCaracteristiqueDTO.getId_type_caracteristique());


    }
}
