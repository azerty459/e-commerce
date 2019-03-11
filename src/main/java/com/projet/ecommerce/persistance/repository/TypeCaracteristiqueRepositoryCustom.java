package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.AvisClient;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface permettant de communiquer avec la base de données pour la table TypeCaracteristique.
 */

@Repository
public interface TypeCaracteristiqueRepositoryCustom{

	public Optional<TypeCaracteristique> findById(int id);

}
