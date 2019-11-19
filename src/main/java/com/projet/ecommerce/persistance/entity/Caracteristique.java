package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "caracteristique")
public class Caracteristique {

    @EmbeddedId
    private CaracteristiquePk caracteristiquePk;

    @Column(nullable = false, length = 100)
    private String valeur;

    public Caracteristique() {

    }

    public CaracteristiquePk getCaracteristiquePk() {
        return caracteristiquePk;
    }

    public void setCaracteristiquePk(CaracteristiquePk caracteristiquePk) {
        this.caracteristiquePk = caracteristiquePk;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @Embeddable
    public static class CaracteristiquePk implements Serializable {

        @ManyToOne()
        @JoinColumn(name = "type_caracteristique_id")
        private TypeCaracteristique typeCaracteristique;

        @ManyToOne()
        @JoinColumn(name = "produit_id")
        private Produit produit;

        public CaracteristiquePk() {

        }

        public TypeCaracteristique getTypeCaracteristique() {
            return typeCaracteristique;
        }

        public void setTypeCaracteristique(TypeCaracteristique typeCaracteristique) {
            this.typeCaracteristique = typeCaracteristique;
        }

        public Produit getProduit() {
            return produit;
        }

        public void setProduit(Produit produit) {
            this.produit = produit;
        }

    }
}
