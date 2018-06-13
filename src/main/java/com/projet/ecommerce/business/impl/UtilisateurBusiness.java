package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IUtilisateurBusiness;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.dto.transformer.UtilisateurTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Role;
import com.projet.ecommerce.persistance.entity.Utilisateur;
import com.projet.ecommerce.persistance.repository.RoleRepository;
import com.projet.ecommerce.persistance.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service permettant de gérer les actions effectuées pour les produits.
 */

@Service
@Transactional
public class UtilisateurBusiness implements IUtilisateurBusiness {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UtilisateurDTO add(UtilisateurDTO utilisateurDTO) {
        if (utilisateurDTO == null) {
            return null;
        }
        Utilisateur utilisateur = UtilisateurTransformer.dtoToEntity(utilisateurDTO);
        if (utilisateur.getEmail().isEmpty() && utilisateur.getMdp().isEmpty()) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout de l'utilisateur (l'adresse email ou le mot de passe est vide)");
            graphQLCustomException.ajouterExtension("Email", utilisateur.getEmail());
            graphQLCustomException.ajouterExtension("Mdp", utilisateur.getMdp());
            throw graphQLCustomException;
        }
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()) != null) {
            throw new GraphQLCustomException("L'adresse email déjà utilisée");
        }

        utilisateur.setMdp(passwordEncoder.encode(utilisateurDTO.getMdp()));
        utilisateur.setRoles(completeRoleData(utilisateur.getRoles()));

        return UtilisateurTransformer.entityToDto(utilisateurRepository.save(utilisateur));
    }

    /**
     * Remplace la liste de rôle par une nouvelle liste contenant l'ensemble des données des rôles.
     *
     * @param roleList Une array list contenant des nom de rôle
     * @return une collection de rôle
     */
    private Collection<Role> completeRoleData(Collection<Role> roleList) {
        List<Role> retourList = new ArrayList<>();
        for (Role role : roleList) {
            Optional<Role> roleOptional = roleRepository.findByNom(role.getNom());
            if (roleOptional.isPresent()) {
                retourList.add(roleOptional.get());
            } else {
                throw new GraphQLCustomException("Le rôle n'existe pas.");
            }
        }
        return retourList;
    }

    @Override
    public UtilisateurDTO update(UtilisateurDTO utilisateurDTO) {
        return null;
    }

    @Override
    public boolean delete(String email, int id) {
        if (id != 0) {
            utilisateurRepository.deleteById(id);
        } else {
            utilisateurRepository.deleteByEmail(email);
        }
        return true;
    }

    @Override
    public List<UtilisateurDTO> getUtilisateur(int id, String email, String nom, String prenom, String role) {

        Collection<Utilisateur> utilisateurCollection = new ArrayList<>();

        // On va chercher les catégories
        if (email != null) {
            utilisateurCollection = utilisateurRepository.findByEmailContainingIgnoreCaseOrderByEmail(email);
        } else if (nom != null) {
            utilisateurCollection = utilisateurRepository.findByNomContainingIgnoreCaseOrderByNom(nom);
        } else if (prenom != null) {
            utilisateurCollection = utilisateurRepository.findByPrenomContainingIgnoreCaseOrderByPrenom(prenom);
        } else if (role != null) {
            utilisateurCollection = utilisateurRepository.findByRoles_NomContainingIgnoreCase(role);
        } else if (id != 0) {
            utilisateurCollection.add(utilisateurRepository.findById(id));
        } else {
            utilisateurCollection = utilisateurRepository.findAll();
        }
        return new ArrayList<>(UtilisateurTransformer.entityToDto(utilisateurCollection));
    }

    @Override
    public UtilisateurDTO login(String email, String mdp) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (email.isEmpty() || mdp.isEmpty()) {
            throw new GraphQLCustomException("Veuillez écrire votre mot de passe ou votre adresse e-mail.");
        }
        if (utilisateur == null) {
            throw new GraphQLCustomException("Impossible de trouver un compte correspondant à cette adresse e-mail.");
        }
        String mdpHashe = passwordEncoder.encode(mdp);
        System.out.println("Mdp hashé : " + mdpHashe + "Mdp utilisateur :" + utilisateur.getMdp());
        System.out.println(passwordEncoder.matches(mdp, utilisateur.getMdp()));
        if (!passwordEncoder.matches(mdp, utilisateur.getMdp())) {
            throw new GraphQLCustomException("Votre mot de passe est incorrect.");
        }
        return UtilisateurTransformer.entityToDto(utilisateur);
    }

    @Override
    public Page<Utilisateur> getPage(int pageNumber, int nb) {
        return null;
    }
}
