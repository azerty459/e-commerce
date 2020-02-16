package com.projet.ecommerce.persistance.entity;

import com.projet.ecommerce.persistance.primarykey.CaracteristiqueID;

import javax.persistence.*;

/**
 * Entité représentant la table caracteristique sous forme de classe.
 */

@Entity
@Table(name = "caracteristique")
public class Caracteristique {
    @EmbeddedId
    private CaracteristiqueID id;

    @Column(name = "valeur")
    private String valeur;

    //TODO - FetchType et Cascade mode
    @ManyToOne
    @MapsId("referenceProduit")
    private Produit produit;
 
    //TODO - FetchType et Cascade mode
    @ManyToOne
    @MapsId("idLibelle")
    private Libelle libelle;

    //XXX - pas de setter pour caracteristiqueId car en fonction du produit et du libellé - correct ?
    public CaracteristiqueID getId() {
        return id;
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

    //XXX - bonne manière de faire ?
    public void setProduit(Produit produit) {
        if (produit==null) return;
        this.produit = produit;
        this.id.referenceProduit = produit.getReferenceProduit();
    }

    public Libelle getLibelle() {
        return libelle;
    }

    //XXX - idem
    public void setLibelle(Libelle libelle) {
        if (libelle==null) return;
        this.libelle = libelle;
        this.id.idLibelle = libelle.getIdLibelle();
    }
}
