package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;

/**
 * Interface permettant de communiquer avec la base de données pour la table Caracteristique.
 */

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, Integer> {
	
	@Override
    Collection<Caracteristique> findAll();
}
