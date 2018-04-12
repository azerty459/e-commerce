package com.projet.ecommerce.business.dto;

import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.List;

public class CategorieDTO {

    private String nom;

    private List<CategorieDTO> sousCategories;

    public CategorieDTO() {}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<CategorieDTO> getSousCategories() {
        return sousCategories;
    }

    public void setSousCategories(List<CategorieDTO> sousCategories) {
        this.sousCategories = sousCategories;
    }
}
