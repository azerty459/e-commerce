package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.primarykey.CaracteristiqueID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface permettant de communiquer avec la base de données pour la table Caractéristique.
 */

@Repository
public interface CaracteristiqueRepository extends CrudRepository<Caracteristique, CaracteristiqueID> {
}
