package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettante de communiquer avec la base de données pour la table Catégorie.
 */

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, String> {

    Collection<Categorie> findAll();
}