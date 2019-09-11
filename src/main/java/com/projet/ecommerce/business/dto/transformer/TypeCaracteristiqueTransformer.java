package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.ArrayList;
import java.util.Collection;

public class TypeCaracteristiqueTransformer {

    private TypeCaracteristiqueTransformer(){}

    public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typeCaracteristique){
        TypeCaracteristiqueDTO typeCaractDTO = new TypeCaracteristiqueDTO();
        typeCaractDTO.setType(typeCaracteristique.getType());
        return typeCaractDTO;
    }

    public static Collection<TypeCaracteristiqueDTO> entityToDto(Collection<TypeCaracteristique> typeCaracteristiqueList){
        if(typeCaracteristiqueList == null)
            return null;

        final ArrayList<TypeCaracteristiqueDTO> listTypeCaractDTO = new ArrayList<>(typeCaracteristiqueList.size());
        typeCaracteristiqueList.forEach( typeCaracteristique -> listTypeCaractDTO.add(entityToDto(typeCaracteristique)));
        return listTypeCaractDTO;
    }
}
