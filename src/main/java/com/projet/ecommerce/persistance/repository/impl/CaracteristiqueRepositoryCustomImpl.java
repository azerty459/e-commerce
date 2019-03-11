package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepositoryCustom;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;


@Repository
public class CaracteristiqueRepositoryCustomImpl implements CaracteristiqueRepositoryCustom {

    // Requêtes en Java Persistence Query Language
    private static final String SQL_ALL_CARACTERISTIQUE = "SELECT c FROM Caracteristique AS c";

    @Autowired
    private EntityManager entityManager;
    
	public Collection<Caracteristique> findByProduit(Produit produit) {
		Query query = null;
		String sql = SQL_ALL_CARACTERISTIQUE + " WHERE reference_produit = " + produit.getReferenceProduit();
		query = entityManager.createQuery(sql, TypeCaracteristique.class);
    	return query.getResultList();
	}
}