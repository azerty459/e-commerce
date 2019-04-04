package com.projet.ecommerce.business;

import java.util.List;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;

public interface ITypeCaracteristiqueBusiness {
	public List<TypeCaracteristiqueDTO> getAll();
	
	public TypeCaracteristiqueDTO create(TypeCaracteristiqueDTO typeCaracDto);
	
	public void delete(TypeCaracteristiqueDTO typeDto);
	
	public TypeCaracteristiqueDTO update(TypeCaracteristiqueDTO typeDto);
}
