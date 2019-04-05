package com.projet.ecommerce.persistance.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

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
	public Collection<Produit> findAllWithParam(Integer noteMin, Integer noteMax, String name, String partOfName,
			String categorie) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produit> criteriaQuery = criteriaBuilder.createQuery(Produit.class);
		
		Root<Produit> produit = criteriaQuery.from(Produit.class);
		

		Join<Produit, AvisClient> avis = produit.joinList("avisClients", JoinType.LEFT);
		Join<Produit, Categorie> categ = produit.joinList("categories", JoinType.LEFT);
		List<Predicate> havingPredicates = new ArrayList<>();
		List<Predicate> wherePredicates = new ArrayList<>();
		

		if(noteMin!=null)
			havingPredicates.add(criteriaBuilder.greaterThan(criteriaBuilder.avg(avis.get("note")), noteMin.doubleValue()));
		
		if(noteMax!=null)
			havingPredicates.add(criteriaBuilder.lessThan(criteriaBuilder.avg(avis.get("note")), noteMax.doubleValue()));

		if(name!=null)
			wherePredicates.add(criteriaBuilder.equal(produit.get("nom"), name));
		
		if(partOfName!=null)
			wherePredicates.add(criteriaBuilder.like(produit.get("nom"), "%"+partOfName+"%"));
		
		if(categorie!=null)
			wherePredicates.add(criteriaBuilder.equal(categ.get("nomCategorie"), categorie));
		
		Predicate pHaving = criteriaBuilder.and(havingPredicates.toArray(new Predicate[havingPredicates.size()]));
		Predicate pWhere = criteriaBuilder.and(wherePredicates.toArray(new Predicate[wherePredicates.size()]));
		
		criteriaQuery.select(produit);

		criteriaQuery.where(pWhere).distinct(true).having(pHaving);

		
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}