package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettante de communiquer avec la base de données pour la table Catégorie.
 */

@Repository
public interface CategorieRepository extends PagingAndSortingRepository<Categorie, String>, CategorieRepositoryCustom {

    Collection<Categorie> findAll();
}
