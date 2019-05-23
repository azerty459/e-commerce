package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import com.projet.ecommerce.entrypoint.authentification.Token;
import com.projet.ecommerce.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/utilisateur")
public class ControllerUtilisateur {

	@Autowired
	private UtilisateurBusiness utilisateurBusiness;

	@GetMapping("/{id}")
	public UtilisateurDTO getUser(@PathVariable @NotNull int id) throws AuthException {
		/*
		if (!authentification(authData)) {
			throw new AuthException("Authentification non reconnu");
		}*/
		UtilisateurDTO retour = utilisateurBusiness.getUtilisateurById(id);
		retour.setMdp("");
		return retour;
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable @NotNull int id) throws AuthException {
		/*
		if (!authentification(authData)) {
			throw new AuthException("Authentification non reconnu");
		}*/
		utilisateurBusiness.delete(null, id);
	}

	@PostMapping
	public UtilisateurDTO createUser(@RequestBody UtilisateurDTO utilisateurDTO) throws AuthException {
		/*
		if (!authentification(authData)) {
			throw new AuthException("Authentification non reconnu");
		}*/
		UtilisateurDTO retour = utilisateurBusiness.add(utilisateurDTO);
		retour.setMdp("");
		return retour;
	}

	@PutMapping
	public UtilisateurDTO updateUser(@RequestBody UtilisateurDTO utilisateurDTO) throws AuthException {
		/*
		if (!authentification(authData)) {
			throw new AuthException("Authentification non reconnu");
		}*/
		UtilisateurDTO retour = utilisateurBusiness.update(utilisateurDTO);
		retour.setMdp("");
		return retour;
	}

	private boolean authentification(String authData) {
		Token supposedToken = new Token();
		supposedToken.setToken(authData);
		return utilisateurBusiness.isLogged(supposedToken);
	}

}
