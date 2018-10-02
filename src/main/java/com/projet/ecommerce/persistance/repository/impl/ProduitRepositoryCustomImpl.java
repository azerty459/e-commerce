package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;


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
                    "INNER JOIN Produit p2 ON p2.referenceProduit = ac.produit.referenceProduit " +
                    "GROUP BY (note) " +
                    "HAVING p.referenceProduit = p2.referenceProduit) " +
                    "> :averageLowerBound";
    private static final String SQL_WHERE_AVERAGE_UPPER_BOUND_FILTER =
            "(SELECT AVG(ac.note) " +
                    "FROM AvisClient AS ac " +
                    "INNER JOIN Produit p2 ON p2.referenceProduit = ac.produit.referenceProduit " +
                    "GROUP BY (note) " +
                    "HAVING p.referenceProduit = p2.referenceProduit) " +
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
    public Collection<Produit> findWithFiltersWithCriteria(Float averageLowerBoundFilter,
                                                           Float averageUpperBoundFilter,
                                                           String fullNameFilter,
                                                           String partNameFilter,
                                                           String sameCatFilter,
                                                           String subCatFilter) {
        Collection<Produit> produits;
        Map<String, Object> paramsToSet = new HashMap<>();

        StringBuilder queryStringBuilder = new StringBuilder(SQL_ALL_PRODUCTS);
        if (!(averageLowerBoundFilter == null && averageUpperBoundFilter == null && fullNameFilter == null
                && partNameFilter == null && sameCatFilter == null && subCatFilter == null))
        {
            queryStringBuilder.append(" WHERE ");
        }
            StringJoiner queryWhereStringJoiner = new StringJoiner(" AND ");
        // ajout à la requête des filtres optionnels
        if (averageLowerBoundFilter != null) {
            queryWhereStringJoiner.add(SQL_WHERE_AVERAGE_LOWER_BOUND_FILTER);
            paramsToSet.put("averageLowerBound", averageLowerBoundFilter + "");
        }
        if (averageUpperBoundFilter != null) {
            queryWhereStringJoiner.add(SQL_WHERE_AVERAGE_UPPER_BOUND_FILTER);
            paramsToSet.put("averageUpperBound", averageUpperBoundFilter + "");
        }
        if (fullNameFilter != null) {
            queryWhereStringJoiner.add(SQL_WHERE_FULL_NAME_FILTER);
            paramsToSet.put("name", fullNameFilter);
        }
        if (partNameFilter != null) {
            queryWhereStringJoiner.add(SQL_WHERE_PART_NAME_FILTER);
            paramsToSet.put("partname", "%" + partNameFilter + "%");
        }
        if (sameCatFilter != null) {
            queryWhereStringJoiner.add(SQL_WHERE_SAME_CAT_FILTER);
            paramsToSet.put("cat", sameCatFilter);
        }
        // construction de la requête finale
        String finalStringQuery = queryStringBuilder.toString() + queryWhereStringJoiner.toString();
        Query query = entityManager.createQuery(finalStringQuery, Produit.class);
        paramsToSet.forEach(query::setParameter);
        produits = query.getResultList();

        return produits;
    }

}