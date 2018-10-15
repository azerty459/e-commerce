package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueAssociatedDTO;
import com.projet.ecommerce.persistance.entity.CaracteristiqueAssociated;

import java.util.Collection;
import java.util.stream.Collectors;

public class CaracteristiqueAssociatedTransformer {

    public static CaracteristiqueAssociated dtoToEntity (CaracteristiqueAssociatedDTO caracteristiqueAssociatedDTO) {
        CaracteristiqueAssociated result = new CaracteristiqueAssociated();
        result.setCaracteristique(CaracteristiqueTransformer.dtoToEntity(caracteristiqueAssociatedDTO.getCaracteristique()));
        result.setValue(caracteristiqueAssociatedDTO.getValue());
        return result;
    }

    public static CaracteristiqueAssociatedDTO entityToDto (CaracteristiqueAssociated caracteristiqueAssociated) {
        CaracteristiqueAssociatedDTO result = new CaracteristiqueAssociatedDTO();
        result.setCaracteristique(CaracteristiqueTransformer.entityToDto(caracteristiqueAssociated.getCaracteristique()));
        result.setValue(caracteristiqueAssociated.getValue());
        return result;
    }

    public static Collection<CaracteristiqueAssociatedDTO> entityToDto(
            Collection<CaracteristiqueAssociated> caracteristiqueAssociatedCollection
    ) {
        if (caracteristiqueAssociatedCollection == null) {
            return null;
        }
        return caracteristiqueAssociatedCollection
                .stream()
                .map(CaracteristiqueAssociatedTransformer::entityToDto)
                .collect(Collectors.toList());
    }


    public static Collection<CaracteristiqueAssociated> dtoToEntity(
            Collection<CaracteristiqueAssociatedDTO> caracteristiqueAssociatedDtoCollection
    ) {
        if (caracteristiqueAssociatedDtoCollection == null) {
            return null;
        }
        return caracteristiqueAssociatedDtoCollection
                .stream()
                .map(CaracteristiqueAssociatedTransformer::dtoToEntity)
                .collect(Collectors.toList());
    }
}
