package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import com.projet.ecommerce.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/utilisateur")
@CrossOrigin
public class ControllerUtilisateur {

	@Autowired
	private UtilisateurBusiness utilisateurBusiness;

	@GetMapping("/{id}")
	public UtilisateurDTO getUser(@PathVariable @NotNull int id) throws AuthException {
		UtilisateurDTO retour = utilisateurBusiness.getUtilisateurById(id);
		retour.setMdp("");
		return retour;
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Administrateur')")
	public void deleteUser(@PathVariable @NotNull int id) throws AuthException {
		utilisateurBusiness.delete(null, id);
	}

	@PostMapping("")
	@PreAuthorize("hasRole('Administrateur')")
	public UtilisateurDTO createUser(@RequestBody UtilisateurDTO utilisateurDTO) throws AuthException {
		UtilisateurDTO retour = utilisateurBusiness.add(utilisateurDTO);
		retour.setMdp("");
		return retour;
	}

	@PutMapping("")
	@PreAuthorize("hasRole('Administrateur')")
	public UtilisateurDTO updateUser(@RequestBody UtilisateurDTO utilisateurDTO) throws AuthException {
		UtilisateurDTO retour = utilisateurBusiness.update(utilisateurDTO);
		retour.setMdp("");
		return retour;
	}

}
