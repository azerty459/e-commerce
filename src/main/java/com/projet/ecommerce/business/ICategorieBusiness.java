package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Interface du service CategorieBusiness.
 */

public interface ICategorieBusiness {

	/**
	 * Méthode allant chercher les catégories
	 *
	 * @param id  l'id de la catégorie à rechercher
	 * @param nom le nom de la catégorie à aller chercher. "null" si on cherche à lister toutes les catégories.
	 * @return liste des catégories recherchées
	 */
	List<CategorieDTO> getCategorie(int id, String nom, boolean sousCategorie, boolean parent);

	/**
	 * Construit un HashMap associant chaque catégorie à un chemin représentant l'arborescence jusqu'à la catégorie.
	 *
	 * @param categories la collection des catégories dont on cheche les chemins.
	 * @return Un HashMap associant chaque catégorie à une chaîne de caractère représentant le chemin.
	 */
	HashMap<Categorie, Collection<Categorie>> construireAssociationEnfantsChemins(Collection<Categorie> categories);

	/**
	 * rearrangerBornes
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
	 * @param idParent     L'ID parent de la catégorie
	 * @return la catégorie crée
	 */
	CategorieDTO addEnfant(String nomCategorie, int idParent);

	/**
	 * Méthode définissant le supprésion d'une catégorie.
	 *
	 * @param id ID de la catégorie à supprimer
	 * @return true
	 */
	boolean delete(int id);

	/**
	 * Déplace une catégorie (et ses enfants) d'un parent à un autre
	 *
	 * @param idADeplacer  id de la catégorie à déplacer
	 * @param idReceptrice id du nouveau parent
	 * @return false si le client tente d'effectuer un déplacement null, true sinon
	 */
	boolean moveCategorie(int idADeplacer, int idReceptrice);

	/**
	 * Méthode permettant de modifier le nom d'une catégorie.
	 *
	 * @param id  l'id de la catégorie à modifier.
	 * @param nom le nouveau nom de la catégorie
	 * @return la catégorie modifiée ou une exception GraphQL si elle n'a pas été trouvée.
	 */
	CategorieDTO updateCategorie(int id, String nom);

	/**
	 * Méthode définissant la pagination
	 *
	 * @param pageNumber le page souhaitée
	 * @param nb         le nombre de produit à afficher dans la page
	 * @return une page de categorie
	 */
	Page<Categorie> getPage(int pageNumber, int nb);

	/**
	 * Retourne le nombre de categories
	 *
	 * @return le nombre de categories
	 */
	Long countCategories();

}
