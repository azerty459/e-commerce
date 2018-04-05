package com.projet.ecommerce.dto;

import com.projet.ecommerce.entity.Categorie;
import com.projet.ecommerce.entity.Produit;

import javax.persistence.*;
import java.util.List;

public class CategorieDTO {

    private String nomCategorie;
    private List<Categorie> sousCategories;

    public CategorieDTO() {}

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public List<Categorie> getSousCategories() {
        return sousCategories;
    }

    public void setSousCategories(List<Categorie> sousCategories) {
        this.sousCategories = sousCategories;
    }
}
