package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface permettante de communiquer avec la base de données pour la table Catégorie.
 */

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {

    @Override
    Collection<Role> findAll();

    Collection<Role> findById(int id);

    Optional<Role> findByNom(String nom);

    Collection<Role> findByNomContainingIgnoreCaseOrderByNom(String nom);
}
