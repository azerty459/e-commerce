package com.projet.ecommerce.persistance.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "produit_caracteristique")
public class ProduitCaracteristique {
    
    @EmbeddedId
    private ProduitCaracteristiqueId id = new ProduitCaracteristiqueId();
    
    private String valeur;

    public ProduitCaracteristique() {
        //Utilisé par JPA
    }
    
    public ProduitCaracteristique(Produit p, Caracteristique c) {
        this.id = new ProduitCaracteristiqueId(p, c);
    }

    public ProduitCaracteristiqueId getId() {
        return id;
    }

    public void setId(ProduitCaracteristiqueId id) {
        this.id = id;
    }
    
    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    /*
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
    */
    
}
