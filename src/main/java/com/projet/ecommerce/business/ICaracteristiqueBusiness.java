package com.projet.ecommerce.business;

import java.util.List;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;

public interface ICaracteristiqueBusiness {

	public List<CaracteristiqueDTO> getAllCaracteristique();
	
	public CaracteristiqueDTO getCaracteristique(CaracteristiqueDTO caracteristiqueDTO);
	
	public CaracteristiqueDTO createCaracteristique(CaracteristiqueDTO caracteristiqueDTO);
	
	public boolean deleteCaracteristique(int id);
	
	public CaracteristiqueDTO updateCaracteristique(CaracteristiqueDTO caracteristiqueDTO);
	
}
