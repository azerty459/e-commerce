package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.business.dto.ProduitCriteriaDTO;
import com.projet.ecommerce.persistance.entity.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Interface permettant de communiquer avec la base de données pour la table Produit.
 */

@Repository
public interface ProduitRepository extends PagingAndSortingRepository<Produit, String>, ProduitRepositoryCustom {

    @Override
    Collection<Produit> findAll();

    Collection<Produit> findByNomContainingIgnoreCase(String nom);

    Page<Produit> findByNomContainingIgnoreCase(Pageable pageable, String nom);

    Page<Produit> findByNomContainingIgnoreCaseAndCategories_borneGaucheGreaterThanEqualAndCategories_borneDroitLessThanEqual(Pageable pageable, String nom, int borneGauche, int borneDroite);

    Page<Produit> findByCategories_IdCategorie(Pageable pageable, @Param("id") int idCategorie);

    Page<Produit> findAllByCategories_borneGaucheGreaterThanEqualAndCategories_borneDroitLessThanEqual(Pageable pageable, int borneGauche, int borneDroite);

}
