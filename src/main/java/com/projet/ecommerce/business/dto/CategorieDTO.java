package com.projet.ecommerce.business.dto;

import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.List;

public class CategorieDTO {

    private String nomCategorie;

    private List<CategorieDTO> sousCategories;

    public CategorieDTO() {}

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public List<CategorieDTO> getSousCategories() {
        return sousCategories;
    }

    public void setSousCategories(List<CategorieDTO> sousCategories) {
        this.sousCategories = sousCategories;
    }
}
