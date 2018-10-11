package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.utilitaire.ProduitFilterCustomUtilitaire;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;


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

    private static final String SQL_WHERE_AVERAGE_LOWER_BOUND_FILTER =
            "(SELECT AVG(ac.note) " +
                    "FROM AvisClient AS ac " +
                    "WHERE p = ac.produit) " +
                    "> :averageLowerBound";
    private static final String SQL_WHERE_AVERAGE_UPPER_BOUND_FILTER =
            "(SELECT AVG(ac.note) " +
                    "FROM AvisClient AS ac " +
                    "WHERE p = ac.produit) " +
                    "< :averageUpperBound";
    private static final String SQL_WHERE_FULL_NAME_FILTER = "p.nom = :name";
    private static final String SQL_WHERE_PART_NAME_FILTER = "p.nom LIKE :partname";
    private static final String SQL_WHERE_SAME_CAT_FILTER = "p.categories IN (" + SQL_PRODUCTS_BY_CATEGORY + ")";

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
    public Collection<Produit> findWithFiltersWithCriteria(ProduitFilterCustomUtilitaire produitFilterCustomUtilitaire) {
        // TODO CriteriaBuilder
        Map<String, Object> paramsToSet = new HashMap<>();

        StringBuilder queryStringBuilder = new StringBuilder(SQL_ALL_PRODUCTS);
        if (!(produitFilterCustomUtilitaire.getAverageLowerBoundFilter() == null && produitFilterCustomUtilitaire.getAverageUpperBoundFilter() == null && produitFilterCustomUtilitaire.getFullNameFilter() == null
                && produitFilterCustomUtilitaire.getPartNameFilter() == null && produitFilterCustomUtilitaire.getSameCatFilter() == null && produitFilterCustomUtilitaire.getSubCatFilter() == null))
        {
            queryStringBuilder.append(" WHERE ");
        }
            StringJoiner queryWhereStringJoiner = new StringJoiner(" AND ");
        // ajout à la requête des filtres optionnels
        if (produitFilterCustomUtilitaire.getAverageLowerBoundFilter() != null) {
            queryWhereStringJoiner.add(SQL_WHERE_AVERAGE_LOWER_BOUND_FILTER);
            paramsToSet.put("averageLowerBound", produitFilterCustomUtilitaire.getAverageLowerBoundFilter() + "");
        }
        if (produitFilterCustomUtilitaire.getAverageUpperBoundFilter() != null) {
            queryWhereStringJoiner.add(SQL_WHERE_AVERAGE_UPPER_BOUND_FILTER);
            paramsToSet.put("averageUpperBound", produitFilterCustomUtilitaire.getAverageUpperBoundFilter() + "");
        }
        if (produitFilterCustomUtilitaire.getFullNameFilter() != null) {
            queryWhereStringJoiner.add(SQL_WHERE_FULL_NAME_FILTER);
            paramsToSet.put("name", produitFilterCustomUtilitaire.getFullNameFilter());
        }
        if (produitFilterCustomUtilitaire.getPartNameFilter() != null) {
            queryWhereStringJoiner.add(SQL_WHERE_PART_NAME_FILTER);
            paramsToSet.put("partname", "%" + produitFilterCustomUtilitaire.getPartNameFilter() + "%");
        }
        if (produitFilterCustomUtilitaire.getSameCatFilter() != null) {
            queryWhereStringJoiner.add(SQL_WHERE_SAME_CAT_FILTER);
            paramsToSet.put("cat", produitFilterCustomUtilitaire.getSameCatFilter());
        }
        // construction de la requête finale
        String finalStringQuery = queryStringBuilder.toString() + queryWhereStringJoiner.toString();
        TypedQuery<Produit> query = entityManager.createQuery(finalStringQuery, Produit.class);
        paramsToSet.forEach(query::setParameter);
        return query.getResultList();
    }

}