package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.Collection;
import java.util.HashMap;

public interface CategorieRepositoryCustom {

    Collection<Categorie> findAllWithCriteria(int id, String nom, Boolean sousCat);

    Collection<Categorie> findParents(HashMap<Integer,Categorie> cats);

    Categorie findDirectParent(Categorie cat);

    /**
     * Décale toutes les bornes supérieures à la borne gauche de cat vers la droite
     * Le but est d'inser une ou plusieurs catégories.
     * @param cat la catégorie de référence pour la borne gauche
     * @param decalage le nombre d'indices à décaler
     */
    void ecarterBornes(Categorie cat, int decalage);


    /**
     * Réarranger les bornes pour éviter qu'il y ait des trous après suppression ou déplacement d'une catégorie
     * @param bg borne gauche de la catégorie supprimée
     * @param bd borne droite de la catégorie supprimée
     * @param intervalle intervalle entre les 2
     * @return ne nombre de catégories réorganisées
     */
    int rearrangerBornes(int bg, int bd, int intervalle);

    /**
     * Trouve la borne max de toute la base de données
     * @return la borne maximale
     */
    int findBorneMax();

}
