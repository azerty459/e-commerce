package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCharacteristicDTO;
import com.projet.ecommerce.persistance.entity.TypeCharacteristic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeCharacteristicTransformer {

    private TypeCharacteristicTransformer() {
    }

    /**
     * Transform the object collection {@link TypeCharacteristicDTO} to another object collection {@link TypeCharacteristic}
     * @param typeCharacteristicDTOCollection
     * @return
     */
    public static List<TypeCharacteristic> listDtoToEntity (List<TypeCharacteristicDTO> typeCharacteristicDTOCollection) {
        List<TypeCharacteristic> typeCharacteristicCollection = new ArrayList<>();
        if (typeCharacteristicDTOCollection != null) {
            for (TypeCharacteristicDTO typeCharacteristicDTO : typeCharacteristicDTOCollection) {
                typeCharacteristicCollection.add(dtoToEntity(typeCharacteristicDTO));
            }
        }
        return typeCharacteristicCollection;
    }

    /**
     * Transform the object {@link TypeCharacteristicDTO} to another object {@link TypeCharacteristic}
     * @param typeCharacteristicDTO
     * @return
     */
    public static TypeCharacteristic dtoToEntity(TypeCharacteristicDTO typeCharacteristicDTO) {
        TypeCharacteristic typeCharacteristic = new TypeCharacteristic();
        typeCharacteristic.setIdCharacteristicType(typeCharacteristicDTO.getId());
        typeCharacteristic.setTypeCharacteristic(typeCharacteristicDTO.getType());
        return typeCharacteristic;
    }

    /**
     * Transform the object collection {@link TypeCharacteristic} to another object collection {@link TypeCharacteristicDTO}
     * @param typeCharacteristicCollection
     * @return
     */
    public static List<TypeCharacteristicDTO> listEntityToDto(List<TypeCharacteristic> typeCharacteristicCollection) {
        List<TypeCharacteristicDTO> typeCharacteristicDTOCollection = new ArrayList<>();
        if (typeCharacteristicCollection != null) {
            for (TypeCharacteristic typeCharacteristic : typeCharacteristicCollection) {
                typeCharacteristicDTOCollection.add(entityToDto(typeCharacteristic));
            }
        }
        return typeCharacteristicDTOCollection;
    }

    /**
     * Transform the object {@link TypeCharacteristic} to another object {@link TypeCharacteristicDTO}
     * @param typeCharacteristic
     * @return
     */
    public static TypeCharacteristicDTO entityToDto(TypeCharacteristic typeCharacteristic) {
        TypeCharacteristicDTO typeCharacteristicDTO = new TypeCharacteristicDTO();
        typeCharacteristicDTO.setId(typeCharacteristic.getIdCharacteristicType());
        typeCharacteristicDTO.setType(typeCharacteristic.getTypeCharacteristic());
        return typeCharacteristicDTO;
    }
}
