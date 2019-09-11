package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/**
 * Entité représentant la table caracteristique sous forme de classe.
 */

@Entity
@Table(name = "caracteristique")
@IdClass(CaracteristiqueID.class)
public class Caracteristique {

    @Id
    @Column(name = "reference_produit")
    private String referenceProduit;

    @Id
    @Column(name = "type")
    private String type;

    @Column(name = "valeur")
    private String valeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_produit", insertable= false, updatable = false)
    private Produit produit;


    public String getReferenceProduit() {
        return referenceProduit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
