package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.business.dto.ProduitCriteriaDTO;
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
    public Collection<Produit> filter(ProduitCriteriaDTO criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> cq = cb.createQuery(Produit.class);

        Root<Produit> produit = cq.from(Produit.class);

        List<Predicate> wheres = new ArrayList<>();
        List<Predicate> havings = new ArrayList<>();

        if (criteria.getNom() != null) {
            wheres.add(cb.equal(produit.get("nom"), criteria.getNom()));
        }

        if (criteria.getNomLike() != null) {
            wheres.add(cb.like(produit.get("nom"), "%" + criteria.getNomLike() + "%"));
        }

        if (criteria.getNomCategorie() != null) {
            Join<Produit, Categorie> produitCat = produit.join("categories");
            wheres.add(cb.like(produitCat.get("nomCategorie"), "%" + criteria.getNomCategorie() + "%"));
        }

        if (criteria.getNoteMin() != null) {
            Join<Produit, AvisClient> avis = produit.join("avisClients");
            havings.add(cb.lessThan(cb.avg(avis.get("note")), criteria.getNoteMin()));
        }

        if (criteria.getNoteMax() != null) {
            Join<Produit, AvisClient> avis = produit.join("avisClients");
            havings.add(cb.greaterThan(cb.avg(avis.get("note")), criteria.getNoteMax()));
        }

        cq.where(wheres.toArray(new Predicate[0])).having(havings.toArray(new Predicate[0])).distinct(true);

        TypedQuery<Produit> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}