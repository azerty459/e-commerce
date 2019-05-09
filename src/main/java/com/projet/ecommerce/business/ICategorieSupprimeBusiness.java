package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;

import java.util.List;

/**
 * Interface du service CategorieBusiness.
 */

public interface ICategorieSupprimeBusiness {

	/**
	 * Methode permettant de transfert une catégorie dans la table catégorie supprimé.
	 *
	 * @param categorieToSave la catégorie a supprimé
	 * @return vrai si le transfert est un succès, faux sinon.
	 */
	boolean saveInCategorieSupprime(Categorie categorieToSave);

	/**
	 * Methode effectuant les appels permettant de restaurer une categorie
	 * notament le deplacement de la categorie supprime une fois insérer dans la table categorie
	 *
	 * @param nouveauParent id de la categorie parent de la categorie a restaurer
	 * @return l'id de la categorie restauré ou 0 si la categorie n'a pas pu être restauré
	 */
	List<CategorieDTO> restoreLastDeletedCategorie(int nouveauParent);

	/**
	 * Methode permettant de deplacer les bornes des categories supprimés afin preparer l'insertion dans la table categorie
	 * puis l'insére dans la table categorie
	 *
	 * @return l'id de la catégorie une fois insérer dans la table categorie
	 */
	int moveCategorieSupprime();

}
