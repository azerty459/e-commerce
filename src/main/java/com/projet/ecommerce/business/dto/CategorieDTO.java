package com.projet.ecommerce.business.dto;

import java.util.List;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Categorie).
 */

public class CategorieDTO {

    private int id;

    private String nom;

    private List<CategorieDTO> sousCategories;


    // US#192 - DEBUT
    /**
     * Liste des parents de la catégories.
     */
    private List<CategorieDTO> parents;

     /**
     * Niveau de la catégorie dans l'arborescence des catégories (Niveau 1 = niveau le plus élevé).
     */
    private int level;
    // US#192 - FIN

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

    /**
     * Retourne l'id de la catégorie.
     * @return l'id de la catégorie
     */
    public int getId() {
        return id;
    }

    /**
     * Remplace l'id par celle-ci mit en paramètre.
     * @param id La nouvelle id
     */
    public void setId(int id) {
        this.id = id;
    }

    // US#192 - DEBUT

    public List<CategorieDTO> getParents() {
        return this.parents;
    }

    public void setParents(List<CategorieDTO> p) {
        this.parents = p;
    }

    public int getLevel() { return this.level; }

    public void setLevel(int l) { this.level = l; }

    // US#192 - FIN
}
