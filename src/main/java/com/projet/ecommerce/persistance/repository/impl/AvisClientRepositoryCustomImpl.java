package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.repository.AvisClientRepositoryCustom;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class AvisClientRepositoryCustomImpl implements AvisClientRepositoryCustom {

	private static final String SQL_AVERAGE_BY_REFERENCE = "SELECT avg(note) FROM AvisClient AS a WHERE a.produit.referenceProduit = :reference GROUP BY a.produit";

	@Autowired
	private EntityManager entityManager;

	@Override
	public float averageByReferenceProduit(String reference) {

		if (StringUtils.isBlank(reference)) {
			return 0F;
		}

		TypedQuery<Double> query = entityManager.createQuery(SQL_AVERAGE_BY_REFERENCE, Double.class);
		query.setParameter("reference", reference);

		try {
			return query.getSingleResult().floatValue();
		} catch (NoResultException ex) {
			return 0F;
		}
	}

}
