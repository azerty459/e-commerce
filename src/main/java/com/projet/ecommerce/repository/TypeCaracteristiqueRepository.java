package com.projet.ecommerce.repository;

import com.projet.ecommerce.entity.TypeCaracteristique;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface permettant de communiquer avec la base de données pour la table TypeCaracteristique.
 */

@Repository
public interface TypeCaracteristiqueRepository extends CrudRepository<TypeCaracteristique, Integer> {

    List<TypeCaracteristique> findAll();
}
