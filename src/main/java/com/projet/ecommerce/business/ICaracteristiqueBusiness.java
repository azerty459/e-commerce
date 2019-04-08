package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import java.util.Collection;


public interface ICaracteristiqueBusiness {
    
    CaracteristiqueDTO add(CaracteristiqueDTO carac);
    
    CaracteristiqueDTO update(CaracteristiqueDTO carac);
    
    boolean delete(int id);
    
    Collection<CaracteristiqueDTO> getAll();
    
}
