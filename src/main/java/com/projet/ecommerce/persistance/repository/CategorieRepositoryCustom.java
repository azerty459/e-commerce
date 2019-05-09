package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CategorieRepositoryCustom {

	Collection<Categorie> findParents(Map<Integer, Categorie> cats);

	Categorie findDirectParent(Categorie cat);

	/**
	 * Décale toutes les bornes supérieures à la borne gauche de cat vers la droite
	 * Le but est d'inser une ou plusieurs catégories.
	 *
	 * @param bg       la catégorie de référence pour la borne gauche
	 * @param decalage le nombre d'indices à décaler
	 */
	void ecarterBornes(int bg, int decalage);


	/**
	 * Réarranger les bornes pour éviter qu'il y ait des trous après suppression ou déplacement d'une catégorie
	 *
	 * @param bg         borne gauche de la catégorie supprimée
	 * @param intervalle intervalle entre les 2
	 * @return ne nombre de catégories réorganisées
	 */
	int rearrangerBornes(int bg, int intervalle);

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

	void recolleCategories();

}
