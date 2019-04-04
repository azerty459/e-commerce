package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.List;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

public class CaracteristiqueTransformer {
	
	private CaracteristiqueTransformer() {}
	
	public static List<CaracteristiqueDTO> entitiesToDto(Iterable<Caracteristique> iterable){
		List<CaracteristiqueDTO> caracsDto = new ArrayList<CaracteristiqueDTO>();
		
		for(Caracteristique carac : iterable) {
			caracsDto.add(entityToDto(carac));
		}
		
		return caracsDto;
	}
	public static CaracteristiqueDTO entityToDto(Caracteristique carac) {
		CaracteristiqueDTO caracteristiqueDto = new CaracteristiqueDTO();
		caracteristiqueDto.setIdCaracteristique(carac.getIdCaracteristique());
		caracteristiqueDto.setProduit(ProduitTransformer.entityToDto(carac.getProduit()));
		caracteristiqueDto.setTypeCaracteristique(TypeCaracteristiqueTransformer.entityToDto(carac.getTypeCaracteristique()));
		caracteristiqueDto.setValue(carac.getValue());
		return caracteristiqueDto;
	}
	
	public static Caracteristique dtoToEntity(CaracteristiqueDTO caracDto) {
		Caracteristique carac = new Caracteristique();
		
		carac.setIdCaracteristique(caracDto.getIdCaracteristique());
		carac.setProduit(ProduitTransformer.dtoToEntity(caracDto.getProduit()));
		carac.setTypeCaracteristique(TypeCaracteristiqueTransformer.dtoToEntity(caracDto.getTypeCaracteristique()));
		carac.setValue(caracDto.getValue());
		
		return carac;
	}
	public static List<Caracteristique> dtoToEntities(List<CaracteristiqueDTO> caracteristiques) {
		 List<Caracteristique> caracs = new ArrayList<Caracteristique>();
		
		for(CaracteristiqueDTO caracDto : caracteristiques) {
			caracs.add(dtoToEntity(caracDto));
		}
		
		return caracs;
	}
}
