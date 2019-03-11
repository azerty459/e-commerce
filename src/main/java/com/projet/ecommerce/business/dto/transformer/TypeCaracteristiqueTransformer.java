package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeCaracteristiqueTransformer {

    private TypeCaracteristiqueTransformer() {
    }

    public static Collection<TypeCaracteristique> dtoToEntity(Collection<TypeCaracteristiqueDTO> typeCaracteristiqueDTOCollection) {
        List<TypeCaracteristique> typeCaracteristiqueList = new ArrayList<>();
        for (TypeCaracteristiqueDTO typeCaracteristiqueDTO : typeCaracteristiqueDTOCollection) {
            typeCaracteristiqueList.add(dtoToEntity(typeCaracteristiqueDTO));
        }
        return typeCaracteristiqueList;
    }

    public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {
        TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
        typeCaracteristique.setIdTypeCaracteristique(typeCaracteristiqueDTO.getId());
        typeCaracteristique.setType(typeCaracteristiqueDTO.getTypeCarac());
        return typeCaracteristique;
    }

    public static Collection<TypeCaracteristiqueDTO> entityToDto(Collection<TypeCaracteristique> typeCaracteristiqueCollection) {
        List<TypeCaracteristiqueDTO> typeCaracteristiqueDTOList = new ArrayList<>();
        for (TypeCaracteristique typeCaracteristique : typeCaracteristiqueCollection) {
            if (typeCaracteristique.getIdTypeCaracteristique() != 0) {
            	typeCaracteristiqueDTOList.add(entityToDto(typeCaracteristique));
            }
        }
        return typeCaracteristiqueDTOList;
    }
    
    public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typeCaracteristique) {
    	TypeCaracteristiqueDTO typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
    	typeCaracteristiqueDTO.setId(typeCaracteristique.getIdTypeCaracteristique());
    	typeCaracteristiqueDTO.setTypeCarac(typeCaracteristique.getType());
        return typeCaracteristiqueDTO;
    }
}
