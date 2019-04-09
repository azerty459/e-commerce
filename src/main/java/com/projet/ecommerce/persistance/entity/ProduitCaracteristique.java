package com.projet.ecommerce.persistance.entity;

import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "produit_caracteristique")
public class ProduitCaracteristique {
    
    @EmbeddedId
    private ProduitCaracteristiqueId id = new ProduitCaracteristiqueId();
    
    @MapsId("referenceProduit")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_produit")
    private Produit produit;
    
    @MapsId("idCaracteristique")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caracteristique")
    private Caracteristique caracteristique;
    
    private String valeur;

    public ProduitCaracteristique() {
        //Utilisé par JPA
    }
    
    public ProduitCaracteristique(Produit p, Caracteristique c) {
        this.produit = p;
        this.caracteristique = c;
        this.id = new ProduitCaracteristiqueId(p.getReferenceProduit(), c.getIdCaracteristique());
    }

    public ProduitCaracteristiqueId getId() {
        return id;
    }

    public void setId(ProduitCaracteristiqueId id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Caracteristique getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(Caracteristique caracteristique) {
        this.caracteristique = caracteristique;
    }
    
    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        ProduitCaracteristique other = (ProduitCaracteristique) obj;
        return this.produit.getReferenceProduit().equals(other.produit.getReferenceProduit()) && this.caracteristique.getIdCaracteristique() == other.caracteristique.getIdCaracteristique();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.produit, this.caracteristique);
    }
    
}