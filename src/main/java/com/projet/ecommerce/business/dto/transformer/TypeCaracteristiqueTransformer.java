package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeCaracteristiqueTransformer {

    /*public static Collection<TypeCaracteristique> dtoToEntity(Collection<TypeCaracteristiqueDTO> typeCaracteristiqueDTOCollection){
        List<TypeCaracteristique> typeCaracteristiqueList = new ArrayList<>();
        for (TypeCaracteristiqueDTO typeCaracteristiqueDTO : typeCaracteristiqueDTOCollection) {
            typeCaracteristiqueList.add(dtoToEntity(typeCaracteristiqueDTO));
        }
        return typeCaracteristiqueList;
    }*/

    public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {
        TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
        typeCaracteristique.setLibelle(typeCaracteristiqueDTO.getLibelle());
        typeCaracteristique.setIdTypeCaracteristique(typeCaracteristiqueDTO.getIdTypeCaracteristique());
        return typeCaracteristique;
    }

    public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typeCaracteristique) {
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
        typeCaracteristiqueDTO.setLibelle(typeCaracteristique.getLibelle());
        typeCaracteristiqueDTO.setIdTypeCaracteristique(typeCaracteristique.getIdTypeCaracteristique());
        return typeCaracteristiqueDTO;
    }

}
