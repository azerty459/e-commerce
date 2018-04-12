package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettant de communiquer avec la base de données pour la table TypeCaracteristique.
 */

@Repository
public interface TypeCaracteristiqueRepository extends CrudRepository<TypeCaracteristique, Integer> {

    Collection<TypeCaracteristique> findAll();
}
