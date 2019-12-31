package com.projet.ecommerce.business.dto.transformer;


import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CaracteristiqueTransformer {


    /**
     * Transforme une collection de caracteristique CaracteristiqueDTO en une collection d'objets Caracteristique.
     *
     * @param caracteristiqueDTOCollection Une collection d'objets CaracteristiqueDTO
     * @return une collection d'objets Caracteristique
     */
    public static Collection<Caracteristique> dtoToEntity(List<CaracteristiqueDTO> caracteristiqueDTOCollection) {

        List<Caracteristique> caracteristiqueList = new ArrayList<>();
        for (CaracteristiqueDTO caracteristiqueDTO : caracteristiqueDTOCollection) {
            caracteristiqueList.add(dtoToEntity(caracteristiqueDTO));
        }
        return caracteristiqueList;

    }

    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO) {

        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setCaracteristiqueID(caracteristiqueDTO.getCaracteristiqueID());
        caracteristique.setValeur(caracteristiqueDTO.getValeur());
        return caracteristique;
    }


    public static Collection<CaracteristiqueDTO> entityToDto(List<Caracteristique> caracteristiqueList) {
        List<CaracteristiqueDTO> caracteristiqueDTOList = new ArrayList<>();
        for (Caracteristique caracteristique : caracteristiqueList) {
            System.out.println("Carcateristique :"+caracteristique);
            caracteristiqueDTOList.add(entityToDto(caracteristique));
        }

        return caracteristiqueDTOList;
    }

    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
        CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
        caracteristiqueDTO.setCaracteristiqueID(caracteristique.getCaracteristiqueID());
        caracteristiqueDTO.setValeur(caracteristique.getValeur());
        return caracteristiqueDTO;
    }


}
