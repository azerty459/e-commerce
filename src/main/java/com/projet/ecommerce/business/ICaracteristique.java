package com.projet.ecommerce.business;

import java.util.List;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

public interface ICaracteristique {

	public List<CaracteristiqueDTO> getAllCaracteristique();
	
	public CaracteristiqueDTO getCaracteristique(CaracteristiqueDTO caracteristiqueDTO);
	
	public CaracteristiqueDTO createCaracteristique(CaracteristiqueDTO caracteristiqueDTO);
	
	public void deleteCaracteristique(CaracteristiqueDTO caracteristiqueDTO);
	
	public CaracteristiqueDTO updateCaracteristique(CaracteristiqueDTO caracteristiqueDTO);
	
}
