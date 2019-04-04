/**
 * 
 */
package com.projet.ecommerce.persistance.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

/**
 * @author liam
 *
 */
@Repository
public interface TypeCaracteristiqueRepository extends PagingAndSortingRepository<TypeCaracteristique, String>{
	
	Optional<TypeCaracteristique> findById(int ref);
	
	Optional<TypeCaracteristique> findByName(String name);
}
