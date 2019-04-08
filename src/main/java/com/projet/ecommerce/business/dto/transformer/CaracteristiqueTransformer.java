package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import java.util.ArrayList;
import java.util.Collection;

public class CaracteristiqueTransformer {
    
    public static CaracteristiqueDTO entityToDto(Caracteristique carac) {
        if(carac == null) {
            return null;
        }
        return new CaracteristiqueDTO(carac.getIdCaracteristique(), carac.getLibelle());
    }
    
    public static Collection<CaracteristiqueDTO> entityToDto(Iterable<Caracteristique> caracIte) {
        if(caracIte == null) {
            return null;
        }
        Collection<CaracteristiqueDTO> caracdto = new ArrayList<>();
        for(Caracteristique carac : caracIte) {
            CaracteristiqueDTO qsd = entityToDto(carac);
            caracdto.add(qsd);
        }
        return caracdto;
    }
    
    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracdto) {
        if(caracdto == null) {
            return null;
        }
        Caracteristique c = new Caracteristique();
        c.setIdCaracteristique(caracdto.getId());
        c.setLibelle(caracdto.getLibelle());
        return c;
    }
    
    public static Collection<Caracteristique> dtoToEntity(Iterable<CaracteristiqueDTO> caracdtoIte) {
        if(caracdtoIte == null) {
            return null;
        }
        Collection<Caracteristique> carac = new ArrayList<>();
        for(CaracteristiqueDTO caracdto : caracdtoIte) {
            carac.add(dtoToEntity(caracdto));
        }
        return carac;
    }
    
}
