package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CharacteristicDTO;
import com.projet.ecommerce.persistance.entity.Characteristic;
import com.projet.ecommerce.persistance.entity.Characteristic.CharacteristicPK;

import java.util.ArrayList;
import java.util.List;

public class CharacteristicTransformer {

    public static Characteristic dtoToEntity(CharacteristicDTO characteristicDTO) {
        Characteristic characteristic = new Characteristic();

        //Avoid the nullPointer Exception by creating an instance of CharacteristicPK class
        CharacteristicPK characteristicPK = new CharacteristicPK();
        characteristicPK.setProduct(ProduitTransformer.dtoToEntity(
                characteristicDTO.getProductDto()));
        characteristicPK.setTypeCharacteristic(
                TypeCharacteristicTransformer.dtoToEntity(characteristicDTO.getTypeCharacteristicDTO()));

        characteristic.setCharacteristicPK(characteristicPK);
        characteristic.setValue(characteristicDTO.getValeur());
        return characteristic;
    }

    public static CharacteristicDTO entityToDto(Characteristic characteristic) {
        CharacteristicDTO characteristicDTO = new CharacteristicDTO();

        characteristicDTO.setProductDto(ProduitTransformer.entityToDto(characteristic.getCharacteristicPK().getProduct()));
        characteristicDTO.setValeur(characteristic.getValue());
        characteristicDTO.setTypeCharacteristicDTO(TypeCharacteristicTransformer.entityToDto(characteristic.getCharacteristicPK().getTypeCharacteristic()));
        return characteristicDTO;
    }

    public static List<Characteristic> listDtoToEntity(List<CharacteristicDTO> characteristicDTOList) {
        List<Characteristic> characteristicList = new ArrayList<>();
        for (CharacteristicDTO characteristicDTO : characteristicDTOList) {
            characteristicList.add(dtoToEntity(characteristicDTO));
        }
        return characteristicList;
    }

    public static List<CharacteristicDTO> listEntityToDto(List<Characteristic> characteristicList) {
        List<CharacteristicDTO> characteristicDTOList = new ArrayList<>();
        for (Characteristic characteristic : characteristicList) {
            characteristicDTOList.add(entityToDto(characteristic));
        }
        return characteristicDTOList;
    }
}