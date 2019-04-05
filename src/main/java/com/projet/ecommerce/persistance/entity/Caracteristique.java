package com.projet.ecommerce.persistance.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "caracteristique")
public class Caracteristique {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristique")
    private int idCaracteristique;
    
    @Column(name = "label_caracteristique")
    private String label;
    
    @OneToMany(
        mappedBy = "caracteristique",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ProduitCaracteristique> produits = new ArrayList<>();


    public int getIdCaracteristique() {
        return idCaracteristique;
    }

    public void setIdCaracteristique(int idCaracteristique) {
        this.idCaracteristique = idCaracteristique;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ProduitCaracteristique> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitCaracteristique> produits) {
        this.produits = produits;
    }
    
    @Override
    public String toString() {
        return idCaracteristique + " " + label;
    }
    
}
