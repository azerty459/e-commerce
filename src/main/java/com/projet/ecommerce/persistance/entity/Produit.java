package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.util.List;

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
    private String nom;

    @Column
    private String description;

    @Column(name = "prix_ht")
    private double prixHT;

    @ManyToMany
    @JoinTable(
            name = "produit_categorie",
            joinColumns = { @JoinColumn(name = "reference_produit") },
            inverseJoinColumns = { @JoinColumn(name = "nom_categorie") }
    )
    private List<Categorie> categories;

    @OneToMany(mappedBy="produit", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Caracteristique> caracteristiques;

    @OneToMany(mappedBy="produit", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
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

    /**
     *
     * @param categories
     */
    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    /**
     * Remplace la liste de caractéristiques par celui-ci mit en paramètre.
     * @param caracteristiques La nouvelle liste de caractéristique du produit
     */
    public void setCaracteristiques(List<Caracteristique> caracteristiques) {
        this.caracteristiques = caracteristiques;
    }

    /**
     * Remplace la liste de photos par celui-ci mit en paramètre.
     * @param photos La nouvelle liste de photo du produit
     */
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Retourne le nom du produit.
     * @return le nom du produit
     */
    public String getNom() {
        return nom;
    }

    /**
     * Remplace le nom du produit par celui-ci mit en paramètre.
     * @param nom Le nouveau nom du produit
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


}
