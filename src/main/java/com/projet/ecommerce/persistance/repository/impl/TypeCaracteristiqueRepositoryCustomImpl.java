package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepositoryCustom;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;


@Repository
public class TypeCaracteristiqueRepositoryCustomImpl implements TypeCaracteristiqueRepositoryCustom {

	private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";
	private @Nullable CrudMethodMetadata metadata;
	
    // Requêtes en Java Persistence Query Language
    private static final String SQL_TYPE_CARACTERISTIQUE = "SELECT tc FROM TypeCaracteristique AS tc";

    @Autowired
    private EntityManager entityManager;
    
//	public Optional<TypeCaracteristique> findById(int id) {
//		
//		Query query = null;
//		String sql = SQL_TYPE_CARACTERISTIQUE + " WHERE id_type_caracteristique = " + id;
//		query = entityManager.createQuery(sql, TypeCaracteristique.class);
//		
//		if(query.getSingleResult()==null) {
//			return null;
//		}
//		else {
//			return Optional.of( (TypeCaracteristique) (query.getSingleResult()) );
//		}
//	}
	
	public Optional<TypeCaracteristique> findById(int id) {
		
		Assert.notNull(id, ID_MUST_NOT_BE_NULL);

		return Optional.ofNullable(entityManager.find(TypeCaracteristique.class, id));

	}
	
}