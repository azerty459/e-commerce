package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.CategorieSupprime;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettante de communiquer avec la base de données pour la table categorie_supprime.
 */

@Repository
public interface CategorieSupprimeRepository extends PagingAndSortingRepository<CategorieSupprime, Integer>, CategorieSupprimeRepositoryCustom {

	@Override
	Collection<CategorieSupprime> findAll();

}
