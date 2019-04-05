package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

public class TypeCaracteristiqueTransformer {
	
	public TypeCaracteristiqueTransformer() {
		super();
	}

	public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {
		TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
		typeCaracteristique.setIdTypeCaracteristique(typeCaracteristiqueDTO.getIdTypeCaracteristique());
		typeCaracteristique.setTypeCaracteristique(typeCaracteristiqueDTO.getTypeCaracteristique());
		return typeCaracteristique;
	}
	
	public static Collection<TypeCaracteristique> dtoToEntity(Collection<TypeCaracteristiqueDTO> typeCaracteristiqueDTOCollection){
		List<TypeCaracteristique> listeTypeCaracteristique = new ArrayList<TypeCaracteristique>();
		for(TypeCaracteristiqueDTO typeCaracteristiqueDTO : typeCaracteristiqueDTOCollection) {
			listeTypeCaracteristique.add(dtoToEntity(typeCaracteristiqueDTO));
		}
		
		return listeTypeCaracteristique;
	}
	
	public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typeCaracteristique) {
		TypeCaracteristiqueDTO typeCaracteristiqueDTO = new TypeCaracteristiqueDTO();
		typeCaracteristiqueDTO.setIdTypeCaracteristique(typeCaracteristique.getIdTypeCaracteristique());
		typeCaracteristiqueDTO.setTypeCaracteristique(typeCaracteristique.getTypeCaracteristique());
		return typeCaracteristiqueDTO;
	}
	
	public static Collection<TypeCaracteristiqueDTO> entityToDto(Collection<TypeCaracteristique> typeCaracteristiqueCollection){
		List<TypeCaracteristiqueDTO> listeTypeCaracteristiqueDTO = new ArrayList<TypeCaracteristiqueDTO>();
		for(TypeCaracteristique typeCaracteristique : typeCaracteristiqueCollection) {
			listeTypeCaracteristiqueDTO.add(entityToDto(typeCaracteristique));
		}
		
		return listeTypeCaracteristiqueDTO;
	}
}
