package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDto;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import java.util.ArrayList;
import java.util.Collection;

public class CaracteristiqueTransformer {
    
    public static CaracteristiqueDto entityToDto(Caracteristique carac) {
        if(carac == null) {
            return null;
        }
        return new CaracteristiqueDto(carac.getIdCaracteristique(), carac.getLibelle());
    }
    
    public static Collection<CaracteristiqueDto> entityToDto(Iterable<Caracteristique> carac) {
        if(carac == null) {
            return null;
        }
        Collection<CaracteristiqueDto> result = new ArrayList<>();
        for(Caracteristique c : carac) {
            result.add(entityToDto(c));
        }
        return result;
    }
    
    public static Caracteristique dtoToEntity(CaracteristiqueDto carac) {
        if(carac == null) {
            return null;
        }
        Caracteristique c = new Caracteristique();
        c.setIdCaracteristique(carac.getId());
        c.setLibelle(carac.getLibelle());
        return c;
    }
    
    public static Collection<Caracteristique> dtoToEntity(Iterable<CaracteristiqueDto> carac) {
        if(carac == null) {
            return null;
        }
        Collection<Caracteristique> result = new ArrayList<>();
        for(CaracteristiqueDto c : carac) {
            result.add(dtoToEntity(c));
        }
        return result;
    }
    
}
