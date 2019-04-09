package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

public class CaracteristiqueTransformer {

	private CaracteristiqueTransformer() {}


	/**
	 * Transforme une collection d'objets Caracteristique en une collection d'objets CaracteristiqueDTO.
	 *
	 * @param Une collection d'objets Caracteristique
	 * @return une collection d'objet CaracteristiqueDTO
	 */
	public static Collection<CaracteristiqueDTO> entityToDto(Iterable<Caracteristique> caracteristiques){

		if(caracteristiques == null) {
			return null;
		}

		List<CaracteristiqueDTO> caracteristiqueDTOCollection = new ArrayList<>();

		for(Caracteristique caracteristique : caracteristiques) {

			caracteristiqueDTOCollection.add(entityToDto(caracteristique));
		}

		return caracteristiqueDTOCollection;
	}

	
	 /**
     * Transforme un objet Caracteristique en CaracteristiqueDTO 
     *
     * @param Un objet Caracteristique
     * @return un objet CaracteristiqueDTO
     */
	public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
		if(caracteristique == null)
			return null;

		CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
		caracteristiqueDTO.setIdCaracteristique(caracteristique.getIdCaracteristique());
		caracteristiqueDTO.setLibelle(caracteristique.getLibelle());

		return caracteristiqueDTO;
	}


	/**
	 * Transforme une collection d'objets CaracteristiqueDTO en une collection d'objets Caracteristique.
	 *
	 * @param Une collection d'objets CaracteristiqueDTO
	 * @return une collection d'objet Caracteristique
	 */
	public static Collection<Caracteristique> dtoToEntity(Collection<CaracteristiqueDTO> caracteristiquesDTO){

		if(caracteristiquesDTO == null) {
			return null;
		}

		List<Caracteristique> caracteristiqueCollection = new ArrayList<>();

		for(CaracteristiqueDTO caracteristiqueDTO : caracteristiquesDTO) {

			caracteristiqueCollection.add(dtoToEntity(caracteristiqueDTO));
		}

		return caracteristiqueCollection;
	}

	
	 /**
     * Transforme un objet CaracteristiqueDTO en Caracteristique
     *
     * @param caracteristiqueDTO Un objet Caracteristique
     * @return une objet Caracteristique
     */
	public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO) {
		if(caracteristiqueDTO == null)
			return null;

		Caracteristique caracteristique = new Caracteristique();
		caracteristique.setIdCaracteristique(caracteristiqueDTO.getIdCaracteristique());
		caracteristiqueDTO.setLibelle(caracteristiqueDTO.getLibelle());

		return caracteristique;
	}


}
