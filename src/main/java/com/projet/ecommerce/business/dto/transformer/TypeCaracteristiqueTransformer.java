package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
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

}
