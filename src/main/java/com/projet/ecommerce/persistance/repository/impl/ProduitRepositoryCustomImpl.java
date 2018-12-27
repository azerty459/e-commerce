package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.*;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
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
    public List<Produit> findAllProduit() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
        Root<Produit> root = query.from(Produit.class);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Produit> findAllProduitByNote(final float note1,final  float note2) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
        Root<Produit> root = query.from(Produit.class);
        Join<Produit, Caracteristique> caracteristiqueProduitJoin = root.join(Produit_.caracteristiques, JoinType.INNER);
        query.where(
            builder.between(caracteristiqueProduitJoin.get(Caracteristique_.VALEUR), note1, note2)
        );
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Produit> findAllProduitByNom(final String nom) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
        Root<Produit> root = query.from(Produit.class);
        query.where(
                builder.equal(root.get("nom"), nom)
        );
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Produit> findAllProduitLikeNom(final String nom) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
        Root<Produit> root = query.from(Produit.class);
        query.where(
                builder.like(root.get("nom"), nom)
        );
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Produit> findAllProduitByCategorie(final Categorie categorie) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
        Root<Produit> root = query.from(Produit.class);
        Expression<Collection<Categorie>> categories = root.get("categories");
        Expression<Categorie> paramCategorie = builder.parameter(Categorie.class);
        Predicate predicate = builder.isMember(categorie, categories);
        query.where(
                predicate
        );
        return entityManager.createQuery(query).getResultList();
    }
}