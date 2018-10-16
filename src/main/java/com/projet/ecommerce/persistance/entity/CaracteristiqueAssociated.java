package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "produit_caracteristique")
public class CaracteristiqueAssociated implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "reference_produit")
    private Produit produit;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_caracteristique")
    private Caracteristique caracteristique;

    @Column
    private String value;

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Caracteristique getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(Caracteristique caracteristique) {
        this.caracteristique = caracteristique;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
