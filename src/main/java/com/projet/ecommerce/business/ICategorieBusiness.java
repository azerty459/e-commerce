package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface du service CategorieBusiness.
 */

public interface ICategorieBusiness {

    /**
     * Méthode allant chercher les catégories
     * @param nom le nom de la catégorie à aller chercher. "null" si on cherche à lister toutes les catégories.
     * @return liste des catégories recherchées
     */
    List<CategorieDTO> getCategorie(String nom);

    /**
     * Méthode définissant l'ajout d'une catégorie parent.
     *
     * @param nomCategorie Le nom de la catégorie
     * @return la catégorie crée
     */
    CategorieDTO addParent(String nomCategorie);


    /**
     * Méthode définissant l'ajout d'une catégorie enfant.
     *
     * @param nomCategorie Le nom de la catégorie
     * @param parent       Le parent de la catégorie à insérer
     * @return la catégorie crée
     */
    CategorieDTO addEnfant(String nomCategorie, String parent);

    /**
     * Méthode définissant le supprésion d'une catégorie.
     *
     * @param nomCategorie Nom de la catégorie à supprimer
     * @return true
     */
    boolean delete(String nomCategorie);

    /**
     * Méthode définissant la recherche de toutes les catégories.
     *
     * @return Une liste d'objet categorie
     */
    List<CategorieDTO> getAll();

    /**
     * Méthode définissant la recherche d'une categorie selon son nom.
     *
     * @param nomCategorie Le nom de la catégorie à rechercher
     * @return l'objet categorie recherché
     */
    CategorieDTO getByNom(String nomCategorie);


    /**
     * Méthode définissant la pagination
     * @param pageNumber le page souhaitée
     * @param nb le nombre de produit à afficher dans la page
     * @return une page de categorie
     */
    Page<Categorie> getPage(int pageNumber, int nb);
}
