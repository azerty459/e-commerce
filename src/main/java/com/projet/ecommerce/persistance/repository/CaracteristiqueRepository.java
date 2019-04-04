/**
 * 
 */
package com.projet.ecommerce.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.projet.ecommerce.persistance.entity.Caracteristique;

/**
 * @author liam
 *
 */
@Repository
public interface CaracteristiqueRepository extends PagingAndSortingRepository<Caracteristique, Integer>{

	Optional<Caracteristique> findById(int ref);
	
	Optional<Caracteristique> findByValeur(String valeur);
	
}
