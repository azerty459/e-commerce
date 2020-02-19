package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;

public interface CaracteristiqueRepositoryCustom {
    Collection<Caracteristique> findByProduitAndMotCle(Produit produit, String motCle);
}
