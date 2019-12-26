package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/***
 * Entity de la table caracteristique
 * decrit une caracteristique de produit
 */

@Entity
@Table(name = "caracteristique")
public class Caracteristique {

    @EmbeddedId
    private CaracteristiqueID caracteristiqueID;

    private String valeur;

    public Caracteristique() {
    }

    public Caracteristique(CaracteristiqueID caracteristiqueID, String valeur) {
        this.caracteristiqueID = caracteristiqueID;
        this.valeur = valeur;
    }


    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public CaracteristiqueID getCaracteristiqueID() {
        return caracteristiqueID;
    }

    public void setCaracteristiqueID(CaracteristiqueID caracteristiqueID) {
        this.caracteristiqueID = caracteristiqueID;
    }


    @Override
    public String toString() {
        return "Caracteristique{" +
                "caracteristiqueID=" + caracteristiqueID +
                ", valeur='" + valeur + '\'' +
                '}';
    }
}
