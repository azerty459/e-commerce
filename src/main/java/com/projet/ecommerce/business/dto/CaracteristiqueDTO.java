package com.projet.ecommerce.business.dto;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;


public class CaracteristiqueDTO {

    private TypeCaracteristique typeCaracteristique;

    private String valeur;

    public TypeCaracteristique getTypeCaracteristique() {
        return typeCaracteristique;
    }

    public void setTypeCaracteristique(TypeCaracteristique typeCaracteristique) {
        this.typeCaracteristique = typeCaracteristique;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
