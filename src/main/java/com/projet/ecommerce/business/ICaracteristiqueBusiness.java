package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import java.util.Collection;


public interface ICaracteristiqueBusiness {
    
    CaracteristiqueDTO add(String label);
    
    CaracteristiqueDTO update(CaracteristiqueDTO carac);
    
    boolean delete(int id);
    
    Collection<CaracteristiqueDTO> getAll();
    
}
