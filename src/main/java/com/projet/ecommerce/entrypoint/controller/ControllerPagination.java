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

	@GetMapping("/type/{type}/numPage/{numPage}/numberByPage/{npp}/nom/{nom}/idCategorie/{idCategorie}")
	public PaginationDTO getPaginationProduct(@PathVariable("type") @NotNull String type, @PathVariable("numPage") @NotNull int numPage, @PathVariable("npp") @NotNull int npp, @PathVariable("nom") String nom, @PathVariable("idCategorie") int idCategorie) {
		return paginationBusiness.getPagination(type, numPage, npp, nom, idCategorie);
	}

	@GetMapping("/type/{type}/numPage/{numPage}/numberByPage/{npp}")
	public PaginationDTO getPaginationProduct(@PathVariable("type") @NotNull String type, @PathVariable("numPage") @NotNull int numPage, @PathVariable("npp") @NotNull int npp) {
		return getPaginationProduct(type, numPage, npp, "", 0);
	}

}
