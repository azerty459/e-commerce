package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

    public Collection<Produit> findProduitsWithCriteria(Double borneInfAvisClient, Double borneSupAvisClient, String nom, String partieNomProduit, String nomCategorie);

}
