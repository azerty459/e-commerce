package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.entity.Categorie;

/**
 * Interface permettante de communiquer avec la base de données pour la table Catégorie.
 */

@Repository
public interface CategorieRepository extends PagingAndSortingRepository<Categorie, Integer>, CategorieRepositoryCustom {

    @NotNull
    @Override
    Collection<Categorie> findAll();

    Collection<Categorie> findAllByOrderByNomCategorie();

    @Query("SELECT souscat FROM Categorie AS souscat WHERE souscat.borneGauche >= " +
            "(SELECT maincat.borneGauche FROM Categorie AS maincat WHERE maincat.idCategorie =:id) " +
            "AND souscat.borneDroit <= " +
            "(SELECT maincat2.borneDroit FROM Categorie AS maincat2 WHERE maincat2.idCategorie =:id)")
    Collection<Categorie> findByIdCategorieWithSousCat(@Param("id") int id);

    Collection<Categorie> findByNomCategorie(String nom);

    @Query("SELECT souscat FROM Categorie AS souscat WHERE souscat.borneGauche >= " +
            "(SELECT maincat.borneGauche FROM Categorie AS maincat WHERE maincat.nomCategorie =:nom) " +
            "AND souscat.borneDroit <= " +
            "(SELECT maincat2.borneDroit FROM Categorie AS maincat2 WHERE maincat2.nomCategorie =:nom)")
    Collection<Categorie> findByNomCategorieWithSousCat(@Param("nom") String nom);

    Collection<Categorie> findByNomCategorieContainingIgnoreCase(String nom);

    @Query("Select count(c) From Categorie c")
    Long countCategories();
}
