package com.projet.ecommerce.business.dto;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;

import java.util.Collection;
import java.util.List;

public class ProduitDTO {

    private String referenceProduit;

    private String nom;

    private String description;

    private double prixHT;

    private List<CategorieDTO> categories;

    private List<CaracteristiqueDTO> caracteristiques;

    private List<PhotoDTO> photos;

    public String getReferenceProduit() {
        return referenceProduit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
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

    public double getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(double prixHT) {
        this.prixHT = prixHT;
    }

    public List<CategorieDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorieDTO> categories) {
        this.categories = categories;
    }

    public List<CaracteristiqueDTO> getCaracteristiques() {
        return caracteristiques;
    }

    public void setCaracteristiques(List<CaracteristiqueDTO> caracteristiques) {
        this.caracteristiques = caracteristiques;
    }

    public List<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos) {
        this.photos = photos;
    }
}
