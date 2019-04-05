package com.projet.ecommerce.persistance.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "produit_caracteristique")
public class ProduitCaracteristique_1 {
    
    @EmbeddedId
    private ProduitCaracteristiqueId id = new ProduitCaracteristiqueId();
    
    @MapsId("referenceProduit")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_produit")
    private Produit produ;
    
    @MapsId("idCaracteristique")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caracteristique")
    private Caracteristique caracteristique;
    
    private String valeur;
    
    public ProduitCaracteristique_1() {
        
    }
    
    public ProduitCaracteristique_1(Produit p, Caracteristique c) {
        this.produ = p;
        this.caracteristique = c;
        this.id = new ProduitCaracteristiqueId(p.getReferenceProduit(), c.getIdCaracteristique());
    }

    public ProduitCaracteristiqueId getId() {
        return id;
    }

    public void setId(ProduitCaracteristiqueId id) {
        this.id = id;
    }

    public Produit getProdu() {
        return produ;
    }

    public void setProdu(Produit produit) {
        this.produ = produit;
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
        ProduitCaracteristique_1 other = (ProduitCaracteristique_1) obj;
        return this.produ.getReferenceProduit().equals(other.produ.getReferenceProduit()) && this.caracteristique.getIdCaracteristique() == other.caracteristique.getIdCaracteristique();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.produ, this.caracteristique);
    }
    
    
    
}
