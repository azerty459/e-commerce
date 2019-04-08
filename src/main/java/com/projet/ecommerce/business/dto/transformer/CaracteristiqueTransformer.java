package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Caracteristique.CaracPK;

public class CaracteristiqueTransformer {

	private CaracteristiqueTransformer() {}
	
	public static Caracteristique dtoToEntity(CaracteristiqueDTO caracDto) {
		Caracteristique c = new Caracteristique();
		CaracPK cpk = new CaracPK();
		cpk.setProduit(ProduitTransformer.dtoToEntity(caracDto.getProd()));
		cpk.setTypeC(TypeCaracteristiqueTransformer.dtoToEntity(caracDto.getTypeC()));
		c.setValeur(caracDto.getValeur());
		c.setCaracPK(cpk);
		
		return c;
	}
	
	public static CaracteristiqueDTO entityToDto(Caracteristique carac) {
		CaracteristiqueDTO cDTO = new CaracteristiqueDTO();
		cDTO.setTypeC(TypeCaracteristiqueTransformer.entityToDto(carac.getCaracPK().getTypeC()));
		cDTO.setProd(ProduitTransformer.entityToDto(carac.getCaracPK().getProduit()));
		cDTO.setValeur(carac.getValeur());
		
		return cDTO;
	}
	
	public static List<Caracteristique> listDtoToEntity(List<CaracteristiqueDTO> listDto) {
		List<Caracteristique> list = new ArrayList<>();

		if(listDto != null)
			for (CaracteristiqueDTO typeCaracteristiqueDTO : listDto)
				list.add(dtoToEntity(typeCaracteristiqueDTO));

		
		return list;
	}
	
	public static List<CaracteristiqueDTO> listEntityToDto(List<Caracteristique> list) {
		List<CaracteristiqueDTO> listDto = new ArrayList<>();

		if(list != null)
			for (Caracteristique typeCaracteristique : list)
				listDto.add(entityToDto(typeCaracteristique));
		
		return listDto;
	}
	
}
