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
     * @param referenceProduit La référence du produit
     * @param nom Le nom du produit
     * @param description Sa description
     * @param prixHT Son prix hors taxe
     * @return le produit ajouté
     */
    ProduitDTO addProduit(String referenceProduit, String nom, String description, Float prixHT);

    /**
     * Méthode définissant la modification d'un produit.
     * @param referenceProduit La référence du produit à modifier
     * @param nom Le nouveau nom
     * @param description La nouvelle description
     * @param prixHT Le nouveau prix hors taxe
     * @return l'objet produit modifié, null si le produit à modifier n'est pas trouvée
     */
    ProduitDTO updateProduit(String referenceProduit, String nom, String description, Float prixHT);

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
    ProduitDTO getProduitByRef(String referenceProduit);

    /**
     * Méthode définissant la recherche de plusieurs produit par le nom d'une catégorie.
     */
    List<ProduitDTO> getProduitByCategorie(String nomCategorie);
}
