package com.projet.ecommerce.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Entité représentant la table produit sous forme de classe.
 */

@Entity
@Table(name="produit")
public class Produit {
    @Id
    @Column(name = "reference_produit")
    private String referenceProduit;

    @Column
    private String description;

    @Column(name = "prix_HT")
    private double prixHT;

    @ManyToMany
    @JoinTable(
            name = "produit_categorie",
            joinColumns = { @JoinColumn(name = "reference_produit") },
            inverseJoinColumns = { @JoinColumn(name = "nom_categorie") }
    )
    private List<Categorie> categories;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Caracteristique> caracteristiques;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Photo> photos;

    /**
     * Retourne la référence du produit.
     * @return la référence du produit
     */
    public String getReferenceProduit() {
        return referenceProduit;
    }

    /**
     * Remplace la référence du produit par celui-ci mit en paramètre.
     * @param referenceProduit La nouvelle référence du produit
     */
    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    /**
     * Retourne la description lié au produit.
     * @return la description du produit
     */
    public String getDescription() {
        return description;
    }

    /**
     * Remplace la description du produit par celle-ci mit en paramètre.
     * @param description La nouvelle description du produit
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retourne le prix hors taxe du produit.
     * @return le prix hors taxe du produit
     */
    public double getPrixHT() {
        return prixHT;
    }

    /**
     * Remplace le prix hors taxe du produit par celui-ci mit en paramètre.
     * @param prixHT Le nouveau prix hors taxe
     */
    public void setPrixHT(double prixHT) {
        this.prixHT = prixHT;
    }

    /**
     * Retourne une liste de catégories liées à ce produit.
     * @return une liste de catégories liées à ce produit
     */
    public List<Categorie> getCategories() {
        return categories;
    }

    /**
     * Retourne une liste de caratéristiques liées à ce produit.
     * @return une liste de caratéristiques liées à ce produit
     */
    public List<Caracteristique> getCaracteristiques() {
        return caracteristiques;
    }

    /**
     * Retourne une liste de photos liées à ce produit.
     * @return une liste de photos liées à ce produit
     */
    public List<Photo> getPhotos() {
        return photos;
    }
}
