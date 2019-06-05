package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produit")
@CrossOrigin
public class ControllerProduit {

	@Autowired
	private ProduitBusiness produitBusiness;

	/**
	 * Récupére tout les produits
	 *
	 * @return tout les produits
	 */
	@GetMapping("/all")
	public List<ProduitDTO> getAllProduct() {
		List<ProduitDTO> retour = produitBusiness.getAll();
		return retour;
	}

	/**
	 * Récupére un produit via sa référence
	 *
	 * @param ref
	 * @return un produit ayant ref comme référence
	 */
	@GetMapping("/{ref}")
	public ProduitDTO getProduct(@PathVariable @NotNull String ref) {
		return produitBusiness.getByRef(ref);
	}

	/**
	 * effeace le produit ayant ref comme référence
	 *
	 * @param ref
	 */
	@DeleteMapping("/{ref}")
	public void deleteProduct(@PathVariable @NotNull String ref) {

		produitBusiness.delete(ref);
	}

	@PostMapping("")
	public ProduitDTO createProduct(@RequestBody ProduitDTO produitDTO) {
		List<Integer> listIdCategorie = new ArrayList<>();
		if (produitDTO.getCategories() != null) {
			for (CategorieDTO categorieDTO : produitDTO.getCategories()) {
				listIdCategorie.add(categorieDTO.getId());
			}
		}
		return produitBusiness.add(produitDTO.getRef(), produitDTO.getNom(), produitDTO.getDescription(), produitDTO.getPrixHT(), listIdCategorie);
	}

	@PutMapping("")
	public ProduitDTO updateProduit(@RequestBody ProduitDTO produitDTO) {
		return produitBusiness.update(ProduitTransformer.dtoToEntity(produitDTO));
	}

}
