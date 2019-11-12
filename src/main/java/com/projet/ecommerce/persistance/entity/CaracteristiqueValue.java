package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entité representant la table caracteristique_value sous forme de class
 */

@Entity
@Table(name = "caracteristique_value")
public class CaracteristiqueValue {

    @Column(name = "caracteristique_value")
    private String caracteristiqueValue;

    @EmbeddedId
    private CaracteristicPK caracteristicPK;

    public String getCaracteristiqueValue() {
        return caracteristiqueValue;
    }

    public void setCaracteristiqueValue(String caracteristiqueValue) {
        this.caracteristiqueValue = caracteristiqueValue;
    }

    public CaracteristicPK getCaracteristicPK() {
        return caracteristicPK;
    }

    public void setCaracteristicPK(CaracteristicPK caracteristicPK) {
        this.caracteristicPK = caracteristicPK;
    }

    /**
     * Clé composite
     */
    @Embeddable
    public class CaracteristicPK implements Serializable {

        @ManyToOne
        private Produit produit;
        @ManyToOne
        private CaracteristiqueType caracteristiqueType;

        public CaracteristicPK() {
        }

        public Produit getProduit() {
            return produit;
        }

        public void setProduit(Produit produit) {
            this.produit = produit;
        }

        public CaracteristiqueType getCaracteristiqueType() {
            return caracteristiqueType;
        }

        public void setCaracteristiqueType(CaracteristiqueType caracteristiqueType) {
            this.caracteristiqueType = caracteristiqueType;
        }
    }
}
