package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.persistance.entity.Utilisateur;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface du service ProduitBusiness.
 */

public interface IUtilisateurBusiness {

    /**
     * Méthode définissant l'ajout d'un utilisateur.
     *
     * @param utilisateurDTO L'objet utilisateur à sauvegarder
     * @return l'objet utilisateur ajouté
     */
    UtilisateurDTO add(UtilisateurDTO utilisateurDTO);

    /**
     * Méthode définissant la modification d'un utilisateur.
     *
     * @param utilisateurDTO L'objet utilisateur modifié à sauvegarder
     * @return l'objet utilisateur modifié
     */
    UtilisateurDTO update(UtilisateurDTO utilisateurDTO);

    /**
     * Méthode définissant la suppression d'un utilisateur.
     *
     * @param id    Id de l'utilisateur à supprimer
     * @param email Email de l'utilisateur à supprimer
     * @return true
     */
    boolean delete(String email, int id);

    /**
     * Méthode définissant la recherche des utilisateurs selon les paramètres ci-dessous
     *
     * @param id     l'id de l'utilisateur à rechercher
     * @param email  l'mail de/des utilisateur(s) recherché(s)
     * @param nom    le nom de/des utilisateur(s) recherché(s)
     * @param prenom le prénom de/des utilisateur(s) recherché(s)
     * @param role   le role de/des utilisateur(s) recherché(s)
     * @return la liste des utilisateurs trouvés
     */
    List<UtilisateurDTO> getUtilisateur(int id, String email, String nom, String prenom, String role);

    /**
     * Méthode permettant la connexion d'un utilisateur
     *
     * @param email L'email de l'utilisateur
     * @param mdp   Mot de passe de l'utilisateur
     * @return l'utilisateur connecté
     */
    UtilisateurDTO login(String email, String mdp);

    /**
     * Méthode définissant la pagination
     *
     * @param pageNumber la page souhaitée
     * @param nb         le nombre d'utilisateur à afficher dans la page
     * @return une page d'utilisateur
     */
    Page<Utilisateur> getPage(int pageNumber, int nb);
}
