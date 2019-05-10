package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IUtilisateurBusiness;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.dto.transformer.UtilisateurTransformer;
import com.projet.ecommerce.entrypoint.authentification.AuthData;
import com.projet.ecommerce.entrypoint.authentification.SigninPayload;
import com.projet.ecommerce.entrypoint.authentification.Token;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Role;
import com.projet.ecommerce.persistance.entity.Utilisateur;
import com.projet.ecommerce.persistance.repository.RoleRepository;
import com.projet.ecommerce.persistance.repository.UtilisateurRepository;
import com.projet.ecommerce.utilitaire.AuthUtilitaire;
import org.apache.commons.lang3.StringUtils;
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

import static com.projet.ecommerce.utilitaire.Utilitaire.mergeObjects;

/**
 * Service permettant de gérer les actions effectuées pour les utilisateurs.
 */

@Service
@Transactional
public class UtilisateurBusiness implements IUtilisateurBusiness {

	public static final String MESSAGE_ERREUR_SIGNIN = "Votre mot de passe  ou votre identifiant est incorrect.";
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
		if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
			throw new GraphQLCustomException("L'adresse email déjà utilisée");
		}
		Optional<Role> roleOptional = roleRepository.findByNom(utilisateur.getRole().getNom());
		utilisateur.setRole(roleOptional.get());
		utilisateur.setMdp(passwordEncoder.encode(utilisateurDTO.getMdp()));

		return UtilisateurTransformer.entityToDto(utilisateurRepository.save(utilisateur));
	}


	/**
	 * Modifie l'utilisateur dans la base de données selon l'id de l'utilisateur
	 *
	 * @param utilisateurDTO L'objet utilisateur modifié à sauvegarder
	 * @return l'utilisateur modifié sous forme d'objet : UtilisateurDTO
	 */
	@Override
	public UtilisateurDTO update(UtilisateurDTO utilisateurDTO) {
		if (utilisateurDTO == null) {
			return null;
		}
		if (utilisateurDTO.getId() <= 0 || StringUtils.isBlank(utilisateurDTO.getEmail()) || StringUtils.isBlank(utilisateurDTO.getMdp())) {
			GraphQLCustomException graphQLCustomException = new GraphQLCustomException("Erreur dans l'ajout de l'utilisateur (l'id, l'adresse email ou le mot de passe sont vide)");
			graphQLCustomException.ajouterExtension("Id", "" + utilisateurDTO.getId());
			graphQLCustomException.ajouterExtension("Email", utilisateurDTO.getEmail());
			graphQLCustomException.ajouterExtension("Mdp", utilisateurDTO.getMdp());
			throw graphQLCustomException;
		}
		Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(utilisateurDTO.getId());
		optionalUtilisateur.orElseThrow(() -> new GraphQLCustomException("Impossible de trouver l'utilisateur"));
		Utilisateur utilisateurBDD = optionalUtilisateur.get();
		Utilisateur utilisateur = UtilisateurTransformer.dtoToEntity(utilisateurDTO);
		if (utilisateur.getRole() != null) {
			Optional<Role> optionalRole = roleRepository.findByNom(utilisateur.getRole().getNom());
			optionalRole.ifPresent(utilisateur::setRole);
		}
		utilisateur.setMdp(passwordEncoder.encode(utilisateurDTO.getMdp()));
		utilisateur.setId(utilisateurBDD.getId());
		try {
			Utilisateur utilisateurFusion = mergeObjects(utilisateur, utilisateurBDD);
			return UtilisateurTransformer.entityToDto(utilisateurRepository.save(utilisateurFusion));
		} catch (IllegalAccessException | InstantiationException ex) {
			throw new GraphQLCustomException("L'utilisateur ne peut pas être mis à jour");
		}

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
			utilisateurCollection = utilisateurRepository.findByRole_NomContainingIgnoreCase(role);
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


	@Override
	public SigninPayload signinUser(AuthData auth) {
		String email = auth.getEmail();
		String mdp = auth.getPassword();

		if (email.isEmpty() || mdp.isEmpty()) {
			throw new GraphQLCustomException("Veuillez écrire votre mot de passe ou votre adresse e-mail.");
		}
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
		Utilisateur utilisateur = utilisateurOptional.orElseThrow(() -> new GraphQLCustomException(MESSAGE_ERREUR_SIGNIN));
		if (!passwordEncoder.matches(mdp, utilisateur.getMdp())) {
			throw new GraphQLCustomException(MESSAGE_ERREUR_SIGNIN);
		}
		Token token = new Token();
		token.setUtilisateur(utilisateur);
		// 3 600 000 ms= 60 minutes
		// Le subject est mis par defaut ici
		token.setToken(AuthUtilitaire.createJWT(Integer.toString(utilisateur.getId()), utilisateur.getEmail(), "simple auth", 3600000));
		return new SigninPayload(token);
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
		return utilisateurRepository.findAllByOrderByEmail(page);
	}

	/**
	 * Permet de vérifier si l'utilisateur est connecté
	 *
	 * @param supposedToken le token de connection
	 * @return true si connecté false sinon
	 */
	@Override
	public boolean isLogged(Token supposedToken) {
		Token token = AuthUtilitaire.parseJWT(supposedToken.getToken());
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(token.getIssuer());
		Utilisateur utilisateur = utilisateurOptional.orElseThrow(() -> new GraphQLCustomException("Impossible de trouver un compte correspondant à cette adresse e-mail."));
		token.setUtilisateur(utilisateur);
		// TODO sécuriser en ajoutant une verification que le token n'est pas volé
		return true;

	}

	@Override
	public Long countUtilisateurs() {
		return utilisateurRepository.countUtilisateurs();
	}

}
