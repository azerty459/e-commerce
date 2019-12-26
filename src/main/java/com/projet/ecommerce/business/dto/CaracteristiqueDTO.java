package com.projet.ecommerce.business.dto;

import com.projet.ecommerce.persistance.entity.CaracteristiqueID;

public class CaracteristiqueDTO {
    private CaracteristiqueID caracteristiqueID;
    private String valeur ;


    public CaracteristiqueID getCaracteristiqueID() {
        return caracteristiqueID;
    }

    public void setCaracteristiqueID(CaracteristiqueID caracteristiqueID) {
        this.caracteristiqueID = caracteristiqueID;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
