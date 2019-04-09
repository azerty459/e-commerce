package com.projet.ecommerce.persistance.entity;

import com.google.common.base.Objects;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ProduitCaracteristiqueId implements Serializable{
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_produit")
    private Produit produit;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caracteristique")
    private Caracteristique caracteristique;

    public ProduitCaracteristiqueId() {
        //Utilisé par JPA
    }

    public ProduitCaracteristiqueId(Produit produit, Caracteristique caracteristique) {
        this.produit = produit;
        this.caracteristique = caracteristique;
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
    
    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        } else if(this == o) {
            return true;
        } else if(o instanceof ProduitCaracteristiqueId) {
            ProduitCaracteristiqueId pci = (ProduitCaracteristiqueId) o;
            return this.produit.getReferenceProduit().equals(pci.produit.getReferenceProduit()) &&
                    this.caracteristique.getIdCaracteristique() == pci.getCaracteristique().getIdCaracteristique();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.produit.getReferenceProduit(), this.caracteristique.getIdCaracteristique());
    }     
    
}
