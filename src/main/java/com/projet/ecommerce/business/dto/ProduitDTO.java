package com.projet.ecommerce.business.dto;

import java.util.List;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Produit).
 */
public class ProduitDTO {

    /**
     * la référence du produit
     */
    private String ref;

    /**
     * le nom du produit
     */
    private String nom;

    /**
     * description du produit
     */
    private String description;

    /**
     * le prix hors taxes du produit
     */
    private float prixHT;

    /**
     * la liste des catégories auxquelles appartient le produit
     */
    private List<CategorieDTO> categories;

    /**
     * la liste des photos du produit
     */
    private List<PhotoDTO> photos;


    /**
     * La photo pricnipale
     */
    private PhotoDTO photoPrincipale;

    private List<CaracteristiqueDTO> caracteristiques;

    /**
     * Obtenir la référence du produit
     *
     * @return la référence du produit
     */
    public String getRef() {
        return ref;
    }

    /**
     * Définir la référence du produit
     *
     * @param ref la référence du produit
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     * Obtenir le nom du produit
     *
     * @return le nom du produit
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définir le nom du produit
     *
     * @param nom le nom du produit
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtenir la description du produit
     *
     * @return la description du produit
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définir la description du produit
     *
     * @param description la description du produit
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtenir le prix hors taxes du produit
     *
     * @return le prix hors taxes du produit
     */
    public float getPrixHT() {
        return prixHT;
    }

    /**
     * Définir le prix hors taxes du produit
     *
     * @param prixHT le prix hors taxes du produit
     */
    public void setPrixHT(float prixHT) {
        this.prixHT = prixHT;
    }

    /**
     * Obtenir les catégories aux quelles appartient le produit
     *
     * @return la liste des catégories auxquelles appartient le produit
     */
    public List<CategorieDTO> getCategories() {
        return categories;
    }

    /**
     * Définir les catégories auxquelles appartient le produit
     *
     * @param categories la liste des catégories auxquelles appartient le produit
     */
    public void setCategories(List<CategorieDTO> categories) {
        this.categories = categories;
    }

    /**
     * Obtenir les photos du produit
     *
     * @return la liste des photos du produit
     */
    public List<PhotoDTO> getPhotos() {
        return photos;
    }

    /**
     * Définir les photos du produit
     *
     * @param photos la liste des photos du produit
     */
    public void setPhotos(List<PhotoDTO> photos) {
        this.photos = photos;
    }

    /**
     * Permet d'obtenir la photo principale DTO
     *
     * @return
     */
    public PhotoDTO getPhotoPrincipale() {
        return photoPrincipale;
    }

    /**
     * Permet de définir la photo principale DTO
     *
     * @param photoPrincipale
     */
    public void setPhotoPrincipale(PhotoDTO photoPrincipale) {
        this.photoPrincipale = photoPrincipale;
    }

    public List<CaracteristiqueDTO> getCaracteristiques() {
        return caracteristiques;
    }

    public void setCaracteristiques(List<CaracteristiqueDTO> caracteristiques) {
        this.caracteristiques = caracteristiques;
    }
}
