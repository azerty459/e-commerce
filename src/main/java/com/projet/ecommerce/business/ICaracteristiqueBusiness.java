package com.projet.ecommerce.business;

import java.util.Collection;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;

public interface ICaracteristiqueBusiness {
	
	Collection<CaracteristiqueDTO> getAll(String ref);
	
	CaracteristiqueDTO update(CaracteristiqueDTO caracteristique);
	
	boolean delete(Integer idCaracteristique);
	
	CaracteristiqueDTO add(CaracteristiqueDTO caracteristique);

}
