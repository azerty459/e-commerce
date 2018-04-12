package com.projet.ecommerce.business.dto;

import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.List;

public class CategorieDTO {

    private String nom;

    private List<CategorieDTO> sousCategories;

    /**
     * Retourne le nom de la categorieDTO.
     * @return le nom de la categorie.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Remplace le nom de la CategorieDTO par celui-ci mit en paramètre.
     * @param nom Le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne une liste de CategorieDTO.
     * @return une liste de CategorieDTO.
     */
    public List<CategorieDTO> getSousCategories() {
        return sousCategories;
    }

    /**
     * Remplace la liste de CategorieDTO par celle-ci mit en paramètre.
     * @param sousCategories Une liste de CategorieDTO
     */
    public void setSousCategories(List<CategorieDTO> sousCategories) {
        this.sousCategories = sousCategories;
    }
}
