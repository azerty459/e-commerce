package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Repository
public class ProduitRepositoryCustomImpl implements ProduitRepositoryCustom {

    // Requêtes en Java Persistence Query Language
    private static final String SQL_ALL_PRODUCTS = "SELECT p FROM Produit AS p";

    private static final String SQL_PRODUCT_BY_REFERENCE = "SELECT p FROM Produit AS p WHERE p.referenceProduit = :ref";

    private static final String SQL_PRODUCTS_BY_CATEGORY = "SELECT c.produits FROM Categorie AS c " +
            "WHERE c.borneGauche >= (" +
            "SELECT cat_recherchee.borneGauche " +
            "FROM Categorie AS cat_recherchee " +
            "WHERE cat_recherchee.nomCategorie =:cat" +
            ") " +
            "AND c.borneDroit <= (" +
            "SELECT cat_recherchee2.borneDroit " +
            "FROM Categorie AS cat_recherchee2 " +
            "WHERE cat_recherchee2.nomCategorie =:cat)";

    @Autowired
    private EntityManager entityManager;

    @Override
    public Collection<Produit> findAllWithCriteria(String ref, String cat) {

        String sql = "";
        Query query = null;

        // ref et cat sont nuls
        if(ref == null && cat == null) {
            query =  entityManager.createQuery(SQL_ALL_PRODUCTS, Produit.class);
            return query.getResultList();
        }

        // Seul ref est non nul
        if(ref != null && cat == null) {
            query =  entityManager.createQuery(SQL_PRODUCT_BY_REFERENCE, Produit.class);
            query.setParameter("ref", ref);
            return query.getResultList();
        }

        // Seul cat est non null
        if(ref == null && cat != null) {
            query =  entityManager.createQuery(SQL_PRODUCTS_BY_CATEGORY, Collection.class);
            query.setParameter("cat", cat);
        }

        // ref et cat non nulls: à implémenter si demandé

        return query.getResultList();
    }
}
