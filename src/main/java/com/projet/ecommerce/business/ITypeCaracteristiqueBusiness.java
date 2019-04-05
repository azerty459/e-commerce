package com.projet.ecommerce.business;

import java.util.List;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;

public interface ITypeCaracteristiqueBusiness {
	
	List<TypeCaracteristiqueDTO> getAll();

	TypeCaracteristiqueDTO createTypeCaracteristique(TypeCaracteristiqueDTO typeCaracacteristiqueDTO);
	
	TypeCaracteristiqueDTO update(TypeCaracteristiqueDTO typeDto);
}
