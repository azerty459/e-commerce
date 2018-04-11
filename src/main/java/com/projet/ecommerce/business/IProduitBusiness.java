package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.List;

/**
 * Interface de ProduitBusiness
 */
public interface IProduitBusiness {

    /**
     * Méthode définissant la modification d'un produit.
     * @param produitDTO Objet ProduitDTO
     * @return le produit modifié
     */
    ProduitDTO addProduit(ProduitDTO produitDTO);

    /**
     * Méthode définissant la modification d'un produit.
     * @param produitDTO Objet ProduitDTO
     * @return le produit modifié
     */
    ProduitDTO updateProduit(ProduitDTO produitDTO);

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
    List<ProduitDTO> getProduit();

    /**
     * Méthode définissant la recherche d'un produit selon la référence du produit recherché.
     * @param referenceProduit Référence du produit à rechercher
     * @return le produit rechercher
     */
    ProduitDTO getProduitByID(String referenceProduit);

    /**
     * Méthode définissant la recherche de plusieurs produit par le nom d'une catégorie.
     */
    List<ProduitDTO> getProduitByCategorie(String nomCategorie);
}
