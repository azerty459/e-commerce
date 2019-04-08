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
	public static Collection<Caracteristique> listeDtoToEntity(Collection<CaracteristiqueDTO> caracteristiqueDTOCollection){
		List<Caracteristique> listeCaracteristique = new ArrayList<Caracteristique>();
		for(CaracteristiqueDTO caracteristiqueDTO : caracteristiqueDTOCollection) {
			listeCaracteristique.add(dtoToEntity(caracteristiqueDTO));
		}
		
		return listeCaracteristique;
	}

	public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO) {
		Caracteristique caracteristique = new Caracteristique();
		caracteristique.setIdCaracteristique(caracteristiqueDTO.getIdCaracteristique());
		caracteristique.setValeurCaracteristique(caracteristiqueDTO.getValeur());
		caracteristique.setProduit(caracteristiqueDTO.getProduit());
		caracteristique.setTypeCaracteristique(caracteristiqueDTO.getTypeCaracteristique());
		return caracteristique;
	}
	
	
	public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
		CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
		caracteristiqueDTO.setIdCaracteristique(caracteristique.getIdCaracteristique());
		caracteristiqueDTO.setValeur(caracteristique.getValeurCaracteristique());
		caracteristiqueDTO.setProduit(caracteristique.getProduit());
		caracteristiqueDTO.setTypeCaracteristique(caracteristique.getTypeCaracteristique());
		return caracteristiqueDTO;
	}
	
	public static Collection<CaracteristiqueDTO> listeEntityToDto(Collection<Caracteristique> caracteristiqueCollection){
		List<CaracteristiqueDTO> listeCaracteristiqueDTO = new ArrayList<CaracteristiqueDTO>();
		for(Caracteristique caracteristique : caracteristiqueCollection) {
			listeCaracteristiqueDTO.add(entityToDto(caracteristique));
		}
		
		return listeCaracteristiqueDTO;
	}
	
}
