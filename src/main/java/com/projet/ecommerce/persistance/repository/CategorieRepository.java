package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface permettante de communiquer avec la base de données pour la table Catégorie.
 */

@Repository
public interface CategorieRepository extends PagingAndSortingRepository<Categorie, Integer>, CategorieRepositoryCustom {

    Collection<Categorie> findAll();
}
