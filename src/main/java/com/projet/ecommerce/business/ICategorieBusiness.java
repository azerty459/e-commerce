package com.projet.ecommerce.business;

import com.projet.ecommerce.entity.Categorie;

import java.util.List;

public interface ICategorieBusiness {
    /**
     * Méthode définissant l'ajout d'une catégorie.
     * @param categorie Un objet de type Categorie
     * @return l'objet categorie créer
     */
    Categorie addCategorie(Categorie categorie);

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
    List<Categorie> getCategorie();

    /**
     * Méthode définissant la recherche d'une photo selon l'id de la photo recherché.
     * @param nomCategorie Le nom de la catégorie à rechercher
     * @return l'objet categorie recherché
     */
    Categorie getCategorieByID(String nomCategorie);
}
