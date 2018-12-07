package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

    Collection<Produit> findAllWithJPACriteriaBuilder();

    Collection<Produit> findByNomWithJPACriteriaBuilder(String nom, boolean like);

    Collection<Produit> findByCategorieWithJPACriteriaBuilder(String cat);

    Collection<Produit> findByNoteWithJPACriteriaBuilder(Double noteMin, Double noteMax);

}
