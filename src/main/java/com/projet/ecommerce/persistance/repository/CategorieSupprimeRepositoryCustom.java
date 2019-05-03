package com.projet.ecommerce.persistance.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.projet.ecommerce.persistance.entity.CategorieSupprime;

public interface CategorieSupprimeRepositoryCustom {

    /**
     * Récupérer les catégories parents de la catégorie de nom donné en paramètre
     *
     * @param cats les catégories dont on doit rechercher les parents
     * @return une collection des catégories parents de cette catégorie
     */
    Collection<CategorieSupprime> findParents(Map<Integer, CategorieSupprime> cats);


    /**
     * Trouve la borne max de toute la base de données
     *
     * @return la borne maximale
     */
    int findBorneMax();

    /**
     * Change les bornes des catégories à déplacer ainsi que leurs levels.
     * Effectue réellement le déplacement.
     *
     * @param ids                     ids des catégories à déplacer.
     * @param intervalleDeDeplacement Distance de déplacement pour les bornes.
     * @param intervalLevel           Intervalle de déplacement du level.
     */
    void changerBornesEtLevel(List<Integer> ids, int intervalleDeDeplacement, int intervalLevel);

}
