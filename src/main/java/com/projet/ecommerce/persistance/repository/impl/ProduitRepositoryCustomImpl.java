package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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

        Query query = null;

        if (ref == null) {
            if (cat == null) {
                query = entityManager.createQuery(SQL_ALL_PRODUCTS, Produit.class);
            } else {
                System.out.println("testsdfsdfsdfsdf");
                query = entityManager.createQuery(SQL_PRODUCTS_BY_CATEGORY, Collection.class);
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
    public List<Produit> findAllWithParams(String productName, String productNameContains, Double averageNoteLower, Double averageNoteHigher, String categoryProduct) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> criteriaQuery = criteriaBuilder.createQuery(Produit.class);

        Root<Produit> productRoot = criteriaQuery.from(Produit.class);

        List<Predicate> wherePredicate = new ArrayList<>();
        //For aggregate
        List<Predicate> havingPredicate = new ArrayList<>();

        if (productName != null) {
            wherePredicate.add(criteriaBuilder.equal(productRoot.get("nom"), productName));
        }
        if (productNameContains != null) {
            wherePredicate.add(criteriaBuilder.like(productRoot.get("nom"), productNameContains));
        }
        if (averageNoteLower != null) {
            Join<Produit, AvisClient> avisClient = productRoot.join("avisClients");
            havingPredicate.add(criteriaBuilder.greaterThan(criteriaBuilder.avg(avisClient.get("note")), averageNoteLower));
        }
        if (averageNoteHigher != null) {
            Join<Produit, AvisClient> avisClient = productRoot.join("avisClients");
            havingPredicate.add(criteriaBuilder.lessThan(criteriaBuilder.avg(avisClient.get("note")), averageNoteHigher));
        }
        if (categoryProduct != null) {
            Join<Produit, Categorie> categorie = productRoot.join("categories");
            wherePredicate.add(criteriaBuilder.equal(categorie.get("nomCategorie"), categoryProduct));
        }

        criteriaQuery.select(productRoot).distinct(true)
                .where(criteriaBuilder.and(wherePredicate.toArray(new Predicate[wherePredicate.size()])))
                .having(criteriaBuilder.and(havingPredicate.toArray(new Predicate[havingPredicate.size()])));
        TypedQuery<Produit> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}