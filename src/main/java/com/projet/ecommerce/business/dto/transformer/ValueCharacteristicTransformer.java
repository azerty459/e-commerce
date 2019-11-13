package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.persistance.entity.ValueCharacteristic.CharacteristicPK;
import com.projet.ecommerce.business.dto.ValueCharacteristicDTO;
import com.projet.ecommerce.persistance.entity.ValueCharacteristic;

import java.util.ArrayList;
import java.util.Collection;

public class ValueCharacteristicTransformer {

    private ValueCharacteristicTransformer() {
    }

    public static Collection<ValueCharacteristic> litDtoToEntity(Collection<ValueCharacteristicDTO> valueCharacteristicDTOCollection) {
        Collection<ValueCharacteristic> valueCharacteristicCollection = new ArrayList<>();
        if (valueCharacteristicDTOCollection != null) {
            for (ValueCharacteristicDTO valueCharacteristicDTO : valueCharacteristicDTOCollection) {
                valueCharacteristicCollection.add(dtoToEntity(valueCharacteristicDTO));
            }
        }
        return valueCharacteristicCollection;
    }

    public static ValueCharacteristic dtoToEntity(ValueCharacteristicDTO valueCharacteristicDTO) {
        ValueCharacteristic valueCharacteristic = new ValueCharacteristic();
        CharacteristicPK characteristicPK = new CharacteristicPK();
        characteristicPK.setProduct(
                ProduitTransformer.dtoToEntity(valueCharacteristicDTO.getProductDto())
        );
        characteristicPK.setTypeCharacteristic(
                TypeCharacteristicTransformer.dtoToEntity(valueCharacteristicDTO.getTypeCharacteristicDto())
        );
        valueCharacteristic.setValueCharacteristic(valueCharacteristicDTO.getValue());
        valueCharacteristic.setCharacteristicPK(characteristicPK);
        return valueCharacteristic;
    }

    public static ValueCharacteristicDTO entityToDto(ValueCharacteristic valueCharacteristic) {
        ValueCharacteristicDTO valueCharacteristicDTO = new ValueCharacteristicDTO();
        valueCharacteristicDTO.setTypeCharacteristicDto(
                TypeCharacteristicTransformer.entityToDto(valueCharacteristic.getCharacteristicPK().getTypeCharacteristic())
                );
        valueCharacteristicDTO.setProductDto(
                ProduitTransformer.entityToDto(valueCharacteristic.getCharacteristicPK().getProduct())
        );
        valueCharacteristicDTO.setValue(valueCharacteristic.getValueCharacteristic());
        return valueCharacteristicDTO;
    }

    public static Collection<ValueCharacteristicDTO> listEntityToDto(Collection<ValueCharacteristic> valueCharacteristicCollection) {
        Collection<ValueCharacteristicDTO> valueCharacteristicDTOCollection = new ArrayList<>();
        if (valueCharacteristicCollection != null) {
            for (ValueCharacteristic valueCharacteristic : valueCharacteristicCollection) {
                valueCharacteristicDTOCollection.add(entityToDto(valueCharacteristic));
            }
        }
        return valueCharacteristicDTOCollection;
    }
}
