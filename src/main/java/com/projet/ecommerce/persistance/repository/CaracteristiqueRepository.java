package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.CaracteristiqueId;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettante de communiquer avec la base de données pour la table Caractéristique.
 */

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, CaracteristiqueId> {

    Collection<Caracteristique> findAllByOrderByTypeCaracteristique();

    Collection<Caracteristique> findByTypeCaracteristique(String type);

}
