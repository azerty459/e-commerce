package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

public class CaracteristiqueTransformer {

    private CaracteristiqueTransformer(){}

    /**
     * Transforme un objet Caracteristique en CaracteristiqueDTO
     * @param caracteristique
     * @return
     */
    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique){
        CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
        caracteristiqueDTO.setReferenceProduit(caracteristique.getReferenceProduit());
        caracteristiqueDTO.setType(caracteristique.getTypeCaracteristique());
        caracteristiqueDTO.setValeur(caracteristique.getValeurCaracteristique());
        return caracteristiqueDTO;
    }

}
