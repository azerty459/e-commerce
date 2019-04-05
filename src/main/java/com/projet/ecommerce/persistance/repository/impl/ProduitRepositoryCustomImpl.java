package com.projet.ecommerce.persistance.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;


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
    public Collection<Produit> findAllWithCriteria(Double noteMoyenneMin,Double noteMoyenneMax,
    													String nomProduit,String containsInProductName,
    														Categorie categorie){
    	
    	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Produit> query = criteriaBuilder.createQuery(Produit.class);
    	
    	Root<Produit> fromProduit = query.from(Produit.class);
    	
    	query.select(fromProduit);
    	
    	if(nomProduit != null) {
    		query.where(criteriaBuilder.equal(fromProduit.get("nom"), nomProduit));
    	}
    	
    	if(containsInProductName != null) {
    		query.where(criteriaBuilder.like(fromProduit.get("nom"), ""));
    	}
    	
    	if((noteMoyenneMin != null) ||(noteMoyenneMax != null)) {
    		Join<Produit, AvisClient> avisClient = fromProduit.join("avisClients");
        	query.groupBy(avisClient.get("produit"));
        	
    		if(noteMoyenneMin != null) {
        		query.having(criteriaBuilder.greaterThan(criteriaBuilder.avg(avisClient.get("note")), noteMoyenneMin));
        	}
        	
        	if(noteMoyenneMax != null) {
        		query.having(criteriaBuilder.lessThan(criteriaBuilder.avg(avisClient.get("note")), noteMoyenneMax));
        	}
    	}
    	
    	if(categorie != null) {
    		Join<Produit, Categorie> categories = fromProduit.join("categories");
    		query.where(criteriaBuilder.equal(categories.get("nomCategorie"), categorie.getNomCategorie()));
    	}
    	
    	TypedQuery<Produit> typedQuery = entityManager.createQuery(query
    	        //.select(fromProduit)
    	        //.where(criteriaBuilder.equal(fromProduit.get("nom"), nomProduit))
    	        //.where(criteriaBuilder.like(fromProduit.get("nom"), pattern))
    	        //.groupBy(fromProduit.get("reference_produit"))
    	        //.having(criteriaBuilder.greaterThan(criteriaBuilder.avg(avisClient.get("note")), noteMoyenneMin))
    	        //.having(criteriaBuilder.lessThan(criteriaBuilder.avg(avisClient.get("note")), noteMoyenneMax))
    	);

    	return typedQuery.getResultList();
    }
}