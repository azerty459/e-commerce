package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.persistance.entity.CharacteristicType;
import com.projet.ecommerce.persistance.entity.CharacteristicValue.CharacteristicPK;
import com.projet.ecommerce.business.dto.CharacteristicValueDTO;
import com.projet.ecommerce.persistance.entity.CharacteristicValue;

import java.util.ArrayList;
import java.util.Collection;

public class CharacteristicValueTransformer {

    private CharacteristicValueTransformer() {
    }

    public static Collection<CharacteristicValue> litDtoToEntity(Collection<CharacteristicValueDTO> characteristicValueDTOCollection) {
        Collection<CharacteristicValue> characteristicValueCollection = new ArrayList<>();
        if (characteristicValueDTOCollection != null) {
            for (CharacteristicValueDTO characteristicValueDTO : characteristicValueDTOCollection) {
                characteristicValueCollection.add(dtoToEntity(characteristicValueDTO));
            }
        }
        return characteristicValueCollection;
    }

    public static CharacteristicValue dtoToEntity(CharacteristicValueDTO characteristicValueDTO) {
        CharacteristicValue characteristicValue = new CharacteristicValue();
        CharacteristicPK characteristicPK = new CharacteristicPK();
        characteristicPK.setProduct(
                ProduitTransformer.dtoToEntity(characteristicValueDTO.getProductDto())
        );
        characteristicPK.setCharacteristicType(
                CharacteristicTypeTransformer.dtoToEntity(characteristicValueDTO.getCharacteristicTypeDto())
        );
        characteristicValue.setValueCharacteristic(characteristicValueDTO.getValue());
        characteristicValue.setCharacteristicPK(characteristicPK);
        return characteristicValue;
    }

    public static CharacteristicValueDTO entityToDto(CharacteristicValue characteristicValue) {
        CharacteristicValueDTO characteristicValueDTO = new CharacteristicValueDTO();
        characteristicValueDTO.setCharacteristicTypeDto(
                CharacteristicTypeTransformer.entityToDto(characteristicValue.getCharacteristicPK().getCharacteristicType())
                );
        characteristicValueDTO.setProductDto(
                ProduitTransformer.entityToDto(characteristicValue.getCharacteristicPK().getProduct())
        );
        characteristicValueDTO.setValue(characteristicValue.getValueCharacteristic());
        return characteristicValueDTO;
    }

    public static Collection<CharacteristicValueDTO> listEntityToDto(Collection<CharacteristicValue> characteristicValueCollection) {
        Collection<CharacteristicValueDTO> characteristicValueDTOCollection = new ArrayList<>();
        if (characteristicValueCollection != null) {
            for (CharacteristicValue characteristicValue : characteristicValueCollection) {
                characteristicValueDTOCollection.add(entityToDto(characteristicValue));
            }
        }
        return characteristicValueDTOCollection;
    }
}
