package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.entity.Photo;

/**
 * Interface permettant de communiquer avec la base de données pour la table Caracteristique.
 */

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Photo, Integer> {
	
	@Override
    Collection<Photo> findAll();

    Collection<Photo> findByProduit_ReferenceProduit(String ref);
}
