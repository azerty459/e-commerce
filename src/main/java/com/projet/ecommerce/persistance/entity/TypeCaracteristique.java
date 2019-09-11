package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entité représentant la table type_caracteristique sous forme de classe.
 */

@Entity
@Table(name = "Type_Caracteristique")
public class TypeCaracteristique {
    @Id
    @Column(name = "type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
