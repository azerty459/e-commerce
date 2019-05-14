package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/utilisateur")
@CrossOrigin
public class ControllerUtilisateur {

	@Autowired
	private UtilisateurBusiness utilisateurBusiness;

	@GetMapping("/{id}")
	public UtilisateurDTO getUser(@PathVariable @NotNull int id) {

		UtilisateurDTO retour = utilisateurBusiness.getUtilisateurById(id);
		retour.setMdp("");
		return retour;
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable @NotNull int id) {

		utilisateurBusiness.delete(null, id);
	}

	@PostMapping("")
	public UtilisateurDTO createUser(@RequestBody UtilisateurDTO utilisateurDTO) {

		UtilisateurDTO retour = utilisateurBusiness.add(utilisateurDTO);
		retour.setMdp("");
		return retour;
	}

	@PutMapping("")
	public UtilisateurDTO updateUser(@RequestBody UtilisateurDTO utilisateurDTO) {

		UtilisateurDTO retour = utilisateurBusiness.update(utilisateurDTO);
		retour.setMdp("");
		return retour;
	}

}
