package com.projet.ecommerce.repository;

import com.projet.ecommerce.entity.Caracteristique;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface permettant de communiquer avec la base de données pour la table Caracteristique
 */

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, Integer> {

    List<Caracteristique> findAll();
}
