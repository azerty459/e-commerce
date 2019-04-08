package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/**
 * Entité représentant la table des types d'une caractéristique sous forme de classe
 */


@Entity
@Table(name = "type_caracteristique")
public class TypeCaracteristique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_caracteristique")
    private int idTypeCaracteristique;

    @Column
    private String libelle;

    @Override
    public boolean equals(Object o) {

        if(this == o) {
            return true;
        }

        if(o == null || o.getClass()!= this.getClass()) {
            return false;
        }

        TypeCaracteristique typeCaracteristique = (TypeCaracteristique) o;

        return (this.idTypeCaracteristique == ((TypeCaracteristique) o).getIdTypeCaracteristique() && this.libelle == ((TypeCaracteristique) o).getLibelle());

    }

    public int getIdTypeCaracteristique() {
        return idTypeCaracteristique;
    }

    public void setIdTypeCaracteristique(int idTypeCaracteristique) {
        this.idTypeCaracteristique = idTypeCaracteristique;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
