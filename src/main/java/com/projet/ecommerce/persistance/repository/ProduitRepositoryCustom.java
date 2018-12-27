package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;
import java.util.List;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

    List<Produit> findAllProduit();

    List<Produit> findAllProduitByNote(final float note1, final float note2);

    List<Produit> findAllProduitByNom(final String nom);

    List<Produit> findAllProduitLikeNom(final String nom);

    List<Produit> findAllProduitByCategorie(final Categorie categorie);

}
