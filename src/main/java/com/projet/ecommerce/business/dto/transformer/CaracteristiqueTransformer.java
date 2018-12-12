package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.ArrayList;
import java.util.List;


/**
 * convertir une caracteristique à un dto caracteristique
 * @author nextoo
 */
public class CaracteristiqueTransformer {

    public static List<CaracteristiqueDTO> entitiesToDtos(List<Caracteristique> caracteristiques) {
        List<CaracteristiqueDTO> result = new ArrayList<CaracteristiqueDTO>();
        if (caracteristiques != null && !caracteristiques.isEmpty()) {
            caracteristiques.stream().forEach(c -> result.add(entityDTO(c)));
        }
        return result;
    }

    public static List<Caracteristique> dtosToEntities(List<CaracteristiqueDTO> dtos) {
        List<Caracteristique> result = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty() ){
            dtos.stream().forEach(d -> result.add(dtoToEntity(d)));
        }
        return result;
    }


    public static CaracteristiqueDTO entityDTO(Caracteristique c) {
        CaracteristiqueDTO dto = new CaracteristiqueDTO();
        dto.setType(c.getType() != null ? c.getType().getType() : null);
        dto.setValeur(c.getValeur());
        return dto;
    }

    public static Caracteristique dtoToEntity(CaracteristiqueDTO dto) {
        Caracteristique c = new Caracteristique();
        c.setValeur(dto.getValeur());
        TypeCaracteristique t = new TypeCaracteristique();
        t.setType(dto.getType());
        c.setType(t);
        return c;
    }
}
