package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CaracteristiqueDto;
import java.util.Collection;


public interface ICaracteristiqueBusiness {
    
    CaracteristiqueDto add(String label);
    
    CaracteristiqueDto update(CaracteristiqueDto carac);
    
    boolean delete(int id);
    
    Collection<CaracteristiqueDto> getAll();
    
}
