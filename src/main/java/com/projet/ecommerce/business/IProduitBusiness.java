package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.ProduitDTO;

import java.util.List;

/**
 * Interface du service ProduitBusiness.
 */

public interface IProduitBusiness {

    /**
     * Méthode définissant la modification d'un produit.
     *
     * @param referenceProduit La référence du produit
     * @param nom              Le nom du produit
     * @param description      Sa description
     * @param prixHT           Son prix hors taxe
     * @return le produit ajouté
     */
    ProduitDTO add(String referenceProduit, String nom, String description, Float prixHT);

    /**
     * Méthode définissant la modification d'un produit.
     *
     * @param referenceProduit La référence du produit à modifier
     * @param nom              Le nouveau nom
     * @param description      La nouvelle description
     * @param prixHT           Le nouveau prix hors taxe
     * @return l'objet produit modifié, null si le produit à modifier n'est pas trouvée
     */
    ProduitDTO update(String referenceProduit, String nom, String description, Float prixHT);

    /**
     * Méthode définissant la suppression d'un produit.
     *
     * @param referenceProduit Référence du produit à supprimer
     * @return true
     */
    boolean delete(String referenceProduit);

    /**
     * Méthode définissant la recherche de tous les produits.
     *
     * @return une liste de produit
     */
    List<ProduitDTO> getAll();





    /**
     * A FAIRE *******************************************************************
     * @param ref la référence du produit recherché
     * @param cat la catégorie du /des produit(s) recherché(s)
     * @return la liste des produits trouvés
     */
    List<ProduitDTO> getAll(String ref, String cat);




    /**
     * Méthode définissant la recherche d'un produit selon la référence du produit recherché.
     *
     * @param referenceProduit Référence du produit à rechercher
     * @return le produit rechercher
     */
    ProduitDTO getByRef(String referenceProduit);

    /**
     * Méthode définissant la recherche de plusieurs produit par le nom d'une catégorie.
     */
    List<ProduitDTO> getByCategorie(String nomCategorie);
}
