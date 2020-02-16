package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Libelle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface permettant de communiquer avec la base de données pour la table Libellé.
 */

 //XXX - pourquoi toutes ces interfaces ne commencent pas par I ??
@Repository
public interface LibelleRepository extends CrudRepository<Libelle, Integer>, LibelleRepositoryCustom {
}
