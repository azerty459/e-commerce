package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

//    Collection<Produit> findAllWithCriteriaRequeteComplexe(String nom, String partieNom, double moyenneAvisInferieurA, double moyenneAvisSuperieurA, Categorie categorie);

}
