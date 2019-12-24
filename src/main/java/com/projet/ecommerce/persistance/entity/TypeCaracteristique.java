package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

@Entity
@Table(name = "type_caracteristique")
public class TypeCaracteristique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int id_type_caracteristique;


    @Column(name = "libelle")
    private String libelle;

    public int getId_type_caracteristique() {
        return id_type_caracteristique;
    }

    public void setId_type_caracteristique(int id_type_caracteristique) {
        this.id_type_caracteristique = id_type_caracteristique;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
