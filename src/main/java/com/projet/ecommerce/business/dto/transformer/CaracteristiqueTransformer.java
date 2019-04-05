package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CaracteristiqueTransformer {

    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO) {
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setTypeCaracteristique(TypeCaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO.getTypeCaracteristique()));
        caracteristique.setIdCaracteristique(caracteristiqueDTO.getIdCaracteristique());
        caracteristique.setValeur(caracteristiqueDTO.getValeur());
        caracteristique.setProduit(ProduitTransformer.dtoToEntity(caracteristiqueDTO.getProduit()));
        return caracteristique;
    }

    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
        CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
        caracteristiqueDTO.setIdCaracteristique(caracteristique.getIdCaracteristique());
        caracteristiqueDTO.setValeur(caracteristique.getValeur());
        caracteristiqueDTO.setTypeCaracteristique(TypeCaracteristiqueTransformer.entityToDto(caracteristique.getTypeCaracteristique()));
        caracteristiqueDTO.setProduit(ProduitTransformer.entityToDto(caracteristique.getProduit()));
        return caracteristiqueDTO;
    }

    public static List<Caracteristique> dtoToEntity(Collection<CaracteristiqueDTO> caracteristiqueDTOCollection){
        List<Caracteristique> caracteristiqueList = new ArrayList<>();
        if (caracteristiqueDTOCollection != null) {
            for (CaracteristiqueDTO caracteristiqueDTO : caracteristiqueDTOCollection) {
                caracteristiqueList.add(dtoToEntity(caracteristiqueDTO));
            }
        }
        return caracteristiqueList;
    }

    public static List<CaracteristiqueDTO> entityToDto(Collection<Caracteristique> caracteristiqueCollection){
        List<CaracteristiqueDTO> caracteristiqueDTOList = new ArrayList<>();
        if (caracteristiqueCollection != null) {
            for (Caracteristique caracteristique : caracteristiqueCollection) {
                caracteristiqueDTOList.add(entityToDto(caracteristique));
            }
        }
        return caracteristiqueDTOList;
    }

}
