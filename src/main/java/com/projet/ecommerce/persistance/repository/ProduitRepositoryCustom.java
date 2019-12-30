package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

    Collection<Produit> findAllWithOptionnalFilter(Double noteMoyenneMinimal, Double noteMoyennemaximal, String nomProduitExact, String nomProduitDoitContenir, Integer categorieRecherche);


}
