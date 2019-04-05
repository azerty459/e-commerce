package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);
    Collection<Produit> findAllWithParam(Integer noteMin, Integer noteMax, String name, String partOfName, String categorie);

}
