package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.business.dto.StatistiqueProduitCategorieDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ProduitRepositoryCustom {

	Collection<Produit> findAllWithCriteria(String ref, String cat);

	/**
	 * Trouve les produits possédant la catégorie ou une categorie fille de la categorie recherchée
	 *
	 * @param pageable  Information pour la pagination
	 * @param categorie La categorie recherchée
	 * @return
	 */
	Page<Produit> findByCategories(Pageable pageable, Categorie categorie);

	Collection<StatistiqueProduitCategorieDTO> countProduitsByCategories();

}
