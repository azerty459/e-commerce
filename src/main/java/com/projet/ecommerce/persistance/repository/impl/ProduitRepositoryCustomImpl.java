package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;


@Repository
public class ProduitRepositoryCustomImpl implements ProduitRepositoryCustom {

    // Requêtes en Java Persistence Query Language
    private static final String SQL_ALL_PRODUCTS = "SELECT p FROM Produit AS p";
    private static final String SQL_PRODUCT_BY_REFERENCE = "SELECT p FROM Produit AS p WHERE p.referenceProduit = :ref";
    private static final String SQL_PRODUCTS_BY_CATEGORY_NAME = "SELECT c.produits FROM Categorie AS c " +
            "WHERE c.borneGauche >= (" +
            "SELECT cat_recherchee.borneGauche " +
            "FROM Categorie AS cat_recherchee " +
            "WHERE cat_recherchee.nomCategorie =:cat" +
            ") " +
            "AND c.borneDroit <= (" +
            "SELECT cat_recherchee2.borneDroit " +
            "FROM Categorie AS cat_recherchee2 " +
            "WHERE cat_recherchee2.nomCategorie =:cat)";
    private static final String SQL_PRODUCTS_BY_CATEGORY = "SELECT p FROM Produit p Join p.categories c " +
            "Where c.borneGauche >= :borneGauche " +
            "And c.borneDroit <= :borneDroite";
    private static final String SQL_COUNT_PRODUCTS_BY_CATEGORY = "SELECT count(p) FROM Produit p Join p.categories c " +
            "Where c.borneGauche >= :borneGauche " +
            "And c.borneDroit <= :borneDroite";

    @Autowired
    private EntityManager entityManager;

    @Override
    public Collection<Produit> findAllWithCriteria(String ref, String cat) {

        Query query = null;

        if (ref == null) {
            if (cat == null) {
                query = entityManager.createQuery(SQL_ALL_PRODUCTS, Produit.class);
            } else {
                System.out.println("testsdfsdfsdfsdf");
                query = entityManager.createQuery(SQL_PRODUCTS_BY_CATEGORY_NAME, Collection.class);
                query.setParameter("cat", cat);
            }
        } else {
            // Dans tous les cas, on fait une recherche par référence
            query = entityManager.createQuery(SQL_PRODUCT_BY_REFERENCE, Produit.class);
            query.setParameter("ref", ref);
        }
        System.out.println(query.getResultList().size());
        return query.getResultList();
    }

    @Override
    public Page<Produit> findByCategories(Pageable pageable, Categorie categorie) {
        if(pageable == null || categorie == null) {
            return Page.empty();
        }

        //Recupere les produits à retourner
        TypedQuery<Produit> queryProduits = entityManager.createQuery(SQL_PRODUCTS_BY_CATEGORY, Produit.class);
        queryProduits.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        queryProduits.setMaxResults(pageable.getPageSize());
        queryProduits.setParameter("borneGauche", categorie.getBorneGauche());
        queryProduits.setParameter("borneDroite", categorie.getBorneDroit());
        List<Produit> produits = queryProduits.getResultList();

        //Compte le nombre total de produit
        TypedQuery<Long> queryTotal = entityManager.createQuery(SQL_COUNT_PRODUCTS_BY_CATEGORY, Long.class);
        queryTotal.setParameter("borneGauche", categorie.getBorneGauche());
        queryTotal.setParameter("borneDroite", categorie.getBorneDroit());
        Long total = queryTotal.getSingleResult();

        return new PageImpl<>(produits, pageable, total);
    }
}
