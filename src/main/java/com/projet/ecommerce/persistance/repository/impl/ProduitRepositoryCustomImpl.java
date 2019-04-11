package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import java.util.Collection;


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
	public Collection<Produit> findAllWithCriteriaRequeteComplexe(String nom, String partieNom,
			Double moyenneAvisInferieurA, Double moyenneAvisSuperieurA, Categorie categorie) {
		
		CriteriaBuilder criteriaBuilderObj = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Produit> queryObj = criteriaBuilderObj.createQuery(Produit.class);

	    Root<Produit> fromProduit = queryObj.from(Produit.class);

	    queryObj.select(fromProduit);

		
		if(nom !=null) {
			queryObj.where(criteriaBuilderObj.equal(fromProduit.get("nom"), nom));

		}
		
		if(partieNom !=null) {
			queryObj.where(criteriaBuilderObj.like(criteriaBuilderObj.upper(fromProduit.get("nom")), "%"+partieNom.toUpperCase()+"%"));

		}
		
		if(moyenneAvisInferieurA != null) {
			Join<Produit, AvisClient> avisClient = fromProduit.join("avisClients");
			queryObj.groupBy(fromProduit.get("referenceProduit"));
			queryObj.having(criteriaBuilderObj.lt(criteriaBuilderObj.avg(avisClient.get("note")),moyenneAvisInferieurA));
		}
		
		if(moyenneAvisSuperieurA != null) {
			Join<Produit, AvisClient> avisClient = fromProduit.join("avisClients");
			queryObj.groupBy(fromProduit.get("referenceProduit"));
			queryObj.having(criteriaBuilderObj.gt(criteriaBuilderObj.avg(avisClient.get("note")),moyenneAvisSuperieurA));
		}

		if (categorie != null) {
			Join<Produit, Categorie> categorieJoin = fromProduit.join("categories");
			//Ne fonctionne pas avec les id de la catégorie
			queryObj.where(criteriaBuilderObj.equal(categorieJoin.get("nomCategorie"), categorie.getNomCategorie()));
		}
		return entityManager.createQuery(queryObj).getResultList();
	}
	
}
