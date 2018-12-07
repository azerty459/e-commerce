package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
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
    public Collection<Produit> findAllWithJPACriteriaBuilder() {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
        Root<Produit> from = query.from(Produit.class);

        CriteriaQuery<Produit> selectQuery = query.select(from);


        List<Produit> result = entityManager.createQuery(selectQuery).getResultList();
        return result;

    }

    @Override
    public Collection<Produit> findByNomWithJPACriteriaBuilder(String nom, boolean like) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
        Root<Produit> from = query.from(Produit.class);


        Predicate whereCondition = null;
        if(like){
            whereCondition = builder.like(from.get("nom"),nom);
        }else{
            whereCondition = builder.equal(from.get("nom"),nom);
        }

        CriteriaQuery<Produit> selectQuery = query.select(from)
                .where(whereCondition);

        List<Produit> result = entityManager.createQuery(selectQuery)
                .getResultList();

        return result;
    }

    @Override
    public Collection<Produit> findByCategorieWithJPACriteriaBuilder(String cat) {

        //select p.* from produit p , produit_categorie pc , categorie c
        // where pc.reference_produit = p.reference_produit and pc.id_categorie = c.id_categorie and c.nom_categorie = 'Photoshop';

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
        Root<Produit> from = query.from(Produit.class);

        Join<Produit, Categorie> joinCategories = from.join("categories");

        Predicate whereCondition = builder.equal(joinCategories.get("nomCategorie"),cat);

        CriteriaQuery<Produit> selectQuery = query.select(from)
                .where(whereCondition);

        List<Produit> result = entityManager.createQuery(selectQuery)
                .getResultList();


        return result;
    }

//    @Override
//    public Collection<Produit> findByNoteWithJPACriteriaBuilder(Integer note) {
//        return null;
//    }


//
//    public Collection<Produit> findAllWithJPACriteriaBuilder(String nom, boolean like, String cat) {
//
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Produit> query = builder.createQuery(Produit.class);
//        Root<Produit> from = query.from(Produit.class);
//
//        CriteriaQuery<Produit> selectQuery = query.select(from);
//
//        if(nom != null){
//
//            ParameterExpression<String> nomParameter = builder.parameter(String.class);
//
//            Predicate whereCondition = null;
//            if(like){
//                whereCondition = builder.like(from.get("nom"),nomParameter);
//            }else{
//                whereCondition = builder.equal(from.get("nom"),nomParameter);
//            }
//
//        }
//
//        if(cat != null){
//
//            Join<Produit, Categorie> joinCategories = from.join("categories");
//
//            ParameterExpression<String> catParameter = builder.parameter(String.class);
//
//            Predicate whereCondition = builder.equal(joinCategories.get("nomCategorie"),catParameter);
//
//            CriteriaQuery<Produit> selectQuery = query.select(from)
//                    .where(whereCondition);
//
//        }
//
//
//        List<Produit> result = entityManager.createQuery(selectQuery).getResultList();
//        return result;
//
//    }
}