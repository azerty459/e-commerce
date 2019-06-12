package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.impl.CategorieBusiness;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorie")
@CrossOrigin
public class ControllerCategorie {

	@Autowired
	private CategorieBusiness categorieBusiness;

	@GetMapping("/all")
	public List<CategorieDTO> getAllCategorie() {
		return categorieBusiness.getCategorie(0, null, false, false);
	}

	@GetMapping("/{id}")
	public List<CategorieDTO> getCategorieById(@PathVariable @NotNull int id) {
		return categorieBusiness.getCategorie(id, null, false, false);
	}

	@DeleteMapping("/{id}")
	public boolean deleteCategorieById(@PathVariable @NotNull int id) {
		return categorieBusiness.delete(id);

	}

	@PutMapping("/idCategorie/{id}/nomCategorie/{nom}")
	public CategorieDTO updateCategorieDTO(@PathVariable("id") @NotNull int id, @PathVariable("nom") @NotNull String nom) {
		return categorieBusiness.updateCategorie(id, nom);
	}

	@PostMapping("/nom/{nom}")
	public CategorieDTO addCategorieParent(@PathVariable @NotNull String nom) {
		return categorieBusiness.addParent(nom);
	}

	// à changer dans le front

	@PostMapping("/nom/{nom}/enfant/{id}")
	public CategorieDTO addCategorieEnfant(@PathVariable @NotNull("nom") String nom, @PathVariable("id") @NotNull int id) {
		return categorieBusiness.addEnfant(nom, id);
	}

	// à changer dans le front
}
