package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeCaracteristiqueTransformer {

    public static Collection<TypeCaracteristique> dtoToEntity(Collection<TypeCaracteristiqueDTO> typeCaracteristiqueDTOCollection){
        Collection<TypeCaracteristique> typeCaracteristiqueCollection = new ArrayList<>();
        for (TypeCaracteristiqueDTO typeCaracteristiqueDTO : typeCaracteristiqueDTOCollection) {
            typeCaracteristiqueCollection.add(dtoToEntity(typeCaracteristiqueDTO));
        }
        return typeCaracteristiqueCollection;
    }

    public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typeCaracteristiqueDTO){
        TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
        typeCaracteristique.setType(typeCaracteristiqueDTO.getType());
        typeCaracteristique.setIdTypeCaracteristique(typeCaracteristiqueDTO.getIdTypeCaracteristique());
        return typeCaracteristique;
    }

    public static Collection<TypeCaracteristiqueDTO> entityToDto(Collection<TypeCaracteristique> typeCaracteristiqueCollection){
        Collection<TypeCaracteristiqueDTO> typeCaracteristiqueDTOCollection = new ArrayList<>();
        for (TypeCaracteristique typeCaracteristique : typeCaracteristiqueCollection) {
            typeCaracteristiqueDTOCollection.add(entityToDto(typeCaracteristique));
        }
        return typeCaracteristiqueDTOCollection;
    }

    public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typeCaracteristique){
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
        typeCaracteristiqueDTO.setIdTypeCaracteristique(typeCaracteristique.getIdTypeCaracteristique());
        typeCaracteristiqueDTO.setType(typeCaracteristique.getType());
        return typeCaracteristiqueDTO;
    }
}
