package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


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
    public Collection<Produit> findAllWithComplexCriterias(Integer noteBasse, Integer noteHaute, String nom, String nomContains, String categorie) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
        Root<Produit> rootProduit = query.from(Produit.class);
        Root<AvisClient> rootAvisClient = query.from(AvisClient.class);

        //@Filter  + @FilterDef ?? je vais devoir l'utiliser chez Decath (exemple dans Expedition.java)

        query.select(rootProduit);
        query.distinct(true);
        query.where(builder.equal(rootProduit.get("referenceProduit"), rootAvisClient.get("id")));
        if(noteBasse != null){
            query.where(builder.ge(rootAvisClient.get("note"), noteBasse));
        }

        if(noteHaute != null){
            query.where(builder.le(rootAvisClient.get("note"), noteHaute));
        }

        if(nom != null && !nom.isEmpty()){
            query.where(builder.equal(rootProduit.get("nom"), nom));
        }

        if(nom != null && !nom.isEmpty() && nomContains != null && !nomContains.isEmpty()){
            query.where(builder.equal(rootProduit.get("nom"), nom));
            query.where(builder.or(builder.like(rootProduit.get("nom"), nomContains)));
        }

        TypedQuery<Produit> q = entityManager.createQuery(query);
        List<Produit> result = q.getResultList();

        return null;
    }

}