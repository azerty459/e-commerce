package com.projet.ecommerce.business;

import java.util.List;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;

public interface ICaracteristiqueBusiness {
	
	List<CaracteristiqueDTO> getAll(String referenceProduit);
	
	CaracteristiqueDTO add(CaracteristiqueDTO caracteristiqueDTO);
	
	CaracteristiqueDTO updateCaracteristique(int idCaracteristique);
	
	boolean delete(int id);	
}
