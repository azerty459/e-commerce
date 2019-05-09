package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.ecommerce.persistance.entity.AvisClient;

/**
 * Interface permettant de communiquer avec la base de données pour la table AvisClient.
 */

@Repository
public interface AvisClientRepository extends CrudRepository<AvisClient, Integer>, AvisClientRepositoryCustom{

    @Override
    Collection<AvisClient> findAll();

    Collection<AvisClient> findByProduit_ReferenceProduit(String refProduit);

}
