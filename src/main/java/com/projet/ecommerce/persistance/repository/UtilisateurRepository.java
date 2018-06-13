package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Utilisateur;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface permettante de communiquer avec la base de données pour la table Catégorie.
 */

@Repository
public interface UtilisateurRepository extends PagingAndSortingRepository<Utilisateur, Integer> {

    @Override
    Collection<Utilisateur> findAll();

    Utilisateur findById(int id);

    Utilisateur findByEmail(String email);

    void deleteByEmail(String email);

    Collection<Utilisateur> findByEmailContainingIgnoreCaseOrderByEmail(String email);

    Collection<Utilisateur> findByNomContainingIgnoreCaseOrderByNom(String nom);

    Collection<Utilisateur> findByPrenomContainingIgnoreCaseOrderByPrenom(String prenom);

    Collection<Utilisateur> findByRoles_NomContainingIgnoreCase(String nom);
}
