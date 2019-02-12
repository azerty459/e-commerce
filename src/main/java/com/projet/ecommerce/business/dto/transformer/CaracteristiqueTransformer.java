package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;

public class CaracteristiqueTransformer {
	
	private CaracteristiqueTransformer() {		
	}
	
	public static Collection<Caracteristique> listDtoToEntity(Collection<CaracteristiqueDTO> caracteristiquesDTO) {
        Collection<Caracteristique> caracteristiques = new ArrayList<>();
        if(caracteristiquesDTO != null) {
            for (CaracteristiqueDTO dtoCaracteristique : caracteristiquesDTO) {
            	caracteristiques.add(dtoToEntity(dtoCaracteristique));
            }
        }
        return caracteristiques;
    }

    
    public static Caracteristique dtoToEntity(CaracteristiqueDTO dtoCaracteristique) {
    	Caracteristique caracteristique = new Caracteristique();
    	caracteristique.setId(dtoCaracteristique.getId());
    	caracteristique.setValeur(dtoCaracteristique.getValeur());
    	caracteristique.setTypeCaracteristique(dtoCaracteristique.getType());
        Produit p = new Produit();
        p.setReferenceProduit(dtoCaracteristique.getRefProduit());
        caracteristique.setProduit(p);
        return caracteristique;
    }

   
    public static Collection<CaracteristiqueDTO> listEntityToDto(Collection<Caracteristique> caracteristiques) {
        Collection<CaracteristiqueDTO> caracteristiquesDTO = new ArrayList<>();
        if(caracteristiques != null) {
            for (Caracteristique caracteristique : caracteristiques) {
            	caracteristiquesDTO.add(entityToDto(caracteristique));
            }
        }
        return caracteristiquesDTO;
    }

    
    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
    	CaracteristiqueDTO dtoCaracteristique = new CaracteristiqueDTO();
    	dtoCaracteristique.setId(caracteristique.getId());
    	dtoCaracteristique.setValeur(caracteristique.getValeur());
    	dtoCaracteristique.setType(caracteristique.getTypeCaracteristique());
        if(caracteristique.getProduit() != null){
        	dtoCaracteristique.setRefProduit(caracteristique.getProduit().getReferenceProduit());
        }
        return dtoCaracteristique;
    }

}
