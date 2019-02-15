package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;

/**
 * Interface permettant de communiquer avec la base de données pour la table Caractéristique.
 *
 */
@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, Integer> {
	

	/**
	 * Recherche les caracteristiques pour une ref produit donnée
	 * 
	 * @param refProduit la référence du produit
	 * @return une collection de caractéristiques.  Peut etre vide.
	 */
	Collection<Caracteristique> findByProduit_ReferenceProduit(String refProduit);

}
