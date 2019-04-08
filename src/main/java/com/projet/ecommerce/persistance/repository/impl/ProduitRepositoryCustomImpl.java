package com.projet.ecommerce.persistance.repository.impl;

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
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;

@Repository
public class ProduitRepositoryCustomImpl implements ProduitRepositoryCustom {

	// Requêtes en Java Persistence Query Language
	private static final String SQL_ALL_PRODUCTS = "SELECT p FROM Produit AS p";
	private static final String SQL_PRODUCT_BY_REFERENCE = "SELECT p FROM Produit AS p WHERE p.referenceProduit = :ref";

	private static final String SQL_PRODUCTS_BY_CATEGORY = "SELECT c.produits FROM Categorie AS c "
			+ "WHERE c.borneGauche >= (" + "SELECT cat_recherchee.borneGauche " + "FROM Categorie AS cat_recherchee "
			+ "WHERE cat_recherchee.nomCategorie =:cat" + ") " + "AND c.borneDroit <= ("
			+ "SELECT cat_recherchee2.borneDroit " + "FROM Categorie AS cat_recherchee2 "
			+ "WHERE cat_recherchee2.nomCategorie =:cat)";

	@Autowired
	private EntityManager entityManager;

	@Autowired
	CategorieRepository categorieRepo;

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
	public Collection<Produit> findProduitWithCriteria(Integer noteMin, Integer noteMax, String nomProduitComplet,
			String nomProduitNonComplet, int idCategorie) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		List<Predicate> conditions = new ArrayList<Predicate>();
		CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
		Root<Produit> produit = query.from(Produit.class);
		Join<AvisClient, Produit> avisClient = produit.join("produits");
		Join<Categorie, Produit> categories = produit.join("produits");

		if (idCategorie != 0) {
			Categorie categorie = categorieRepo.findById(idCategorie).get();
			conditions.add(builder.isMember(categorie, produit.get("categories")));
		}

		if (noteMin > 0 && noteMin < noteMax) {
			conditions.add(builder.lessThan(avisClient.get("note"), noteMax));
		}

		if (noteMax > 0 && noteMin < noteMax) {
			conditions.add(builder.lessThan(avisClient.get("note"), noteMax));
		}

		if (nomProduitComplet != null) {
			conditions.add(builder.equal(produit.get("nom"), nomProduitComplet));
		}

		if (nomProduitNonComplet != null) {
			conditions.add(builder.equal(produit.get("nom"), nomProduitNonComplet));
		}

		return entityManager
				.createQuery(query.select(produit).where(builder.and(conditions.toArray(new Predicate[] {}))))
				.getResultList();
	}

}