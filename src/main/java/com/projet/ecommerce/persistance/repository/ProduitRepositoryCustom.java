package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;

import java.math.BigDecimal;
import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);
    
    Collection<Produit> filterProduits(Double noteMoyInf, Double noteMoySup, String nom, String nomContient, String categorie);

}
