package com.projet.ecommerce.business.dto;

public class ValueCharacteristicDTO {

    private String value;
    private ProduitDTO productDto;
    private TypeCharacteristicDTO typeCharacteristicDto;

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

    public TypeCharacteristicDTO getTypeCharacteristicDto() {
        return typeCharacteristicDto;
    }

    public void setTypeCharacteristicDto(TypeCharacteristicDTO typeCharacteristicDto) {
        this.typeCharacteristicDto = typeCharacteristicDto;
    }
}
