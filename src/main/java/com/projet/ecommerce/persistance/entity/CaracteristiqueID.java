package com.projet.ecommerce.persistance.entity;

import java.io.Serializable;

public class CaracteristiqueID implements Serializable {
    private String referenceProduit;
    private String type;

    public CaracteristiqueID(){}

    public CaracteristiqueID(String referenceProduit, String typeCaracteristique) {
        this.referenceProduit = referenceProduit;
        this.type = typeCaracteristique;
    }

    public String getReferenceProduit() {
        return referenceProduit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
