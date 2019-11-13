package com.projet.ecommerce.business.dto;

public class CharacteristicTypeDTO {

    /**
     * Id du type de characteristic
     */
    private int id;

    /**
     * Le type de characteristic
     */
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
