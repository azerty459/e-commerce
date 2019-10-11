package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettante de communiquer avec la base de données pour la table Caractéristique.
 */

@Repository
public interface CaracteristiqueRepository extends PagingAndSortingRepository<Caracteristique, Integer>, CategorieRepositoryCustom {

    @Override
    Collection<Caracteristique> findAll();

    Collection<Caracteristique> findAllByOrderByTypeCaracteristique();

    Collection<Caracteristique> findByTypeCaracteristique(String type);

}
