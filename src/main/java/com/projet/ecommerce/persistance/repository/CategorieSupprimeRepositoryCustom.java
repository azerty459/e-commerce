package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.CategorieSupprime;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CategorieSupprimeRepositoryCustom {
    Collection<CategorieSupprime> findParents(Map<Integer, CategorieSupprime> cats);

    /**
     * Décale toutes les bornes supérieures à la borne gauche de cat vers la droite
     * Le but est d'inser une ou plusieurs catégories.
     *
     * @param bg       la catégorie de référence pour la borne gauche
     * @param decalage le nombre d'indices à décaler
     */
    void ecarterBornes(int bg, int decalage);


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
