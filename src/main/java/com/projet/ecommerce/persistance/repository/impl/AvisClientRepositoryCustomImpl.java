package com.projet.ecommerce.persistance.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.projet.ecommerce.persistance.repository.AvisClientRepositoryCustom;


@Repository
public class AvisClientRepositoryCustomImpl implements AvisClientRepositoryCustom {

	private static final String SQL_AVERAGE_BY_REFERENCE = "SELECT avg(note) FROM AvisClient AS a WHERE a.produit.referenceProduit = :reference GROUP BY a.produit";
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public double averageByReferenceProduit(String reference) {
		
		TypedQuery<Double> query = null;
		
		if (reference.equals("")) {
			return 0;
		}
		
		query = entityManager.createQuery(SQL_AVERAGE_BY_REFERENCE,Double.class);
		query.setParameter("reference", reference);
		
		return query.getSingleResult();
	}

}
