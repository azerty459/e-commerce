package com.projet.ecommerce.business.dto;

import java.util.Objects;

public class CaracteristiqueDTO {

    private TypeCaracteristiqueDTO typeCaracteristique;

    private String valeur;

    public CaracteristiqueDTO() {
    }

    public TypeCaracteristiqueDTO getTypeCaracteristique() {
        return typeCaracteristique;
    }

    public void setTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristique) {
        this.typeCaracteristique = typeCaracteristique;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }


}
