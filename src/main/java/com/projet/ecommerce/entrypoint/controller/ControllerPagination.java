package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.PaginationDTO;
import com.projet.ecommerce.business.impl.PaginationBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pagination")
@CrossOrigin
public class ControllerPagination {


	@Autowired
	private PaginationBusiness paginationBusiness;

	@GetMapping("/type/{type}/numPage/{numPage}/numberByPage/{npp}/nom/{nom}/idCategorie/{idCategorie}/orderBy/{name}")
	public PaginationDTO getPaginationProduct(@PathVariable("type") @NotNull String type, @PathVariable("numPage") @NotNull int numPage, @PathVariable("npp") @NotNull int npp, @PathVariable("nom") String nom, @PathVariable("idCategorie") int idCategorie, @PathVariable("name") @NotNull String name) {
		PaginationDTO paginationDTO = paginationBusiness.getPagination(type, numPage, npp, nom, idCategorie);
		paginationDTO.setProduits(paginationBusiness.getProduitOrderBy(paginationDTO.getProduits(), name));
		return paginationDTO;
	}

	@GetMapping("/type/{type}/numPage/{numPage}/numberByPage/{npp}/orderBy/{name}")
	public PaginationDTO getPaginationProduct(@PathVariable("type") @NotNull String type, @PathVariable("numPage") @NotNull int numPage, @PathVariable("npp") @NotNull int npp, @PathVariable("name") String name) {
		return getPaginationProduct(type, numPage, npp, "", 0, name);
	}

}
