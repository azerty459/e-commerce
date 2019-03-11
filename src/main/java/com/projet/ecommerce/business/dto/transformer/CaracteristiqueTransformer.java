package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.PhotoDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CaracteristiqueTransformer {

    private CaracteristiqueTransformer() {
    }

    public static Collection<Caracteristique> dtoToEntity(Collection<CaracteristiqueDTO> caracteristiqueDTOCollection) {
        List<Caracteristique> caracteristiqueList = new ArrayList<>();
        for (CaracteristiqueDTO caracteristiqueDTO : caracteristiqueDTOCollection) {
            caracteristiqueList.add(dtoToEntity(caracteristiqueDTO));
        }
        return caracteristiqueList;
    }

    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO) {
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setIdCaracteristique(caracteristiqueDTO.getId());
        caracteristique.setTypeCaracteristique(TypeCaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO.getTypeCaracteristiqueDTO()));
        caracteristique.setValeur(caracteristiqueDTO.getValeur());
        return caracteristique;
    }

    public static Collection<CaracteristiqueDTO> entityToDto(Collection<Caracteristique> caracteristiqueCollection) {
        List<CaracteristiqueDTO> caracteristiqueDTOList = new ArrayList<>();
        for (Caracteristique caracteristique : caracteristiqueCollection) {
            if (caracteristique.getIdCaracteristique() != 0) {
            	caracteristiqueDTOList.add(entityToDto(caracteristique));
            }
        }
        return caracteristiqueDTOList;
    }
    
    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
    	CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
    	caracteristiqueDTO.setId(caracteristique.getIdCaracteristique());
    	caracteristiqueDTO.setTypeCaracteristiqueDTO(TypeCaracteristiqueTransformer.entityToDto(caracteristique.getTypeCaracteristique()));
    	caracteristiqueDTO.setValeur(caracteristique.getValeur());
        return caracteristiqueDTO;
    }
}
