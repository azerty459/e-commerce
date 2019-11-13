package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CharacteristicTypeDTO;
import com.projet.ecommerce.persistance.entity.CharacteristicType;

import java.util.ArrayList;
import java.util.Collection;

public class CharacteristicTypeTransformer {

    private CharacteristicTypeTransformer() {
    }

    /**
     * Transform the object collection {@link CharacteristicTypeDTO} to another object collection {@link CharacteristicType}
     * @param characteristicTypeDTOCollection
     * @return
     */
    public static Collection<CharacteristicType> listDtoToEntity (Collection<CharacteristicTypeDTO> characteristicTypeDTOCollection) {
        Collection<CharacteristicType> characteristicTypeCollection = new ArrayList<>();
        if (characteristicTypeDTOCollection != null) {
            for (CharacteristicTypeDTO characteristicTypeDTO : characteristicTypeDTOCollection) {
                characteristicTypeCollection.add(dtoToEntity(characteristicTypeDTO));
            }
        }
        return characteristicTypeCollection;
    }

    /**
     * Transform the object {@link CharacteristicTypeDTO} to another object {@link CharacteristicType}
     * @param characteristicTypeDTO
     * @return
     */
    public static CharacteristicType dtoToEntity(CharacteristicTypeDTO characteristicTypeDTO) {
        CharacteristicType characteristicType = new CharacteristicType();
        characteristicType.setIdCharacteristicType(characteristicTypeDTO.getId());
        characteristicType.setTypeCharacteristic(characteristicTypeDTO.getType());
        return characteristicType;
    }

    /**
     * Transform the object collection {@link CharacteristicType} to another object collection {@link CharacteristicTypeDTO}
     * @param characteristicTypeCollection
     * @return
     */
    public static Collection<CharacteristicTypeDTO> listEntityToDto(Collection<CharacteristicType> characteristicTypeCollection) {
        Collection<CharacteristicTypeDTO> characteristicTypeDTOCollection = new ArrayList<>();
        if (characteristicTypeCollection != null) {
            for (CharacteristicType characteristicType : characteristicTypeCollection) {
                characteristicTypeDTOCollection.add(entityToDto(characteristicType));
            }
        }
        return characteristicTypeDTOCollection;
    }

    /**
     * Transform the object {@link CharacteristicType} to another object {@link CharacteristicTypeDTO}
     * @param characteristicType
     * @return
     */
    public static CharacteristicTypeDTO entityToDto(CharacteristicType characteristicType) {
        CharacteristicTypeDTO characteristicTypeDTO = new CharacteristicTypeDTO();
        characteristicTypeDTO.setId(characteristicType.getIdCharacteristicType());
        characteristicTypeDTO.setType(characteristicType.getTypeCharacteristic());
        return characteristicTypeDTO;
    }
}
