package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.Collection;
import java.util.List;

public interface ICategorieBusiness {

    /**
     * Méthode définissant l'ajout d'une catégorie.
     * @param categorieDTO Un objet de type CategorieDTO
     * @return l'objet categorie créer
     */
    CategorieDTO addCategorie(CategorieDTO categorieDTO);

    /**
     * Méthode définissant le supprésion d'une catégorie.
     * @param nomCategorie Nom de la catégorie à supprimer
     * @return true
     */
    boolean deleteCategorie(String nomCategorie);

    /**
     * Méthode définissant la recherche de toutes les catégories.
     * @return Une liste d'objet categorie
     */
    List<CategorieDTO> getCategorie();

    /**
     * Méthode définissant la recherche d'une photo selon l'id de la photo recherché.
     * @param nomCategorie Le nom de la catégorie à rechercher
     * @return l'objet categorie recherché
     */
    CategorieDTO getCategorieByID(String nomCategorie);
}
