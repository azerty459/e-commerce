package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity representing the table characteristic_value
 */

@Entity
@Table(name = "characteristic_value")
public class Characteristic implements Serializable {

    @Column(name = "value_characteristic")
    private String value;

    @EmbeddedId
    private CharacteristicPK characteristicPK;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CharacteristicPK getCharacteristicPK() {
        return characteristicPK;
    }

    public void setCharacteristicPK(CharacteristicPK characteristicPK) {
        this.characteristicPK = characteristicPK;
    }

    /**
     * Clé composite
     */
    @Embeddable
    public static class CharacteristicPK implements Serializable {

        @ManyToOne
        private Produit product;
        @ManyToOne
        private TypeCharacteristic typeCharacteristic;

        public CharacteristicPK() {
        }

        public Produit getProduct() {
            return product;
        }

        public void setProduct(Produit product) {
            this.product = product;
        }

        public TypeCharacteristic getTypeCharacteristic() {
            return typeCharacteristic;
        }

        public void setTypeCharacteristic(TypeCharacteristic typeCharacteristic) {
            this.typeCharacteristic = typeCharacteristic;
        }
    }
}
