package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Entité représentant la table produit sous forme de classe.
 */

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @Column(name = "reference_produit")
    private String referenceProduit;

    /**
     * La date et l'heure d'ajout du produit dans la base de données (auto-généré par PostgreSQL)
     */
    @Column(name = "date_ajout", updatable = false, insertable = false)
    private LocalDateTime dateAjout;

    @Column
    private String nom;

    @Column
    private String description;

    @Column(name = "prix_ht")
    private float prixHT;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "produit_categorie",
            joinColumns = {@JoinColumn(name = "reference_produit")},
            inverseJoinColumns = {@JoinColumn(name = "id_categorie")}
    )
    private List<Categorie> categories;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<CaracteristiqueAssociated> caracteristiqueAssociated;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_principale")
    private Photo photoPrincipale;

    @OneToMany(mappedBy = "produit", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Photo> photos;

    @OneToMany(mappedBy = "produit", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<AvisClient> avisClients;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "produit_categorie_supprime",
            joinColumns = {@JoinColumn(name = "reference_produit")},
            inverseJoinColumns = {@JoinColumn(name = "id_categorie")}
    )
    private List<Categorie> categoriesSupprime;

    /**
     * Retourne la référence du produit.
     *
     * @return la référence du produit
     */
    public String getReferenceProduit() {
        return referenceProduit;
    }

    /**
     * Remplace la référence du produit par celui-ci mit en paramètre.
     *
     * @param referenceProduit La nouvelle référence du produit
     */
    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    /**
     * Retourne la description lié au produit.
     *
     * @return la description du produit
     */
    public String getDescription() {
        return description;
    }

    /**
     * Remplace la description du produit par celle-ci mit en paramètre.
     *
     * @param description La nouvelle description du produit
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retourne le prix hors taxe du produit.
     *
     * @return le prix hors taxe du produit
     */
    public float getPrixHT() {
        return prixHT;
    }

    /**
     * Remplace le prix hors taxe du produit par celui-ci mit en paramètre.
     *
     * @param prixHT Le nouveau prix hors taxe
     */
    public void setPrixHT(float prixHT) {
        this.prixHT = prixHT;
    }

    /**
     * Retourne une liste de catégories liées à ce produit.
     *
     * @return une liste de catégories liées à ce produit
     */
    public List<Categorie> getCategories() {
        return categories;
    }

    /**
     * Retourne une liste de photos liées à ce produit.
     *
     * @return une liste de photos liées à ce produit
     */
    public List<Photo> getPhotos() {
        return photos;
    }

    public List<AvisClient> getAvisClients() {
        return avisClients;
    }


    public void setAvisClients(List<AvisClient> avisClients) {
        this.avisClients = avisClients;
    }

    /**
     * Remplace la liste de catégorie par celui-ci mit en paramètre
     *
     * @param categories
     */
    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    /**
     * Remplace la liste de photos par celui-ci mit en paramètre.
     *
     * @param photos La nouvelle liste de photo du produit
     */
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Retourne le nom du produit.
     *
     * @return le nom du produit
     */
    public String getNom() {
        return nom;
    }

    /**
     * Remplace le nom du produit par celui-ci mit en paramètre.
     *
     * @param nom Le nouveau nom du produit
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Donne la date et l'heure d'ajout d'un produit
     *
     * @return la date / heure d'ajout
     */
    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    /**
     * Fixe la date et l'heure d'ajout d'un produit.
     *
     * @param dateAjout la date / heure d'ajout d'un produit
     */
    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }

    /**
     * Permet d'obtenir la photo principale
     *
     * @return
     */
    public Photo getPhotoPrincipale() {
        return photoPrincipale;
    }

    /**
     * Permet de définir la photo principale
     *
     * @return
     */
    public void setPhotoPrincipale(Photo photoPrincipale) {
        this.photoPrincipale = photoPrincipale;
    }

    /**
     * Permet d'obtenir les caractéristiques associées
     *
     * @return la caractéristiques associées
     */
    public List<CaracteristiqueAssociated> getCaracteristiqueAssociated() {
        return caracteristiqueAssociated;
    }

    /**
     * Permet de définir les caractéristiques associées
     *
     * @param caracteristiqueAssociated les caractéristiques associées à set
     */
    public void setCaracteristiqueAssociated(List<CaracteristiqueAssociated> caracteristiqueAssociated) {
        this.caracteristiqueAssociated = caracteristiqueAssociated;
    }
}
