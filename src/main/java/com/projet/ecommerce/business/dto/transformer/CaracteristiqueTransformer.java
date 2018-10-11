package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

import java.util.Collection;
import java.util.stream.Collectors;

public class CaracteristiqueTransformer {

    /**
     * Transforme un objet CaracteristiqueDTO en Caracteristique
     *
     * @param caracteristiqueDTO Un objet CaracteristiqueDTO
     * @return un objet Caracteristique
     */
    public static Caracteristique dtoToEntity(CaracteristiqueDTO caracteristiqueDTO) {
        Caracteristique result = new Caracteristique();
        result.setId(caracteristiqueDTO.getId());
        result.setLabel(caracteristiqueDTO.getLabel());
        return result;
    }

    public static CaracteristiqueDTO entityToDto(Caracteristique caracteristique) {
        CaracteristiqueDTO result = new CaracteristiqueDTO();
        result.setId(caracteristique.getId());
        result.setLabel(caracteristique.getLabel());
        return result;
    }

    public static Collection<CaracteristiqueDTO> entityToDto(Collection<Caracteristique> caracteristiqueCollection) {
        if (caracteristiqueCollection == null) {
            return null;
        }
        return caracteristiqueCollection
                .stream()
                .map(CaracteristiqueTransformer::entityToDto)
                .collect(Collectors.toList());
    }
}
