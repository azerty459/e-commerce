package com.projet.ecommerce.persistance.entity;


import javax.persistence.*;

@Entity
@Table(name = "caracteristique")
public class Caracteristique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristique")
    private int idCaracteristique;

    @Column(name = "type_caracteristique")
    @ManyToOne
    private TypeCaracteristique typeCaracteristique;

    @Column
    private String valeur;

    @Column
    @ManyToOne
    private Produit produit;

    public int getIdCaracteristique() {
        return idCaracteristique;
    }

    public void setIdCaracteristique(int idCaracteristique) {
        this.idCaracteristique = idCaracteristique;
    }

    public TypeCaracteristique getTypeCaracteristique() {
        return typeCaracteristique;
    }

    public void setTypeCaracteristique(TypeCaracteristique typeCaracteristique) {
        this.typeCaracteristique = typeCaracteristique;
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
