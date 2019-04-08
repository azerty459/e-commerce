package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.CaracteristiquePK;

public class CaracteristiqueTransformer {
	
	/**
     * Transforme un objet CaracteristiqueDTO en un objet Caracteristique.
     *
     * @param caracteristiqueDTO Un objet CaracteristiqueDTO
     * @return un objet Caracteristique
     */
    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO) {
       Caracteristique caracteristique = new Caracteristique();
       caracteristique.setIdCaracteristique(caracteristiqueDTO.getId());
       caracteristique.setCaracteristiquePk(new CaracteristiquePK());
       caracteristique.getCaracteristiquePk()
       		.setTypeCaracteristique(TypeCaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO.getTypeCaracteristiqueDto()));
       caracteristique.getCaracteristiquePk()
  			.setProduit(ProduitTransformer.dtoToEntity(caracteristiqueDTO.getProduitDto()));
       caracteristique.setValeurCaracteristique(caracteristiqueDTO.getValeurCaracteristique());
       return caracteristique;
    }
    
    public static Collection<CaracteristiqueDTO> entityToDto(Collection<Caracteristique> caracteristiques) {
        if (caracteristiques == null) {
            return null;
        }
        List<CaracteristiqueDTO> caracteristiqueDTOCollection = new ArrayList<>();
        for (Caracteristique caracteristique : caracteristiques) {
        	caracteristiqueDTOCollection.add(entityToDto(caracteristique));
        }
        return caracteristiqueDTOCollection;
    }
    
    /**
     * Transforme un objet CaracteristiqueDTO en un objet Caracteristique.
     *
     * @param caracteristiqueDTO Un objet CaracteristiqueDTO
     * @return un objet Caracteristique
     */
    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
       CaracteristiqueDTO caracteristiqueDto = new CaracteristiqueDTO();
       caracteristiqueDto.setId(caracteristique.getIdCaracteristique());
       caracteristiqueDto.setTypeCaracteristiqueDto(
    		   TypeCaracteristiqueTransformer.entityToDto(caracteristique.getCaracteristiquePk().getTypeCaracteristique())
       );
       caracteristiqueDto.setProduitDto(
    		   ProduitTransformer.entityToDto(caracteristique.getCaracteristiquePk().getProduit())
       );
       caracteristiqueDto.setValeurCaracteristique(caracteristique.getValeurCaracteristique());
       return caracteristiqueDto;
    }
    
    public static Collection<Caracteristique> dtoToEntity(Collection<CaracteristiqueDTO> caracteristiquesDto) {
        if (caracteristiquesDto == null) {
            return null;
        }
        List<Caracteristique> caracteristiqueCollection = new ArrayList<>();
        for (CaracteristiqueDTO caracteristiqueDto : caracteristiquesDto) {
        	caracteristiqueCollection.add(dtoToEntity(caracteristiqueDto));
        }
        return caracteristiqueCollection;
    }

}
