package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface permettante de communiquer avec la base de données pour la table Catégorie.
 */

@Repository
public interface UtilisateurRepository extends PagingAndSortingRepository<Utilisateur, Integer> {

    Collection<Utilisateur> findAllByOrderByEmail();

    Optional<Utilisateur> findByEmail(String email);

    void deleteByEmail(String email);

    Collection<Utilisateur> findByEmailContainingIgnoreCaseOrderByEmail(String email);

    Collection<Utilisateur> findByNomContainingIgnoreCaseOrderByNom(String nom);

    Collection<Utilisateur> findByPrenomContainingIgnoreCaseOrderByPrenom(String prenom);

    Collection<Utilisateur> findByRole_NomContainingIgnoreCase(String nom);

    Page<Utilisateur> findAllByOrderByEmail(Pageable pageable);
}
