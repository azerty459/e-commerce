package com.projet.ecommerce.business;

import com.projet.ecommerce.entity.Produit;

import java.util.List;

/**
 * Interface de ProduitBusiness
 */
public interface IProduitBusiness {
    /**
     * Méthode définissant l'ajout de produit.
     * @param referenceProduit Référence du produit
     * @param description Description
     * @param prixHT Prix hors taxe
     * @return le produit créé
     */
    Produit addProduit(String referenceProduit, String description, double prixHT);

    /**
     * Méthode définissant la modification d'un produit.
     * @param referenceProduit Référence du produit à modifier
     * @param description Description
     * @param prixHT Prix hors taxe
     * @return le produit modifié
     */
    Produit updateProduit(String referenceProduit, String description, double prixHT);

    /**
     * Méthode définissant la suppression d'un produit.
     * @param referenceProduit Référence du produit à supprimer
     * @return true
     */
    boolean deleteProduit(String referenceProduit);

    /**
     * Méthode définissant la recherche de tous les produits.
     * @return une liste de produit
     */
    List<Produit> getProduit();

    /**
     * Méthode définissant la recherche d'un produit selon la référence du produit recherché.
     * @param referenceProduit Référence du produit à rechercher
     * @return le produit rechercher
     */
    Produit getProduitByID(String referenceProduit);
}
