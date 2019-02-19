package com.projet.ecommerce.persistance.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
	public Collection<Produit> filterProduits(Double noteMoyInf, Double noteMoySup, String nom,
			String nomContient, String categorie) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produit> cq = cb.createQuery(Produit.class);
		
		Root<Produit> prod = cq.from(Produit.class);
		Join<Produit, AvisClient> avis = prod.join("avisClients");;
		Join<Produit, Categorie> categs = prod.join("categories");
		
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> predicatesHaving = new ArrayList<>();
		
		Predicate p = null; 
		
		if(noteMoyInf!=null) {
			p = cb.greaterThan(cb.avg(avis.get("note")), noteMoyInf);
			predicatesHaving.add(p);
		}
		if(noteMoySup!=null) {
			p = cb.lessThan(cb.avg(avis.get("note")), noteMoySup);
			predicatesHaving.add(p);
		}
		
		if(nom!=null) {
			p = cb.equal(prod.get("nom"), nom);
			predicates.add(p);
		}		
		if(nomContient!=null) {
			p = cb.like(prod.get("nom"), "%"+nomContient+"%");
			predicates.add(p);
		}
		
		if(categorie!=null) {
			p = cb.equal(categs.get("nomCategorie"), categorie);
			predicates.add(p);
		}
		
		
		Predicate pAnd = cb.and(predicates.stream().toArray(Predicate[]::new));
		Predicate pAndHaving = cb.and(predicatesHaving.stream().toArray(Predicate[]::new));
		
		cq.select(prod);
		cq.where(pAnd).groupBy(prod).having(pAndHaving);
		
		return entityManager.createQuery(cq).getResultList();
		
		
	}
    
    
    
    
}