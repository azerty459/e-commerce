package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;


@Entity
@Table(name = "type_caracteristique")
public class TypeCaracteristique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_caracteristique")
    private int idTypeCaracteristique;

    @Column(name = "type")
    private String type;

    public TypeCaracteristique() {
    }

    public TypeCaracteristique(final int idTypeCaracteristique,final String type) {
        this.idTypeCaracteristique = idTypeCaracteristique;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdTypeCaracteristique() {
        return idTypeCaracteristique;
    }

    public void setIdTypeCaracteristique(int idTypeCaracteristique) {
        this.idTypeCaracteristique = idTypeCaracteristique;
    }
}
