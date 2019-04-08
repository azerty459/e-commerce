package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);
    
    Collection<Produit> findAllWithCriteriaRequeteComplexe(String nom, String partieNom, Double moyenneAvisInferieurA, Double moyenneAvisSuperieurA, Categorie categorie);

}
