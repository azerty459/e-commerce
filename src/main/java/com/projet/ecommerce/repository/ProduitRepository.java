package com.projet.ecommerce.repository;

import com.projet.ecommerce.entity.Produit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

/**
 * Interface permettant de communiquer avec la base de données pour la table Produit.
 */

@Repository
public interface ProduitRepository extends CrudRepository<Produit, String> {

    Collection<Produit> findAll();
}
