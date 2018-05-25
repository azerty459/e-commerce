package com.projet.ecommerce.business.dto;

import java.util.List;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Categorie).
 */
public class CategorieDTO {

    /**
     * L'id de la catégorie.
     */
    private int id;

    /**
     * Le nom de la catégorie.
     */
    private String nom;

    /**
     * La liste des sous-catégories de la catégorie.
     */
    private List<CategorieDTO> sousCategories;

    /**
     * Liste des parents de la catégories.
     */
    private String chemin;

     /**
     * Niveau de la catégorie dans l'arborescence des catégories (Niveau 1 = niveau le plus élevé).
     */
    private int level;

    /**
     * Le parent direct de la catégorie
     */
    private CategorieDTO parent;

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

    /**
     * Retourne le chemin pour arriver à la catégorie depuis la catégorie parente de niveau 1
     * @return une chaîne de caractères représentant ce chemin.
     */
    public String getChemin() {
        return this.chemin;
    }

    /**
     * Fixe le chemin de la catégorie.
     * @param c la chaîne de caractères représentant le chemin.
     */
    public void setChemin(String c) {
        this.chemin = c;
    }

    /**
     * Retourne le niveau de la catégorie dans l'arborescence des catégories.
     * @return le niveau (1 = catégorie de niveau 1, tout en haut de l'arborescence).
     */
    public int getLevel() { return this.level; }

    /**
     * Fixe le niveau de la catégorie.
     * @param l le niveau de la catégorie.
     */
    public void setLevel(int l) { this.level = l; }

    /**
     * Retourne le parent direct de la catégorie
     * @return le CategorieDTO représentant le parent
     */
    public CategorieDTO getParent() {
        return this.parent;
    }

    /**
     * Fixe le parent de la catégorie
     * @param p le parent
     */
    public void setParent(CategorieDTO p) {
        this.parent = p;
    }


}
