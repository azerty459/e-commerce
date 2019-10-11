package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;


public class TypeCaracteristiqueTransformer {

    private TypeCaracteristiqueTransformer(){}

    public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typeCaracteristique){
        TypeCaracteristiqueDTO typeCaractDTO = new TypeCaracteristiqueDTO();
        typeCaractDTO.setType(typeCaracteristique.getType());
        return typeCaractDTO;
    }
}