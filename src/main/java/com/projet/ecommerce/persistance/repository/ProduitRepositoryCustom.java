package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

/*
    Celle-ci devra accepter les filtres optionnels suivant:
    La note moyenne des avis clients est > paramA
    La note moyenne des avis clients est < paramB
    Le nom du produit = paramC
    Le nom du produit contient la valeur paramD
    La catégorie du produit = paramE (précisément, pas de notion de hiérarchie)
    Le format des paramètres paramA à paramE est libre mais doit être justifiée.

*/

    Collection<Produit> findByCriteria(Float minAvgNote, Float maxAvgNote, String nomProduit, String motCle, Categorie categorie);

    void printAvgNoteByProduct();
}
