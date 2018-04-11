package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

import java.util.ArrayList;
import java.util.List;

public class CaracteristiqueTransformer {

    public static List<Caracteristique> dtoToEntity(List<CaracteristiqueDTO> caracteristiqueDTOCollection){
        if(caracteristiqueDTOCollection == null) return null;
        List<Caracteristique> caracteristiques = new ArrayList<>();
        for (CaracteristiqueDTO caracteristiqueDTO : caracteristiqueDTOCollection) {
            caracteristiques.add(dtoToEntity(caracteristiqueDTO));
        }
        return caracteristiques;
    }

    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO){
        if (caracteristiqueDTO == null) return null;
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setTypeCaracteristique(caracteristiqueDTO.getTypeCaracteristique());
        caracteristique.setValeur(caracteristiqueDTO.getValeur());
        return caracteristique;
    }


    public static List<CaracteristiqueDTO> entityToDto(List<Caracteristique> caracteristiques){
        if (caracteristiques == null) return null;
        List<CaracteristiqueDTO> caracteristiqueDTOCollection = new ArrayList<>();
        for (Caracteristique caracteristique : caracteristiques) {
            caracteristiqueDTOCollection.add(entityToDto(caracteristique));
        }
        return caracteristiqueDTOCollection;
    }

    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique){
        if (caracteristique == null) return null;
        CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
        caracteristiqueDTO.setTypeCaracteristique(caracteristique.getTypeCaracteristique());
        caracteristiqueDTO.setValeur(caracteristique.getValeur());
        return caracteristiqueDTO;
    }
}
