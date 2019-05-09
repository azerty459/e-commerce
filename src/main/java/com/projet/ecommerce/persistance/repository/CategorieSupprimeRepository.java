package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.entity.CategorieSupprime;

/**
 * Interface permettante de communiquer avec la base de données pour la table categorie_supprime.
 */

@Repository
public interface CategorieSupprimeRepository extends PagingAndSortingRepository<CategorieSupprime, Integer>, CategorieSupprimeRepositoryCustom {

    @Override
    Collection<CategorieSupprime> findAll();

}
