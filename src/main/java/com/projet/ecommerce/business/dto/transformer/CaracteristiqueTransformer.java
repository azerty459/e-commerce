package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

public class CaracteristiqueTransformer {
	
	public static Collection<CaracteristiqueDTO> entityToDto (final Collection<Caracteristique> caracteristiqueLst) {
		
		if (caracteristiqueLst == null) {
			return null;
		}
		List<CaracteristiqueDTO> caracteristiqueDTOCollection = new ArrayList<>();
		for (Caracteristique caracteristique : caracteristiqueLst) {
			caracteristiqueDTOCollection.add(entityToDto(caracteristique));
		}
		
		return caracteristiqueDTOCollection;
		
	}
	
	public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
        if (caracteristique == null) {
            return null;
        }
        CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
        caracteristiqueDTO.setTypeCaracteristique(caracteristique.getTypeCaracteristique());
        caracteristiqueDTO.setValeur(caracteristique.getValeur());
        return caracteristiqueDTO;
    }
	
	
    public static Collection<Caracteristique> dtoToEntity(Collection<CaracteristiqueDTO> caracteristiqueDTOCollection) {
        if (caracteristiqueDTOCollection == null) {
            return null;
        }
        List<Caracteristique> caracteristiques = new ArrayList<>();
        for (CaracteristiqueDTO caracteristiqueDTO : caracteristiqueDTOCollection) {
            caracteristiques.add(dtoToEntity(caracteristiqueDTO));
        }
        return caracteristiques;
    }


    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO) {
        if (caracteristiqueDTO == null) {
            return null;
        }
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setTypeCaracteristique(caracteristiqueDTO.getTypeCaracteristique());
        caracteristique.setValeur(caracteristiqueDTO.getValeur());
        return caracteristique;
    }


}
