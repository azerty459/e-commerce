package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettant de communiquer avec la base de données pour la table Caracteristique
 */

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, Integer> {

    Collection<Caracteristique> findAll();
}
