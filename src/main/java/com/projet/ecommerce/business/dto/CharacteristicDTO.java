package com.projet.ecommerce.business.dto;

public class CharacteristicDTO {

    private String valeur;
    private ProduitDTO productDto;
    private TypeCharacteristicDTO typeCharacteristicDTO;

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public ProduitDTO getProductDto() {
        return productDto;
    }

    public void setProductDto(ProduitDTO productDto) {
        this.productDto = productDto;
    }

    public TypeCharacteristicDTO getTypeCharacteristicDTO() {
        return typeCharacteristicDTO;
    }

    public void setTypeCharacteristicDTO(TypeCharacteristicDTO typeCharacteristicDTO) {
        this.typeCharacteristicDTO = typeCharacteristicDTO;
    }
}