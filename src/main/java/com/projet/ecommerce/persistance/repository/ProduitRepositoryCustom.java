package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.utilitaire.ProduitFilterCustomUtilitaire;

import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

    Collection<Produit> findWithFiltersWithCriteria(ProduitFilterCustomUtilitaire produitFilterCustomUtilitaire);
}
