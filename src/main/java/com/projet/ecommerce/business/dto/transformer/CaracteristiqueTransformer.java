package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.List;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.CaracteristiquePK;
import com.projet.ecommerce.persistance.entity.Produit;

public class CaracteristiqueTransformer {
	
	/**
     * Transforme un objet CaracteristiqueDTO en un objet Caracteristique.
     *
     * @param caracteristiqueDTO Un objet CaracteristiqueDTO
     * @return un objet Caracteristique
     */
    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO, Produit produit) {
    	//TODO gestion null + param produit
    	if(caracteristiqueDTO == null || produit == null ) {
    		return null;
    	}
       Caracteristique caracteristique = new Caracteristique();
       caracteristique.setCaracteristiquePk(new CaracteristiquePK());
       caracteristique.getCaracteristiquePk()
       		.setTypeCaracteristique(TypeCaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO.getTypeCaracteristiqueDto()));
       caracteristique.getCaracteristiquePk()
  			.setProduit(produit);
       caracteristique.setValeurCaracteristique(caracteristiqueDTO.getValeurCaracteristique());
       return caracteristique;
    }
    
    public static List<CaracteristiqueDTO> entityToDto(List<Caracteristique> caracteristiques, ProduitDTO produitDto) {
        if (caracteristiques == null) {
            return null;
        }
        List<CaracteristiqueDTO> caracteristiqueDTOCollection = new ArrayList<>();
        for (Caracteristique caracteristique : caracteristiques) {
        	caracteristiqueDTOCollection.add(entityToDto(caracteristique, produitDto));
        }
        return caracteristiqueDTOCollection;
    }
    
    /**
     * Transforme un objet CaracteristiqueDTO en un objet Caracteristique.
     *
     * @param caracteristiqueDTO Un objet CaracteristiqueDTO
     * @return un objet Caracteristique
     */
    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique, ProduitDTO produitDto) {
    	//TODO Gestion null
       CaracteristiqueDTO caracteristiqueDto = new CaracteristiqueDTO();
       caracteristiqueDto.setTypeCaracteristiqueDto(
    		   TypeCaracteristiqueTransformer.entityToDto(caracteristique.getCaracteristiquePk().getTypeCaracteristique())
       );
       caracteristiqueDto.setProduitDto(produitDto);
       caracteristiqueDto.setValeurCaracteristique(caracteristique.getValeurCaracteristique());
       return caracteristiqueDto;
    }
    
    public static List<Caracteristique> dtoToEntity(List<CaracteristiqueDTO> caracteristiquesDto, Produit produit) {
        if (caracteristiquesDto == null) {
            return null;
        }
        List<Caracteristique> caracteristiqueCollection = new ArrayList<>();
        for (CaracteristiqueDTO caracteristiqueDto : caracteristiquesDto) {
        	caracteristiqueCollection.add(dtoToEntity(caracteristiqueDto, produit));
        }
        return caracteristiqueCollection;
    }

}
