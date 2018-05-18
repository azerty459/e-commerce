package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettant de communiquer avec la base de données pour la table Photo.
 */

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Integer>, PhotoRepositoryCustom {

    Collection<Photo> findAll();
}
