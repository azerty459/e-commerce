package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import com.projet.ecommerce.exception.InvalidDataException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
	public UtilisateurDTO createUser(@RequestBody UtilisateurDTO utilisateurDTO, BindingResult bindingResult) throws InvalidDataException {
		if (bindingResult.hasErrors()) {
			throw new InvalidDataException("Les données du formulaire sont invalides");
		}
		if (StringUtils.isBlank(utilisateurDTO.getMdp())) {
			throw new InvalidDataException("Le mot de passe ne peut pas être vide");
		}

		UtilisateurDTO retour = utilisateurBusiness.add(utilisateurDTO);
		//UtilisateurDTO retour = utilisateurDTO;
		retour.setMdp("");
		return retour;
	}

	@PutMapping("")
	public UtilisateurDTO updateUser(@RequestBody @Valid UtilisateurDTO utilisateurDTO, BindingResult bindingResult) throws InvalidDataException {
		if (bindingResult.hasErrors()) {
			throw new InvalidDataException("Les données du formulaire sont invalides");
		}

		UtilisateurDTO retour = utilisateurBusiness.update(utilisateurDTO);
		//UtilisateurDTO retour = utilisateurDTO;
		retour.setMdp("");
		return retour;
	}

}
