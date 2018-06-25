package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.ArrayList;
import java.util.Collection;

public class TypeCaracteristiqueTransformer {

    private TypeCaracteristiqueTransformer() {
    }

    /**
     * Transforme une collection d'objets TypeCaracteristiqueDTO en une collection d'objets TypeCaracteristique.
     *
     * @param typeCaracteristiqueDTOCollection Une collection d'objets TypeCaracteristiqueDTO
     * @return une collection d'objets TypeCaracteristique
     */
    public static Collection<TypeCaracteristique> dtoToEntity(Collection<TypeCaracteristiqueDTO> typeCaracteristiqueDTOCollection) {
        Collection<TypeCaracteristique> typeCaracteristiqueCollection = new ArrayList<>();
        for (TypeCaracteristiqueDTO typeCaracteristiqueDTO : typeCaracteristiqueDTOCollection) {
            typeCaracteristiqueCollection.add(dtoToEntity(typeCaracteristiqueDTO));
        }
        return typeCaracteristiqueCollection;
    }

    /**
     * Transforme un objet TypeCaracteristiqueDTO en TypeCaracteristique.
     *
     * @param typeCaracteristiqueDTO Un objet TypeCaracteristiqueDTO
     * @return un objet TypeCaracteristique
     */
    public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {
        TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
        typeCaracteristique.setType(typeCaracteristiqueDTO.getType());
        typeCaracteristique.setIdTypeCaracteristique(typeCaracteristiqueDTO.getIdTypeCaracteristique());
        return typeCaracteristique;
    }

    /**
     * Transforme une collection d'objets TypeCaracteristique en une collection d'objets TypeCaracteristiqueDTO.
     *
     * @param typeCaracteristiqueCollection Une collection d'objets TypeCaracteristique
     * @return une collection d'objets TypeCaracteristiqueDTO
     */
    public static Collection<TypeCaracteristiqueDTO> entityToDto(Collection<TypeCaracteristique> typeCaracteristiqueCollection) {
        Collection<TypeCaracteristiqueDTO> typeCaracteristiqueDTOCollection = new ArrayList<>();
        for (TypeCaracteristique typeCaracteristique : typeCaracteristiqueCollection) {
            typeCaracteristiqueDTOCollection.add(entityToDto(typeCaracteristique));
        }
        return typeCaracteristiqueDTOCollection;
    }

    /**
     * Transforme un objet TypeCaracteristique en TypeCaracteristiqueDTO.
     *
     * @param typeCaracteristique Un objet TypeCaracteristique
     * @return un objet TypeCaracteristiqueDTO
     */
    public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typeCaracteristique) {
        TypeCaracteristiqueDTO typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
        typeCaracteristiqueDTO.setIdTypeCaracteristique(typeCaracteristique.getIdTypeCaracteristique());
        typeCaracteristiqueDTO.setType(typeCaracteristique.getType());
        return typeCaracteristiqueDTO;
    }
}
