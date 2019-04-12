package com.projet.ecommerce.persistance.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    public Collection<Produit> exerice3(Double valeurMin, Double valeurMax, String nomProduit, String valeurContenue, Categorie categorie){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> q = criteriaBuilder.createQuery(Produit.class);
        Root<Produit> root = q.from(Produit.class);
        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> avisPredicate = new ArrayList<>();

        if (valeurMin != null || valeurMax != null) {
            Join<Produit,AvisClient> avisClientsJoin = root.join("avisClients");
            Expression<Double> noteMoy = criteriaBuilder.avg(avisClientsJoin.<Number>get("note"));
            if (valeurMin != null) {
                Predicate pInf = criteriaBuilder.greaterThan(noteMoy, valeurMin);
                avisPredicate.add(pInf);
            }
            if (valeurMax != null){
                Predicate pMax = criteriaBuilder.lessThan(noteMoy, valeurMax);
                avisPredicate.add(pMax);
            }
            q.groupBy(root.get("referenceProduit"));
            q.having(avisPredicate.toArray(new Predicate[predicates.size()]));
        }

        if (nomProduit != null) {
            predicates.add(criteriaBuilder.equal(root.get("nom"), nomProduit));
        }

        if (valeurContenue != null) {
            predicates.add(criteriaBuilder.like(root.get("nom"), "%" + valeurContenue + "%"));
        }

        if (categorie.getNomCategorie() != null) {
            Join categoriesJoin = root.join("categories");
            predicates.add(criteriaBuilder.equal(categoriesJoin.get("nomCategorie"), categorie.getNomCategorie()));
        }

        q.where(predicates.toArray(new Predicate[predicates.size()]));

        List<Produit> result = entityManager.createQuery(q).getResultList();

        return result;

    }
}
