package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/**
 * Entity representing la table characteristic_type
 */

@Entity
@Table(name = "characteristic_type")
public class TypeCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_characteristic_type")
    private int idCharacteristicType;

    @Column
    private String typeCharacteristic;

    public int getIdCharacteristicType() {
        return idCharacteristicType;
    }

    public void setIdCharacteristicType(int idCharacteristicType) {
        this.idCharacteristicType = idCharacteristicType;
    }

    public String getTypeCharacteristic() {
        return typeCharacteristic;
    }

    public void setTypeCharacteristic(String typeCharacteristic) {
        this.typeCharacteristic = typeCharacteristic;
    }
}
