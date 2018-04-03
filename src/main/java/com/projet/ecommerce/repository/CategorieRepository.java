package com.projet.ecommerce.repository;

import com.projet.ecommerce.entity.Categorie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface permettante de communiquer avec la base de données pour la table Catégorie.
 */

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, String> {

    List<Categorie> findAll();
}
