package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.Collection;
import java.util.Map;

public interface CategorieRepositoryCustom {

    Collection<Categorie> findParents(Map<Integer, Categorie> cats);

    Categorie findDirectParent(Categorie cat);

    /**
     * Décale toutes les bornes supérieures à la borne gauche de cat vers la droite
     * Le but est d'inser une ou plusieurs catégories.
     *
     * @param cat      la catégorie de référence pour la borne gauche
     * @param decalage le nombre d'indices à décaler
     */
    void ecarterBornes(Categorie cat, int decalage);


    /**
     * Réarranger les bornes pour éviter qu'il y ait des trous après suppression ou déplacement d'une catégorie
     *
     * @param bg         borne gauche de la catégorie supprimée
     * @param bd         borne droite de la catégorie supprimée
     * @param intervalle intervalle entre les 2
     * @return ne nombre de catégories réorganisées
     */
    int rearrangerBornes(int bg, int bd, int intervalle);

    /**
     * Trouve la borne max de toute la base de données
     *
     * @return la borne maximale
     */
    int findBorneMax();

    /**
     * Change les bornes gauches et droites de la catégorie d'id idCategorie et les déplace de deplacement
     *
     * @param idCategorie id de la catégorie dont on change les bornes
     * @param deplacement intervalle de déplacement de ces bornes
     */
    void changerBornes(int idCategorie, int deplacement);

    /**
     * Change le level d'une catégorie dont l'id est donnée en paramètre
     *
     * @param idCategorie  id de la catégorie dont on modifie le level
     * @param nouveauLevel nouvelle valeur du level
     */
    void changerLevel(int idCategorie, int nouveauLevel);

}
