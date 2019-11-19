package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;
import java.util.List;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

    List<Produit> findAllWithParams(String productName, String productNameContains, Double averageNoteLower, Double averageNoteHigher, String categoryProduct);
}
