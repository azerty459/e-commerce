package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.CategorieSupprimeBusiness;
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

	@Autowired
	private CategorieSupprimeBusiness categorieSupprimeBusiness;

	@GetMapping("/all")
	public List<CategorieDTO> getAllCategorie() {
		return categorieBusiness.getCategorie(0, null, false, false);
	}

	@GetMapping("/{id}")
	public List<CategorieDTO> getCategorieById(@PathVariable @NotNull int id) {
		return categorieBusiness.getCategorie(id, null, true, false);
	}

	@GetMapping("/nom/{nom}")
	public List<CategorieDTO> getSubCategory(@PathVariable @NotNull String nom) {
		return categorieBusiness.getCategorie(0, nom, false, false);
	}

	@DeleteMapping("/{id}")
	public boolean deleteCategorieById(@PathVariable @NotNull int id) {
		return categorieBusiness.delete(id);

	}

	@PutMapping("/nom/{nom}/id/{id}")
	public CategorieDTO updateCategorieDTO(@PathVariable("id") @NotNull int id, @PathVariable("nom") @NotNull String nom) {
		return categorieBusiness.updateCategorie(id, nom);
	}

	@PutMapping("idADeplacer/{idADeplacer}/idNouveauParent/{idNouveauParent}")
	public boolean moveCategorie(@PathVariable("idADeplacer") @NotNull int idADeplacer, @PathVariable("idNouveauParent") @NotNull int idNouveauParent) {
		return categorieBusiness.moveCategorie(idADeplacer, idNouveauParent);
	}

	@PutMapping("lastIdCategorieDeleted/{id}")
	public List<CategorieDTO> restoreCategorie(@PathVariable @NotNull int id) {
		return categorieSupprimeBusiness.restoreLastDeletedCategorie(id);
	}

	@PostMapping("/nom/{nom}")
	public CategorieDTO addCategorieParent(@PathVariable @NotNull String nom) {
		return categorieBusiness.addParent(nom);
	}

	@PostMapping("/nom/{nom}/idEnfant/{id}")
	public CategorieDTO addCategorieEnfant(@PathVariable @NotNull("nom") String nom, @PathVariable("id") @NotNull int id) {
		return categorieBusiness.addEnfant(nom, id);
	}

}
