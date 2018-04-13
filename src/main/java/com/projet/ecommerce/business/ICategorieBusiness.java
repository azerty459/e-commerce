package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.Collection;
import java.util.List;

public interface ICategorieBusiness {

    /**
     * Méthode définissant l'ajout d'une catégorie parent.
     * @param nomCategorie Le nom de la catégorie
     * @return la catégorie crée
     */
    CategorieDTO addParent(String nomCategorie);


    /**
     * Méthode définissant l'ajout d'une catégorie enfant.
     * @param nomCategorie Le nom de la catégorie
     * @param parent Le parent de la catégorie à insérer
     * @return la catégorie crée
     */
    CategorieDTO addEnfant(String nomCategorie, String parent);

    /**
     * Méthode définissant le supprésion d'une catégorie.
     * @param nomCategorie Nom de la catégorie à supprimer
     * @return true
     */
    boolean delete(String nomCategorie);

    /**
     * Méthode définissant la recherche de toutes les catégories.
     * @return Une liste d'objet categorie
     */
    List<CategorieDTO> getAll();

    /**
     * Méthode définissant la recherche d'une categorie selon son nom.
     * @param nomCategorie Le nom de la catégorie à rechercher
     * @return l'objet categorie recherché
     */
    CategorieDTO getByNom(String nomCategorie);
}
