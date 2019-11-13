package com.projet.ecommerce.business.dto;

public class CharacteristicValueDTO {

    private String value;
    private ProduitDTO productDto;
    private CharacteristicTypeDTO characteristicTypeDto;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProduitDTO getProductDto() {
        return productDto;
    }

    public void setProductDto(ProduitDTO productDto) {
        this.productDto = productDto;
    }

    public CharacteristicTypeDTO getCharacteristicTypeDto() {
        return characteristicTypeDto;
    }

    public void setCharacteristicTypeDto(CharacteristicTypeDTO characteristicTypeDto) {
        this.characteristicTypeDto = characteristicTypeDto;
    }
}
