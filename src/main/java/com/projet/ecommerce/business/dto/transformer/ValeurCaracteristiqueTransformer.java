package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.business.dto.ValeurCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.ValeurCaracteristique;

public class ValeurCaracteristiqueTransformer {

	private ValeurCaracteristiqueTransformer() {};
	
	
	/**
	 * Transforme une collection d'objets ValeurCaracteristique en une collection d'objets ValeurCaracteristiqueDTO.
	 *
	 * @param Une collection d'objets ValeurCaracteristique
	 * @return une collection d'objet ValeurCaracteristiqueDTO
	 */
	public static Collection<ValeurCaracteristiqueDTO> entityToDto(Collection<ValeurCaracteristique> valeurCaracteristiques) {
		
		if(valeurCaracteristiques == null)
			return null;
		
		List<ValeurCaracteristiqueDTO> valeurCaracteristiqueDtoCollection = new ArrayList<>();
		
		for(ValeurCaracteristique valeurCaracteristique: valeurCaracteristiques) {
			
			valeurCaracteristiqueDtoCollection.add(entityToDto(valeurCaracteristique));
		}
		
		return valeurCaracteristiqueDtoCollection;
	}
	
	
	 /**
     * Transforme un objet ValeurCaracteristique en ValeurCaracteristiqueDTO 
     *
     * @param Un objet ValeurCaracteristique
     * @return un objet ValeurCaracteristiqueDTO
     */
	private static ValeurCaracteristiqueDTO entityToDto(ValeurCaracteristique valeurCaracteristique) {
		
		if(valeurCaracteristique == null)
			return null;
					
		ValeurCaracteristiqueDTO valeurCaracteristiqueDTO = new ValeurCaracteristiqueDTO();
		
		valeurCaracteristiqueDTO.setValeurCaracteristiquePK(valeurCaracteristique.getValeurCaracteristiquePK());
		valeurCaracteristiqueDTO.setValeur(valeurCaracteristique.getValeur());
		
		return valeurCaracteristiqueDTO;	
	}
	
	/**
	 * Transforme une collection d'objets ValeurCaracteristiqueDTO en une collection d'objets ValeurCaracteristique.
	 *
	 * @param Une collection d'objets ValeurCaracteristiqueDTO
	 * @return une collection d'objet ValeurCaracteristique
	 */
	public static Collection<ValeurCaracteristique> dtoToEntity(Collection<ValeurCaracteristiqueDTO> valeurCaracteristiqueDTOs){
		
		if(valeurCaracteristiqueDTOs == null)
			return null;
		
		List<ValeurCaracteristique> valeurCaracteristiques = new ArrayList<>();
		
		for(ValeurCaracteristiqueDTO valeurCaracteristiqueDTO: valeurCaracteristiqueDTOs) {
			
			valeurCaracteristiques.add(dtoToEntity(valeurCaracteristiqueDTO));
		}
		
		return valeurCaracteristiques;
	}

	/**
     * Transforme un objet ValeurCaracteristiqueDTO en ValeurCaracteristique 
     *
     * @param Un objet ValeurCaracteristiqueDTO
     * @return un objet ValeurCaracteristique
     */
	private static ValeurCaracteristique dtoToEntity(ValeurCaracteristiqueDTO valeurCaracteristiqueDTO) {
		
		if(valeurCaracteristiqueDTO == null)
			return null;
		
		ValeurCaracteristique valeurCaracteristique = new ValeurCaracteristique();
		
		valeurCaracteristique.setValeurCaracteristiquePK(valeurCaracteristiqueDTO.getValeurCaracteristiquePK());
		valeurCaracteristique.setValeur(valeurCaracteristiqueDTO.getValeur());
		
		return valeurCaracteristique;
	}
	
}
