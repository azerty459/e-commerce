package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CaracteristiqueTransformer {

    /**
     * Transforme une collection d'objets CaracteristiqueDTO en une collection d'objets Caracteristique.
     * @param caracteristiqueDTOCollection Une collection d'objets Caracteristique
     * @return une collection d'objet Caracteristique
     */
    public static Collection<Caracteristique> dtoToEntity(Collection<CaracteristiqueDTO> caracteristiqueDTOCollection){
        if(caracteristiqueDTOCollection == null) return null;
        List<Caracteristique> caracteristiques = new ArrayList<>();
        for (CaracteristiqueDTO caracteristiqueDTO : caracteristiqueDTOCollection) {
            caracteristiques.add(dtoToEntity(caracteristiqueDTO));
        }
        return caracteristiques;
    }

    /**
     * Transforme un objet CaracteristiqueDTO en Caracteristique.
     * @param caracteristiqueDTO un objet CaracteristiqueDTO
     * @return un objet Caracteristique
     */
    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO){
        if (caracteristiqueDTO == null) return null;
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setTypeCaracteristique(caracteristiqueDTO.getTypeCaracteristique());
        caracteristique.setValeur(caracteristiqueDTO.getValeur());
        return caracteristique;
    }


    /**
     * Transforme une collection d'objets Caracteristique en une collection d'objets CaracteristiqueDTO.
     * @param caracteristiques Une collection d'objets Caracteristique
     * @return une collection d'objet CaracteristiqueDTO
     */
    public static Collection<CaracteristiqueDTO> entityToDto(Collection<Caracteristique> caracteristiques){
        if (caracteristiques == null) return null;
        List<CaracteristiqueDTO> caracteristiqueDTOCollection = new ArrayList<>();
        for (Caracteristique caracteristique : caracteristiques) {
            caracteristiqueDTOCollection.add(entityToDto(caracteristique));
        }
        return caracteristiqueDTOCollection;
    }

    /**
     * Transforme un objet Caracteristique en CaracteristiqueDTO.
     * @param caracteristique un objet Caracteristique
     * @return un objet CaracteristiqueDTO
     */
    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique){
        if (caracteristique == null) return null;
        CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
        caracteristiqueDTO.setTypeCaracteristique(caracteristique.getTypeCaracteristique());
        caracteristiqueDTO.setValeur(caracteristique.getValeur());
        return caracteristiqueDTO;
    }
}
