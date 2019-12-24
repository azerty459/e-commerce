package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/***
 * Entity de la table caracteristique
 * decrit une caracteristique de produit
 */

@Entity
@Table(name = "caracteristique")
public class Caracteristique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_caracteristique;

    @Column(name = "type_id")
    private int id_type ;

    @Column(name = "valeur")
    private String valeur;


    public int getId_caracteristique() {
        return id_caracteristique;
    }

    public void setId_caracteristique(int id_caracteristique) {
        this.id_caracteristique = id_caracteristique;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
