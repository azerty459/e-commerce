package com.projet.ecommerce.persistance.repository;

import java.util.Collection;
import java.util.Map;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

    Map<Categorie, Long> countProduitsByCategories();

}
