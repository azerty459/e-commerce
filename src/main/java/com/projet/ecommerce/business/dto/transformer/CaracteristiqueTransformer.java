package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

public class CaracteristiqueTransformer {
	
	public CaracteristiqueTransformer() {
		super();
	}

	public static Collection<Caracteristique> dtoToEntity(Collection<CaracteristiqueDTO> caracteristiqueDTOCollection){
		List<Caracteristique> listeCaracteristique = new ArrayList<Caracteristique>();
		for(CaracteristiqueDTO caracteristiqueDTO : caracteristiqueDTOCollection) {
			listeCaracteristique.add(dtoToEntity(caracteristiqueDTO));
		}
		
		return listeCaracteristique;
	}

	private static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO) {
		Caracteristique caracteristique = new Caracteristique();
		caracteristique.setIdCaracteristique(caracteristiqueDTO.getIdCaracteristique());
		caracteristique.setValeurCaracteristique(caracteristiqueDTO.getValeur());
		return caracteristique;
	}
	
	
	private static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
		CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
		caracteristiqueDTO.setIdCaracteristique(caracteristique.getIdCaracteristique());
		caracteristiqueDTO.setValeur(caracteristique.getValeurCaracteristique());
		return caracteristiqueDTO;
	}
	
	public static Collection<CaracteristiqueDTO> entityToDTO(Collection<Caracteristique> caracteristiqueCollection){
		List<CaracteristiqueDTO> listeCaracteristiqueDTO = new ArrayList<CaracteristiqueDTO>();
		for(Caracteristique caracteristique : caracteristiqueCollection) {
			listeCaracteristiqueDTO.add(entityToDto(caracteristique));
		}
		
		return listeCaracteristiqueDTO;
	}
	
}
