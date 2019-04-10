package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class ProduitRepositoryCustomImpl implements ProduitRepositoryCustom {

    // Requêtes en Java Persistence Query Language
    private static final String SQL_ALL_PRODUCTS = "SELECT p FROM Produit AS p";
    private static final String SQL_PRODUCT_BY_REFERENCE = "SELECT p FROM Produit AS p WHERE p.referenceProduit = :ref";

    private static final String SQL_PRODUCTS_BY_CATEGORY = "SELECT c.produits FROM Categorie AS c "
            + "WHERE c.borneGauche >= ("
            + "SELECT cat_recherchee.borneGauche "
            + "FROM Categorie AS cat_recherchee "
            + "WHERE cat_recherchee.nomCategorie =:cat"
            + ") "
            + "AND c.borneDroit <= ("
            + "SELECT cat_recherchee2.borneDroit "
            + "FROM Categorie AS cat_recherchee2 "
            + "WHERE cat_recherchee2.nomCategorie =:cat)";

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
    public Collection<Produit> findByCriteria(double noteSup, double noteInf, String nomProduit, String nomProduitContient, Categorie categorie) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        //Construction de la base de la requete
        CriteriaQuery<Produit> query = criteriaBuilder.createQuery(Produit.class);
        Root<Produit> p = query.from(Produit.class);
        Join<Produit, AvisClient> ac = p.join("avisClients", JoinType.LEFT);
        Join<Produit, Categorie> c = p.join("categories", JoinType.LEFT);
        query.select(p);

        //Création predicat des elements specifiques
        Collection<Predicate> predicateHaving = new ArrayList<>();
        Collection<Predicate> predicateWhere = new ArrayList<>();
        if (noteSup >= 0) {
            predicateHaving.add(criteriaBuilder.gt(criteriaBuilder.avg(ac.get("note")), noteSup));
        }
        if (noteInf >= 0) {
            predicateHaving.add(criteriaBuilder.lt(criteriaBuilder.avg(ac.get("note")), noteInf));
        }
        if (nomProduit != null && !nomProduit.trim().isEmpty()) {
            predicateWhere.add(criteriaBuilder.equal(p.get("nom"), nomProduit));
        }
        if(nomProduitContient != null && !nomProduitContient.trim().isEmpty()) {
            predicateWhere.add(criteriaBuilder.like(p.get("nom"), "%" + nomProduitContient + "%"));
        }
        if(categorie != null) {
            predicateWhere.add(criteriaBuilder.equal(c.get("idCategorie"), categorie.getIdCategorie()));
        }

        //Ajout des predicats
        if (!predicateHaving.isEmpty()) {
            query.groupBy(p.get("referenceProduit"));
            Predicate[] predicates = predicateHaving.toArray(new Predicate[predicateHaving.size()]);
            query.having(criteriaBuilder.and(predicates));
        }
        if (!predicateWhere.isEmpty()) {
            Predicate[] predicates = predicateWhere.toArray(new Predicate[predicateWhere.size()]);
            query.where(predicates);
        }

        TypedQuery<Produit> resultQuery = entityManager.createQuery(query);
        return resultQuery.getResultList();
    }

}
