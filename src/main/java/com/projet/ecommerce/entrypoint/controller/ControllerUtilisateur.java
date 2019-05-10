package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/utilisateur")
@CrossOrigin
public class ControllerUtilisateur {

	@Autowired
	private UtilisateurBusiness utilisateurBusiness;

	@GetMapping("/{id}")
	public List<UtilisateurDTO> getUser(@PathVariable @NotNull int id) {

		return utilisateurBusiness.getUtilisateur(id, null, null, null, null);
	}

	@DeleteMapping("delete/{id}")
	public void deleteUser(@PathVariable @NotNull int id) {

		utilisateurBusiness.delete(null, id);
	}

	@PostMapping()
	public UtilisateurDTO createUser(@PathVariable UtilisateurDTO utilisateurDTO) {

		return utilisateurBusiness.add(utilisateurDTO);
	}

	@PutMapping()
	public UtilisateurDTO updateUser(@PathVariable UtilisateurDTO utilisateurDTO) {

		return utilisateurBusiness.update(utilisateurDTO);
	}

}
