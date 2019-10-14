package com.projet.ecommerce.persistance.entity;

import java.io.Serializable;

public class CaracteristiqueId implements Serializable {
    private String referenceProduit;
    private String typeCaracteristique;

    public CaracteristiqueId(){}

    public CaracteristiqueId(String referenceProduit, String typeCaracteristique) {
        this.referenceProduit = referenceProduit;
        this.typeCaracteristique = typeCaracteristique;
    }

    public String getReferenceProduit() {
        return referenceProduit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    public String getTypeCaracteristique() {
        return typeCaracteristique;
    }

    public void setTypeCaracteristique(String typeCaracteristique) {
        this.typeCaracteristique = typeCaracteristique;
    }
}
