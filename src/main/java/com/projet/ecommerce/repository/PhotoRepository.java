package com.projet.ecommerce.repository;

import com.projet.ecommerce.entity.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Interface permettant de communiquer avec la base de données pour la table Photo.
 */

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Integer> {

    Collection<Photo> findAll();
}
