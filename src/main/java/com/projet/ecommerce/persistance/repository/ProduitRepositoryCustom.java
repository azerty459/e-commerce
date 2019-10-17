package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

	//Collection<Produit> exerciceTrois(int paramA, int paramB, String paramC, String paramD, String paramE);

}
