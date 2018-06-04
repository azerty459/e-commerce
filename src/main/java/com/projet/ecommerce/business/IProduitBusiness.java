package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.persistance.entity.Produit;
import org.springframework.data.domain.Page;

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
    ProduitDTO add(String referenceProduit, String nom, String description, float prixHT, List<Integer> nouvelleCat);

    /**
     * Méthode définissant la modification d'un produit.
     *
     * @param referenceProduit La référence du produit à modifier
     * @param nom              Le nouveau nom
     * @param description      La nouvelle description
     * @param prixHT           Le nouveau prix hors taxe
     * @param idCatAssocier    ID de la catégorie à associer au produit
     * @param supprimerCatAssocier ID de la catégorie à supprimer de l'association au produit
     * @return l'objet produit modifié, null si le produit à modifier n'est pas trouvée
     */
    ProduitDTO update(Produit produit);

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
     * Méthode définissant la recherche des produits selon les paramètres ci-dessous
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
     * Méthode définissant la pagination
     * @param pageNumber la page souhaitée
     * @param nb le nombre de produit à afficher dans la page
     * @return une page de produit
     */
    Page<Produit> getPage(int pageNumber, int nb);

}
