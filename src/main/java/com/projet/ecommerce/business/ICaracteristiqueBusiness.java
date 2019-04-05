package com.projet.ecommerce.business;

import java.util.List;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;

public interface ICaracteristiqueBusiness {
	
	List<CaracteristiqueDTO> getAll(String referenceProduit);
		
	CaracteristiqueDTO updateCaracteristique(int idCaracteristique, String nouvelleValeurCaracteristique);
}
