package com.projet.ecommerce.business;

import java.util.List;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;


public interface ITypeCaracteristiqueBusiness {

	
	List<TypeCaracteristiqueDTO> getAll();
	
	TypeCaracteristiqueDTO create(TypeCaracteristiqueDTO typeCDto);
	
	TypeCaracteristiqueDTO update(TypeCaracteristiqueDTO typeCDto);
	
	void delete(TypeCaracteristiqueDTO typeCDto);
	
}
