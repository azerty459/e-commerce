package com.projet.ecommerce.business.transformer;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TypeCaracteristiqueTransformerTests {

    private static final TypeCaracteristique TYPE_CARACTERISTIQUE1;
    private static final TypeCaracteristique TYPE_CARACTERISTIQUE2;

    private static final TypeCaracteristiqueDTO TYPE_CARACTERISTIQUE_DTO1;
    private static final TypeCaracteristiqueDTO TYPE_CARACTERISTIQUE_DTO2;

    static {
        TYPE_CARACTERISTIQUE1 = new TypeCaracteristique();
        TYPE_CARACTERISTIQUE1.setIdTypeCaracteristique(1);
        TYPE_CARACTERISTIQUE1.setType("testType1");

        TYPE_CARACTERISTIQUE2 = new TypeCaracteristique();
        TYPE_CARACTERISTIQUE2.setIdTypeCaracteristique(2);
        TYPE_CARACTERISTIQUE2.setType("testType2");

        TYPE_CARACTERISTIQUE_DTO1 = new TypeCaracteristiqueDTO();
        TYPE_CARACTERISTIQUE_DTO1.setIdTypeCaracteristique(1);
        TYPE_CARACTERISTIQUE_DTO1.setType("testType1");

        TYPE_CARACTERISTIQUE_DTO2 = new TypeCaracteristiqueDTO();
        TYPE_CARACTERISTIQUE_DTO2.setIdTypeCaracteristique(2);
        TYPE_CARACTERISTIQUE_DTO2.setType("testType2");
    }

    @Test
    public void singleDtoToEntity(){
        TypeCaracteristique retour = TypeCaracteristiqueTransformer.dtoToEntity(TYPE_CARACTERISTIQUE_DTO1);

        Assert.assertNotNull(retour);
        Assert.assertEquals(TYPE_CARACTERISTIQUE_DTO1.getIdTypeCaracteristique(), retour.getIdTypeCaracteristique());
        Assert.assertEquals(TYPE_CARACTERISTIQUE_DTO1.getType(), retour.getType());
    }

    @Test
    public void singleEntityToDto(){
        TypeCaracteristiqueDTO retour = TypeCaracteristiqueTransformer.entityToDto(TYPE_CARACTERISTIQUE1);

        Assert.assertNotNull(retour);
        Assert.assertEquals(retour.getType(), TYPE_CARACTERISTIQUE1.getType());
        Assert.assertEquals(retour.getIdTypeCaracteristique(), TYPE_CARACTERISTIQUE1.getIdTypeCaracteristique());
    }

    @Test
    public void severalDtoToEntity(){
        List<TypeCaracteristiqueDTO> caracteristiqueDTOList = new ArrayList<>();
        caracteristiqueDTOList.add(TYPE_CARACTERISTIQUE_DTO1);
        caracteristiqueDTOList.add(TYPE_CARACTERISTIQUE_DTO2);

        List<TypeCaracteristique> caracteristiqueList = new ArrayList<>(TypeCaracteristiqueTransformer.dtoToEntity(caracteristiqueDTOList));

        Assert.assertNotNull(caracteristiqueList);
        Assert.assertEquals(caracteristiqueList.get(0).getIdTypeCaracteristique(), caracteristiqueDTOList.get(0).getIdTypeCaracteristique());
        Assert.assertEquals(caracteristiqueList.get(0).getType(), caracteristiqueDTOList.get(0).getType());
        Assert.assertEquals(caracteristiqueList.get(1).getIdTypeCaracteristique(), caracteristiqueDTOList.get(1).getIdTypeCaracteristique());
        Assert.assertEquals(caracteristiqueList.get(1).getType(), caracteristiqueDTOList.get(1).getType());
    }

    @Test
    public void severalEntityToDto(){
        List<TypeCaracteristique> typeCaracteristiqueList;
        typeCaracteristiqueList = new ArrayList<>();
        typeCaracteristiqueList.add(TYPE_CARACTERISTIQUE1);
        typeCaracteristiqueList.add(TYPE_CARACTERISTIQUE2);

        List<TypeCaracteristiqueDTO> caracteristiqueDTOList = new ArrayList<>(TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueList));

        Assert.assertNotNull(caracteristiqueDTOList);
        Assert.assertEquals(typeCaracteristiqueList.get(0).getType(), caracteristiqueDTOList.get(0).getType());
        Assert.assertEquals(typeCaracteristiqueList.get(0).getIdTypeCaracteristique(), caracteristiqueDTOList.get(0).getIdTypeCaracteristique());
        Assert.assertEquals(typeCaracteristiqueList.get(1).getType(), caracteristiqueDTOList.get(1).getType());
        Assert.assertEquals(typeCaracteristiqueList.get(1).getIdTypeCaracteristique(), caracteristiqueDTOList.get(1).getIdTypeCaracteristique());
    }
}