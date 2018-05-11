package com.projet.ecommerce.business.dto;


import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.List;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Pagination).
 */

public class PaginationDTO {

    private int pageActuelle;

    private int pageMin;

    private int pageMax;

    private long total;

    private List produits;

    private List categories;

    /**
     * Retourne la page où on ce trouve.
     * @return la page où on ce trouve
     */
    public int getPageActuelle() {
        return pageActuelle;
    }

    /**
     * Modifie la page où on se trouve par celle-ci mit en paramètre.
     * @param pageActuelle Le numéro de page où on souhaite se trouver.
     */
    public void setPageActuelle(int pageActuelle) {
        this.pageActuelle = pageActuelle;
    }

    /**
     * Retourne le nombre de page minimum affichable.
     * @return le nombre de page minimum affichable
     */
    public int getPageMin() {
        return pageMin;
    }

    /**
     * Modifie le nombre de page minimum affichable par celui-ci en paramètre.
     * @param pageMin Le nouveau nombre de page affichable
     */
    public void setPageMin(int pageMin) {
        this.pageMin = pageMin;
    }

    /**
     * Retourne le nombre de page maximum affichable.
     * @return le nombre de page maximum affichable.
     */
    public int getPageMax() {
        return pageMax;
    }

    /**
     * Modifie le nombre de page maximum affichable.
     * @param pageMax Le nouveau nombre de page affichable
     */
    public void setPageMax(int pageMax) {
        this.pageMax = pageMax;
    }

    /**
     * Retourne le nombre d'éléments total.
     * @return le nombre d'éléments
     */
    public long getTotal() {
        return total;
    }

    /**
     * Modifie le nombre d'élements total par celui-ci mit en paramètre.
     * @param total Le nouveau nombre d'éléments
     */
    public void setTotal(long total) {
        this.total = total;
    }

    public List getProduits() {
        return produits;
    }

    public void setProduits(List produits) {
        this.produits = produits;
    }

    public List getCategories() {
        return categories;
    }

    public void setCategories(List categories) {
        this.categories = categories;
    }
}
