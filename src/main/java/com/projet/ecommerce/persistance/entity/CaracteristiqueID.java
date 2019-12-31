package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class CaracteristiqueID implements Serializable {

    @Column(name = "reference_produit")
    private String reference_produit;

    @Column(name = "type_id")
    private Integer type_id;

    public CaracteristiqueID(String referenceProduit, Integer type_id) {
        this.reference_produit = referenceProduit;
        this.type_id = type_id;
    }

    public CaracteristiqueID() {
    }

    public String getReferenceProduit() {
        return reference_produit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.reference_produit = referenceProduit;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "CaracteristiqueID{" +
                "referenceProduit='" + reference_produit + '\'' +
                ", type_id=" + type_id +
                '}';
    }
}
