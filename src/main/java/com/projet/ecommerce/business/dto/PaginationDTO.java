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

    public int getPageActuelle() {
        return pageActuelle;
    }

    public void setPageActuelle(int pageActuelle) {
        this.pageActuelle = pageActuelle;
    }

    public int getPageMin() {
        return pageMin;
    }

    public void setPageMin(int pageMin) {
        this.pageMin = pageMin;
    }

    public int getPageMax() {
        return pageMax;
    }

    public void setPageMax(int pageMax) {
        this.pageMax = pageMax;
    }

    public long getTotal() {
        return total;
    }

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
