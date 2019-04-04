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
