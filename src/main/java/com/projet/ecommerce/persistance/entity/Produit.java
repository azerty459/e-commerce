package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    //TODO regarder cascade.... est ce correct?
    @OneToMany(mappedBy = "produit", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Caracteristique> caracteristiques;


    // Getters et setters
    public String getReferenceProduit() {
        return referenceProduit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(float prixHT) {
        this.prixHT = prixHT;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public Photo getPhotoPrincipale() {
        return photoPrincipale;
    }

    public void setPhotoPrincipale(Photo photoPrincipale) {
        this.photoPrincipale = photoPrincipale;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<AvisClient> getAvisClients() {
        return avisClients;
    }

    public void setAvisClients(List<AvisClient> avisClients) {
        this.avisClients = avisClients;
    }

    public List<Categorie> getCategoriesSupprime() {
        return categoriesSupprime;
    }

    public void setCategoriesSupprime(List<Categorie> categoriesSupprime) {
        this.categoriesSupprime = categoriesSupprime;
    }

    public List<Caracteristique> getCaracteristiques() {
        return caracteristiques;
    }

    public void setCaracteristiques(List<Caracteristique> caracteristiques) {
        this.caracteristiques = caracteristiques;
    }    
}
