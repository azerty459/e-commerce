package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/**
 * @author nextoo
 * classe represantant la table caracteristique
 */
@Entity
@Table(name = "caracteristique")
public class Caracteristique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristique")
    private int idCaracteristique;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_caracteristique")
    private TypeCaracteristique type;

    @Column(name = "valeur")
    private String valeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_produit")
    private Produit produit;


    public int getIdCaracteristique() {
        return idCaracteristique;
    }

    public void setIdCaracteristique(int idCaracteristique) {
        this.idCaracteristique = idCaracteristique;
    }

    public TypeCaracteristique getType() {
        return type;
    }

    public void setType(TypeCaracteristique type) {
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
