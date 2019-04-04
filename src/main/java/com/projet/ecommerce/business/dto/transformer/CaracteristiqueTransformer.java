/**
 * 
 */
package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

/**
 * @author liam
 *
 */
public class CaracteristiqueTransformer {

	private CaracteristiqueTransformer() {
	}

	public static Collection<CaracteristiqueDTO> entityToDto(Collection<Caracteristique> caracteristiqueCollection){
		if (caracteristiqueCollection == null) {
			return null;
		}
		List<CaracteristiqueDTO> caracteristiqueDTOCollection = new ArrayList<>();
		for (Caracteristique caracteristique : caracteristiqueCollection) {
			caracteristiqueDTOCollection.add(entityToDto(caracteristique));
		}
		return caracteristiqueDTOCollection;
	}

	public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
		if(caracteristique == null) {
			return null;
		}

		CaracteristiqueDTO cdto = new CaracteristiqueDTO();
		cdto.setIdCaracteristique(caracteristique.getIdCaracteristique());

		ProduitDTO pdto = ProduitTransformer.entityToDto(caracteristique.getProduit());

		cdto.setProduit(pdto);

		TypeCaracteristiqueDTO tc = TypeCaracteristiqueTransformer.entityToDto(caracteristique.getTypeCaracteristique());

		cdto.setTypeCaracteristique(tc);

		cdto.setValeurCaracteristique(caracteristique.getValeurCaracteristique());

		return cdto;
	}

	public static Collection<Caracteristique> dtoToEntity(Collection<CaracteristiqueDTO> caracteristiqueDTOCollection){
		if (caracteristiqueDTOCollection== null) {
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
		caracteristique.setIdCaracteristique(caracteristiqueDTO.getIdCaracteristique());
		caracteristique.setValeurCaracteristique(caracteristiqueDTO.getValeurCaracteristique());
		caracteristique.setProduit(ProduitTransformer.dtoToEntity(caracteristiqueDTO.getProduit()));
		caracteristique.setTypeCaracteristique(TypeCaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO.getTypeCaracteristique()));
		
		return caracteristique;
	}
}
