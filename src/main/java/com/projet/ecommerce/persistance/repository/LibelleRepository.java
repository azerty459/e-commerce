package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Libelle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettant de communiquer avec la base de données pour la table Libellé.
 */

 //XXX - pourquoi toutes ces interfaces ne commencent pas par I ??
@Repository
public interface LibelleRepository extends CrudRepository<Libelle, Integer> {
    Collection<Libelle> findByNomIgnoreCase(String nom);

    //XXX - Contains vs Containing ??
    Collection<Libelle> findByNomContainingIgnoreCase(String motCle);
}
