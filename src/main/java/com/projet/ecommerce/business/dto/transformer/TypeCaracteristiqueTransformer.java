/**
 * 
 */
package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

/**
 * @author liam
 *
 */
public class TypeCaracteristiqueTransformer {
	
	private TypeCaracteristiqueTransformer() {
		
	}
	
	public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typecaracteristique) {
		if(typecaracteristique == null) {
			return null;
		}
		
		TypeCaracteristiqueDTO tcdto = new TypeCaracteristiqueDTO();
		tcdto.setIdTypeCaracteristique(typecaracteristique.getIdTypeCaracteristique());
		tcdto.setNomCaracteristique(typecaracteristique.getNomCaracteristique());
		
		return tcdto;
	}
	
	
	public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typecaracteristiqueDTO) {
		if(typecaracteristiqueDTO == null) {
			return null;
		}
		
		TypeCaracteristique tc = new TypeCaracteristique();
		
		tc.setIdTypeCaracteristique(typecaracteristiqueDTO.getIdTypeCaracteristique());
		tc.setNomCaracteristique(typecaracteristiqueDTO.getNomCaracteristique());
		
		return tc;
	}
}
