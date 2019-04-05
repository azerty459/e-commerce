package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

public class TypeCaracteristiqueTransformer {
	
	/**
     * Transforme un objet TypeCaracteristiqueDTO en un objet TypeCaracteristique.
     *
     * @param typeCaracteristiqueDto Un objet typeCaracteristiqueDTO
     * @return un objet typeCaracteristique
     */
    public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typeCaracteristiqueDto) {
        TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
		typeCaracteristique.setIdTypeCaracteristique(typeCaracteristiqueDto.getId());
		typeCaracteristique.setNomTypeCaracteristique(typeCaracteristiqueDto.getNomType());
        return typeCaracteristique;
        
    }
    
    public static Collection<TypeCaracteristiqueDTO> entityToDto(Collection<TypeCaracteristique> TypeCaracteristiques) {
        if (TypeCaracteristiques == null) {
            return null;
        }
        List<TypeCaracteristiqueDTO> typeCaracteristiqueDTOCollection = new ArrayList<>();
        for (TypeCaracteristique typeCaracteristique : TypeCaracteristiques) {
        	typeCaracteristiqueDTOCollection.add(entityToDto(typeCaracteristique));
        }
        return typeCaracteristiqueDTOCollection;
    }
    
    /**
     * Transforme un objet TypeCaracteristique en un objet TypeCaracteristiqueDTO.
     *
     * @param typeCaracteristique Un objet typeCaracteristique
     * @return un objet typeCaracteristiqueDTO
     */
    public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typeCaracteristique) {
        TypeCaracteristiqueDTO typeCaracteristiqueDto = new TypeCaracteristiqueDTO();
		typeCaracteristiqueDto.setId(typeCaracteristique.getIdTypeCaracteristique());
		typeCaracteristiqueDto.setNomType(typeCaracteristique.getNomTypeCaracteristique());
        return typeCaracteristiqueDto;
        
    }
    
    public static Collection<TypeCaracteristique> dtoToEntity(Collection<TypeCaracteristiqueDTO> typeCaracteristiquesDto) {
        if (typeCaracteristiquesDto == null) {
            return null;
        }
        List<TypeCaracteristique> typeCaracteristiqueCollection = new ArrayList<>();
        for (TypeCaracteristiqueDTO typeCaracteristiqueDto : typeCaracteristiquesDto) {
        	typeCaracteristiqueCollection.add(dtoToEntity(typeCaracteristiqueDto));
        }
        return typeCaracteristiqueCollection;
    }

}
