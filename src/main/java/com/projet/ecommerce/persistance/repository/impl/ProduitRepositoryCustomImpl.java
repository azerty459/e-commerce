package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.*;
import com.projet.ecommerce.persistance.entity.Categorie_;
import com.projet.ecommerce.persistance.entity.Produit_;
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

    /**
     * Renvoit la liste de Produit correspondant aux critères de selection optionnels fournit
     *
     * @param noteMoyenneMini
     * @param noteMoyenneMaxi
     * @param nomProduitExact
     * @param nomProduitDoitContenir
     * @param categorieRecherche
     * @return
     */
    @Override
    public Collection<Produit> findAllWithOptionnalFilter(Double noteMoyenneMini, Double noteMoyenneMaxi, String nomProduitExact, String nomProduitDoitContenir, Integer categorieRecherche) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Produit.class);
        Root<Produit> produitRoot = query.from(Produit.class);


        if (isValide(noteMoyenneMini)) {
            predicateList.add(getNoteMoyenneMiniPredicate(noteMoyenneMini, cb, query, produitRoot));
        }
        if (isValide(noteMoyenneMaxi)) {
            predicateList.add(getNoteMoyenneMaxiPredicate(noteMoyenneMaxi, cb, query, produitRoot));
        }
        if (isValide(nomProduitExact)) {
            predicateList.add(getNomproduitExactPredicate(nomProduitExact, cb, produitRoot));
        }
        if (isValide(nomProduitDoitContenir)) {
            predicateList.add(getNomproduitContientPredicate(nomProduitDoitContenir, cb, produitRoot));
        }
        if (isValide(categorieRecherche)) {
            predicateList.add(getCategoriePredicate(categorieRecherche, query));

        }

        query.select(produitRoot).where(predicateList.toArray(new Predicate[0])).distinct(true);
        TypedQuery produitTypedQuery = entityManager.createQuery(query);
        System.out.println("SQL EXECUTE :" + produitTypedQuery.unwrap(org.hibernate.Query.class).getQueryString());

        return produitTypedQuery.getResultList();
    }

    private boolean isValide(Object value) {

        return (value != null && !value.toString().trim().isEmpty());

    }


    /**
     * Predicat categorie : la liste des categories du produit contient la categorie recherche
     *
     * @param categorieRecherche
     * @param query
     * @return precicat
     */
    private Predicate getCategoriePredicate(Integer categorieRecherche, CriteriaQuery query) {
        Subquery<Categorie> categorieSubquery = query.subquery(Categorie.class);
        Root<Categorie> categorieRoot = categorieSubquery.from(Categorie.class);
        Expression<Categorie> idCategorie = categorieSubquery.select(categorieRoot.get("idCategorie"));
        return idCategorie.in(categorieRecherche);

    }

    /**
     * Predicat nomExact : le nom du produit correspond exactement au nom recherché
     *
     * @param nomProduitExact
     * @param cb
     * @param produitTable
     * @return
     */
    private Predicate getNomproduitExactPredicate(String nomProduitExact, CriteriaBuilder cb, Root<Produit> produitTable) {

        return cb.equal(cb.lower(produitTable.get("nom")), nomProduitExact.toLowerCase());
    }

    /**
     * Predicat nomContient : le nom du produit contient le nom recherché
     *
     * @param nomProduitContient
     * @param cb
     * @param produitTable
     * @return
     */
    private Predicate getNomproduitContientPredicate(String nomProduitContient, CriteriaBuilder cb, Root<Produit> produitTable) {

        return cb.like(cb.lower(produitTable.get("nom")), "%" + nomProduitContient.toLowerCase() + "%");
    }

    /**
     * Predicat : la moyenne des avis client du produit est superieur à la noteMoyenneMini fournit
     *
     * @param noteMoyenneMini
     * @param cb
     * @param query
     * @param produitTable
     * @return
     */
    private Predicate getNoteMoyenneMiniPredicate(Double noteMoyenneMini, CriteriaBuilder cb, CriteriaQuery query, Root<Produit> produitTable) {
        Subquery<Double> noteMoyenneAvis = query.subquery(Double.class);
        Root<AvisClient> subQueryAvis = noteMoyenneAvis.from(AvisClient.class);
        noteMoyenneAvis.select(cb.avg(subQueryAvis.get("note"))).groupBy(produitTable.get("referenceProduit"));

        return cb.greaterThan(noteMoyenneAvis, noteMoyenneMini);
    }

    /**
     * Predicat : la note moyenne du produit est inférieur à la noteMoyenneMaxi fournit
     *
     * @param noteMoyenneMaxi
     * @param cb
     * @param query
     * @param produitTable
     * @return
     */
    private Predicate getNoteMoyenneMaxiPredicate(Double noteMoyenneMaxi, CriteriaBuilder cb, CriteriaQuery query, Root<Produit> produitTable) {
        Subquery<Double> noteMoyenneAvis = query.subquery(Double.class);
        Root<AvisClient> subQueryAvis = noteMoyenneAvis.from(AvisClient.class);
        noteMoyenneAvis.select(cb.avg(subQueryAvis.get("note"))).groupBy(produitTable.get("referenceProduit"));
        return cb.lessThan(noteMoyenneAvis, noteMoyenneMaxi);
    }


}
