package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;

import java.util.Collection;

public interface ProduitRepositoryCustom {

    Collection<Produit> findAllWithCriteria(String ref, String cat);

    Collection<Produit> findWithFiltersWithCriteria(Float averageLowerBoundFilter,
                                                    Float averageUpperBoundFilter,
                                                    String fullNameFilter,
                                                    String partNameFilter,
                                                    String sameCatFilter,
                                                    String subCatFilter);
}
