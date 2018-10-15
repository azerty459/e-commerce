package com.projet.ecommerce.business.dto;

import com.projet.ecommerce.persistance.entity.Caracteristique;

public class CaracteristiqueAssociatedDTO {

    private CaracteristiqueDTO caracteristique;

    private String value;

    public CaracteristiqueDTO getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(CaracteristiqueDTO caracteristique) {
        this.caracteristique = caracteristique;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
