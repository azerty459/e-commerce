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

    @Query("SELECT souscat FROM Categorie AS souscat WHERE souscat.borneGauche >= " +
            "(SELECT maincat.borneGauche FROM Categorie AS maincat WHERE maincat.idCategorie =:id) " +
            "AND souscat.borneDroit <= " +
            "(SELECT maincat2.borneDroit FROM Categorie AS maincat2 WHERE maincat2.idCategorie =:id)")
    Collection<Categorie> findByIdCategorieWithSousCat(@Param("id")  int id);

    Collection<Categorie> findByNomCategorie(String nom);

    @Query("SELECT souscat FROM Categorie AS souscat WHERE souscat.borneGauche >= " +
            "(SELECT maincat.borneGauche FROM Categorie AS maincat WHERE maincat.nomCategorie =:nom) " +
            "AND souscat.borneDroit <= " +
            "(SELECT maincat2.borneDroit FROM Categorie AS maincat2 WHERE maincat2.nomCategorie =:nom)")
    Collection<Categorie> findByNomCategorieWithSousCat(@Param("nom") String nom);
}
