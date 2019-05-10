package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.entrypoint.authentification.AuthData;
import com.projet.ecommerce.entrypoint.authentification.SigninPayload;
import com.projet.ecommerce.entrypoint.authentification.Token;
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
	 * Méthode définissant la pagination
	 *
	 * @param pageNumber la page souhaitée
	 * @param nb         le nombre d'utilisateur à afficher dans la page
	 * @return une page d'utilisateur
	 */
	Page<Utilisateur> getPage(int pageNumber, int nb);

	boolean isLogged(Token token);

	/**
	 * Permet de connecter un utilisateur
	 *
	 * @param auth l'objet contenant les données de connection
	 * @return SigninPayload contenant les informations necessaire à l'authentification
	 */
	SigninPayload signinUser(AuthData auth) throws IllegalAccessException;

	/**
	 * Compte le nombre d'utilisateur
	 *
	 * @return le nombre d'utilisateur
	 */
	Long countUtilisateurs();

}
