package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/**
 * Entité représentant la table libelle sous forme de classe.
 */

 //TODO - est ce que le nom du libelle ne devrait pas etre la clé primaire ?

@Entity
@Table(name = "libelle")
public class Libelle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libelle")
    private int idLibelle;

    @Column(name = "nom")
    private String nom;

    public int getIdLibelle() {
        return idLibelle;
    }

    public void setIdLibelle(int idLibelle) {
        this.idLibelle = idLibelle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
