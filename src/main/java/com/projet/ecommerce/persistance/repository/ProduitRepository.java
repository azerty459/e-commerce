package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettant de communiquer avec la base de données pour la table Produit.
 */

@Repository
public interface ProduitRepository extends PagingAndSortingRepository<Produit, String>, ProduitRepositoryCustom {

    Collection<Produit> findAll();

    Collection<Produit> findByNomContainingIgnoreCase(String nom);
}
