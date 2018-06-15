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
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service permettant de gérer les actions effectuées pour les utilisateurs.
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

    /**
     * Permets d'ajouter un utilisateur dans la base de données
     *
     * @param utilisateurDTO L'objet utilisateur à sauvegarder
     * @return l'utilisateur créé sous forme d'objet : UtilisateurDTO
     */
    @Override
    public UtilisateurDTO add(UtilisateurDTO utilisateurDTO) {
        if (utilisateurDTO == null) {
            return null;
        }
        Utilisateur utilisateur = UtilisateurTransformer.dtoToEntity(utilisateurDTO);
        if (utilisateur.getEmail().isEmpty() || utilisateur.getMdp().isEmpty()) {
            GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout de l'utilisateur (l'adresse email ou le mot de passe est vide)");
            graphQLCustomException.ajouterExtension("Email", utilisateur.getEmail());
            graphQLCustomException.ajouterExtension("Mdp", utilisateur.getMdp());
            throw graphQLCustomException;
        }
        if (!utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new GraphQLCustomException("L'adresse email déjà utilisée");
        }

        utilisateur.setRoles(completeRoleData(utilisateur.getRoles()));
        utilisateur.setMdp(passwordEncoder.encode(utilisateurDTO.getMdp()));

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
        System.out.println("ok");
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

    /**
     * Modifie l'utilisateur dans la base de données selon l'id de l'utilisateur
     *
     * @param utilisateurDTO L'objet utilisateur modifié à sauvegarder
     * @return l'utilisateur modifié sous forme d'objet : UtilisateurDTO
     */
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

    /**
     * Retoune une liste d'utilisateur selon les pramètres ci-dessous.
     *
     * @param id     l'id de l'utilisateur à rechercher
     * @param email  l'mail de/des utilisateur(s) recherché(s)
     * @param nom    le nom de/des utilisateur(s) recherché(s)
     * @param prenom le prénom de/des utilisateur(s) recherché(s)
     * @param role   le role de/des utilisateur(s) recherché(s)
     * @return une liste d'utilisateur
     */
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
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
            if (utilisateurOptional.isPresent()) {
                utilisateurCollection.add(utilisateurOptional.get());
            } else {
                throw new GraphQLCustomException("L'utilisateur avec l'id recherché, n'a pas été trouvé");
            }
        } else {
            utilisateurCollection = utilisateurRepository.findAllByOrderByEmail();
        }
        return new ArrayList<>(UtilisateurTransformer.entityToDto(utilisateurCollection));
    }

    /**
     * Retourne l'utilisateur connecté où des erreurs graphql selon les erreurs qu'il y a commise, s'il n'y arrive pas
     *
     * @param email L'email de l'utilisateur
     * @param mdp   Mot de passe de l'utilisateur
     * @return l'utilisateur connecté
     */
    @Override
    public UtilisateurDTO login(String email, String mdp) {
        if (email.isEmpty() || mdp.isEmpty()) {
            throw new GraphQLCustomException("Veuillez écrire votre mot de passe ou votre adresse e-mail.");
        }
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
        Utilisateur utilisateur = utilisateurOptional.orElseThrow(() -> new GraphQLCustomException("Impossible de trouver un compte correspondant à cette adresse e-mail."));
        if (!passwordEncoder.matches(mdp, utilisateur.getMdp())) {
            throw new GraphQLCustomException("Votre mot de passe est incorrect.");
        }
        return UtilisateurTransformer.entityToDto(utilisateur);
    }

    /**
     * Retourne une page d'utilisateur.
     *
     * @param pageNumber le page souhaitée
     * @param nb         le nombre d'utilisateur à afficher dans la page
     * @return une page paginée
     */
    @Override
    public Page<Utilisateur> getPage(int pageNumber, int nb) {
        PageRequest page = (pageNumber == 0) ? PageRequest.of(pageNumber, nb) : PageRequest.of(pageNumber - 1, nb);
        return utilisateurRepository.findAll(page);
    }
}
