package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

public class TypeCaracteristiqueTransformer {
	
	private TypeCaracteristiqueTransformer() {}
	public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique type) {
		TypeCaracteristiqueDTO typeDto = new TypeCaracteristiqueDTO();
		
		typeDto.setIdTypeCaracteristique(type.getIdTypeCaracteristique());
		typeDto.setLibelle(type.getLibelle());
		
		return typeDto;
	}

	public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typeDto) {
		TypeCaracteristique type = new TypeCaracteristique();
		type.setIdTypeCaracteristique(typeDto.getIdTypeCaracteristique());
		type.setLibelle(typeDto.getLibelle());
		return type;
	}
	public static Collection<? extends TypeCaracteristiqueDTO> entitiesToDto(Iterable<TypeCaracteristique> typeCaracs) {
		List<TypeCaracteristiqueDTO> typeCaracsDto = new ArrayList<>();
		for(TypeCaracteristique type : typeCaracs) {
			typeCaracsDto.add(entityToDto(type));
		}
		return typeCaracsDto;
	}
}
