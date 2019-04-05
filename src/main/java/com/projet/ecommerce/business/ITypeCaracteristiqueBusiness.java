package com.projet.ecommerce.business;

import java.util.Collection;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;

/**
 * Interface du service TypeCaracteristiqueBusiness.
 */

public interface ITypeCaracteristiqueBusiness {

	/**
	 * Méthode retournant les types de caractéristiques des produit
	 * 
	 * @return une liste de tous les types de caratéristiques des produits possibles
	 */
	Collection<TypeCaracteristiqueDTO> getToutLesTypesCaracteristiques();
	
	/**
	 * Méthode permettant l'ajout d'un type de caractéristique
	 * 
	 * @param nomType	le nom du type de carcatéristique
	 * @return Le type de caractéristique ajouté
	 */
	TypeCaracteristiqueDTO ajouterTypeCaractéristique(String nomType);
	
	/**
	 * Méthode permettant de modifié un type de caractéristique
	 * 
	 * @param typeCaracteristique	l'objet TypeCaracteristique à modifié
	 * @return le type de caractéristique modifié
	 */
	TypeCaracteristiqueDTO modifierTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristiqueDto);
	
	/**
	 * Méthode permettant de supprimer un type de caractéristique
	 * 
	 * @param typeCaracteristique	le type de caractéristique à supprimer
	 */
	void supprimerTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristique);
}
