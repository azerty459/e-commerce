package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        //System.out.println(query.getResultList().size());
        return query.getResultList();
    }

    /*public Collection<Produit> findProduitsWithCriteria(){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> q = criteriaBuilder.createQuery(Produit.class);
        Root root = q.from(Produit.class);

        Predicate predicate = criteriaBuilder.conjunction();

        List<Produit> result = entityManager.createQuery(q).getResultList();

        return result;

    }*/

    /*public Collection<Produit> findProduitsWithCriteria(String nomProduit){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> q = criteriaBuilder.createQuery(Produit.class);
        Root root = q.from(Produit.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nomProduit != null) {
            predicates.add(criteriaBuilder.like(root.get("nom"), nomProduit));
        }

        q.where(predicates.toArray(new Predicate[predicates.size()]));

        List<Produit> result = entityManager.createQuery(q).getResultList();

        return result;

    }*/
    /*
    public Collection<Produit> findProduitsWithCriteria(String nomProduit, String partieNomProduit){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> q = criteriaBuilder.createQuery(Produit.class);
        Root root = q.from(Produit.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nomProduit != null) {
            predicates.add(criteriaBuilder.equal(root.get("nom"), nomProduit));
        }

        if (partieNomProduit != null) {
            predicates.add(criteriaBuilder.like(root.get("nom"), "%" + partieNomProduit + "%"));
        }

        q.where(predicates.toArray(new Predicate[predicates.size()]));

        List<Produit> result = entityManager.createQuery(q).getResultList();

        return result;

    }*/

    public Collection<Produit> findProduitsWithCriteria(Double borneSupAvisClient, String nomProduit, String partieNomProduit, String nomCategorie){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> q = criteriaBuilder.createQuery(Produit.class);
        Root<Produit> root = q.from(Produit.class);
        List<Predicate> predicates = new ArrayList<>();

        if (borneSupAvisClient != null) {
            Join avisClientsJoin = root.join("avisClients");
            q.groupBy(avisClientsJoin.get("id"));
            Predicate predicatAvis = criteriaBuilder.greaterThan(criteriaBuilder.avg(avisClientsJoin.get("note")), borneSupAvisClient);
            predicates.add(predicatAvis);
        }

        if (nomProduit != null) {
            predicates.add(criteriaBuilder.equal(root.get("nom"), nomProduit));
        }

        if (partieNomProduit != null) {
            predicates.add(criteriaBuilder.like(root.get("nom"), "%" + partieNomProduit + "%"));
        }

        if (nomCategorie != null) {
            Join categoriesJoin = root.join("categories");
            predicates.add(criteriaBuilder.equal(categoriesJoin.get("nomCategorie"), nomCategorie));
        }

        q.where(predicates.toArray(new Predicate[predicates.size()]));

        List<Produit> result = entityManager.createQuery(q).getResultList();

        return result;

    }
}