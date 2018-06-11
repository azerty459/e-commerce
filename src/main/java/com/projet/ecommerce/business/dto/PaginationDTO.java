package com.projet.ecommerce.business.dto;


import java.util.List;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Pagination).
 */

public class PaginationDTO {

    private int pageActuelle;

    private int pageMin;

    private int pageMax;

    private long total;

    private List<ProduitDTO> produits;

    private List<CategorieDTO> categories;

    /**
     * Retourne la page où on est actuellement.
     *
     * @return la page où on ce trouve
     */
    public int getPageActuelle() {
        return pageActuelle;
    }

    /**
     * Modifie la page où on est en actuellement par celle-ci mis en paramètre.
     *
     * @param pageActuelle Le numéro de page où on souhaite se trouver.
     */
    public void setPageActuelle(int pageActuelle) {
        this.pageActuelle = pageActuelle;
    }

    /**
     * Retourne le nombre de page minimum affichable.
     *
     * @return le nombre de page minimum affichable
     */
    public int getPageMin() {
        return pageMin;
    }

    /**
     * Modifie le nombre de page minimum affichable par celui-ci mis en paramètre.
     *
     * @param pageMin Le nouveau nombre de page affichable
     */
    public void setPageMin(int pageMin) {
        this.pageMin = pageMin;
    }

    /**
     * Retourne le nombre de page maximum affichable.
     *
     * @return le nombre de page maximum affichable.
     */
    public int getPageMax() {
        return pageMax;
    }

    /**
     * Modifie le nombre de page maximum affichable.
     *
     * @param pageMax Le nouveau nombre de page affichable
     */
    public void setPageMax(int pageMax) {
        this.pageMax = pageMax;
    }

    /**
     * Retourne le nombre d'éléments total.
     *
     * @return le nombre d'éléments
     */
    public long getTotal() {
        return total;
    }

    /**
     * Modifie le nombre d'élements total par celui-ci mis en paramètre.
     *
     * @param total Le nouveau nombre d'éléments
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * Retourne une collection d'objet produit.
     *
     * @return une collection d'objet produit
     */
    public List<ProduitDTO> getProduits() {
        return produits;
    }

    /**
     * Modifie la collection de produit par celle-ci mis en pramètre.
     *
     * @param produits La nouvelle collection
     */
    public void setProduits(List<ProduitDTO> produits) {
        this.produits = produits;
    }

    /**
     * Retourne une collection d'objet catégorie.
     *
     * @return une collection d'objet catégorie.
     */
    public List<CategorieDTO> getCategories() {
        return categories;
    }

    /**
     * Modifie la collection de categorie par celle-ci mis en paramètres
     *
     * @param categories La nouvelle collection
     */
    public void setCategories(List<CategorieDTO> categories) {
        this.categories = categories;
    }
}
