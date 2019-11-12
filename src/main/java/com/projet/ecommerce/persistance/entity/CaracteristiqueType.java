package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/**
 * Entité representant la table caracteristique_type sous forme de class
 */

@Entity
@Table(name = "caracteristique_type")
public class CaracteristiqueType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristique_type")
    private int idCaracteristiqueType;

    @Column
    private String typeCaracteristique;

    public int getIdCaracteristiqueType() {
        return idCaracteristiqueType;
    }

    public void setIdCaracteristiqueType(int idCaracteristiqueType) {
        this.idCaracteristiqueType = idCaracteristiqueType;
    }

    public String getTypeCaracteristique() {
        return typeCaracteristique;
    }

    public void setTypeCaracteristique(String typeCaracteristique) {
        this.typeCaracteristique = typeCaracteristique;
    }
}
