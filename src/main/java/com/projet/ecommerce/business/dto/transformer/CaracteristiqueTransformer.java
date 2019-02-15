package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;

import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;

/**
 * Classe utilitaire permettant de transformer un objet DTO Caracteristique en objet entité Caracteristique (et vice et versa)
 *
 */
public class CaracteristiqueTransformer {
	
	private CaracteristiqueTransformer() {		
	}
	
	/**
	 * Transforme une collection d'objets {@link CaracteristiqueDTO} en une collection d'objets {@link Caracteristique}.
	 * 
	 * @param caracteristiquesDTO  Une collection d'objets CaracteristiqueDTO
	 * @return une collection d'objets Caracteristique
	 */
	public static Collection<Caracteristique> listDtoToEntity(Collection<CaracteristiqueDTO> caracteristiquesDTO) {
        Collection<Caracteristique> caracteristiques = new ArrayList<>();
        if(caracteristiquesDTO != null) {
            for (CaracteristiqueDTO dtoCaracteristique : caracteristiquesDTO) {
            	caracteristiques.add(dtoToEntity(dtoCaracteristique));
            }
        }
        return caracteristiques;
    }

    
    /**
     * Transforme un objet {@link CaracteristiqueDTO} en un objet {@link Caracteristique}.
     * 
     * @param dtoCaracteristique un objet CaracteristiqueDTO
     * @return un objet Caracteristique
     */
    public static Caracteristique dtoToEntity(CaracteristiqueDTO dtoCaracteristique) {
    	Caracteristique caracteristique = new Caracteristique();
    	caracteristique.setId(dtoCaracteristique.getId());
    	caracteristique.setValeur(dtoCaracteristique.getValeur());
    	caracteristique.setTypeCaracteristique(dtoCaracteristique.getTypeCaracteristique());
        Produit p = new Produit();
        p.setReferenceProduit(dtoCaracteristique.getRefProduit());
        caracteristique.setProduit(p);
        return caracteristique;
    }

   
    /**
     * Transforme une collection d'objets {@link Caracteristique} en une collection d'objets {@link CaracteristiqueDTO}.
     * 
     * @param caracteristiques Une collection d'objets Caracteristique
     * @return Une collection d'objets CaracteristiqueDTO
     */
    public static Collection<CaracteristiqueDTO> listEntityToDto(Collection<Caracteristique> caracteristiques) {
        Collection<CaracteristiqueDTO> caracteristiquesDTO = new ArrayList<>();
        if(caracteristiques != null) {
            for (Caracteristique caracteristique : caracteristiques) {
            	caracteristiquesDTO.add(entityToDto(caracteristique));
            }
        }
        return caracteristiquesDTO;
    }

    
    /**
     * Transforme un objet {@link Caracteristique} en un objet {@link CaracteristiqueDTO}.
     * 
     * @param caracteristique  un objet Caracteristique
     * @return  un objet CaracteristiqueDTO
     */
    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
    	CaracteristiqueDTO dtoCaracteristique = new CaracteristiqueDTO();
    	dtoCaracteristique.setId(caracteristique.getId());
    	dtoCaracteristique.setValeur(caracteristique.getValeur());
    	dtoCaracteristique.setTypeCaracteristique(caracteristique.getTypeCaracteristique());
        if(caracteristique.getProduit() != null){
        	dtoCaracteristique.setRefProduit(caracteristique.getProduit().getReferenceProduit());
        }
        return dtoCaracteristique;
    }

}
