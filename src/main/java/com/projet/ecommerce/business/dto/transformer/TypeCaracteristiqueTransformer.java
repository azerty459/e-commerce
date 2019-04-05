package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

public class TypeCaracteristiqueTransformer {

	private TypeCaracteristiqueTransformer() {}
	
	public static TypeCaracteristique dtoToEntity(TypeCaracteristiqueDTO typeCDto) {
		TypeCaracteristique tc = new TypeCaracteristique();
		tc.setIdTypeCarac(typeCDto.getIdTypeC());
		tc.setLibelle(typeCDto.getLibelle());
		
		return tc;
	}
	
	public static TypeCaracteristiqueDTO entityToDto(TypeCaracteristique typeC) {
		TypeCaracteristiqueDTO tcDto = new TypeCaracteristiqueDTO();
		tcDto.setIdTypeC(typeC.getIdTypeCarac());
		tcDto.setLibelle(typeC.getLibelle());
		
		return tcDto;
	}
	
	public static List<TypeCaracteristique> listDtoToEntity(Iterable<TypeCaracteristiqueDTO> listDto) {
		List<TypeCaracteristique> list = new ArrayList<TypeCaracteristique>();
		
		for (TypeCaracteristiqueDTO typeCaracteristiqueDTO : listDto) {
			list.add(dtoToEntity(typeCaracteristiqueDTO));
		}
		
		return list;
	}
	
	public static List<TypeCaracteristiqueDTO> listEntityToDto(Iterable<TypeCaracteristique> list) {
		List<TypeCaracteristiqueDTO> listDto = new ArrayList<TypeCaracteristiqueDTO>();
		
		for (TypeCaracteristique typeCaracteristique : list) {
			listDto.add(entityToDto(typeCaracteristique));
		}
		
		return listDto;
	}
}
