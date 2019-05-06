package com.projet.ecommerce.persistance.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.repository.AvisClientRepositoryCustom;

@Repository
public class AvisClientRepositoryCustomImpl implements AvisClientRepositoryCustom {

    private static final String SQL_AVERAGE_BY_REFERENCE = "SELECT avg(note) FROM AvisClient AS a WHERE a.produit.referenceProduit = :reference GROUP BY a.produit";

    @Autowired
    private EntityManager entityManager;

    @Override
    public float averageByReferenceProduit(String reference) {

        if (reference == null || StringUtils.isBlank(reference)) {
            return 0;
        }

        TypedQuery<Double> query = entityManager.createQuery(SQL_AVERAGE_BY_REFERENCE, Double.class);
        query.setParameter("reference", reference);

        return query.getSingleResult().floatValue();
    }

}
