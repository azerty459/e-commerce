package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.dto.AvisClientDTO;
import com.projet.ecommerce.business.impl.AvisClientBusiness;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/avisClient")
@CrossOrigin
public class ControllerAvisClient {

	@Autowired
	private AvisClientBusiness avisClientBusiness;

	@GetMapping("/all")
	public Collection<AvisClientDTO> getAll() {
		return avisClientBusiness.getAll();
	}

	@GetMapping("/allByRef/{ref}")
	public Collection<AvisClientDTO> getAllByRef(@PathVariable @NotNull String ref) {
		return avisClientBusiness.getAll(ref);
	}

	@PostMapping()
	public AvisClientDTO addAvisClient(@RequestBody AvisClientDTO avisClientDTO) {
		return avisClientBusiness.add(avisClientDTO);
	}

	@PutMapping("/update")
	public AvisClientDTO updateAvisClient(@RequestBody AvisClientDTO avisClientDTO) {
		return avisClientBusiness.update(avisClientDTO);
	}

	@DeleteMapping("/id/{id}")
	public boolean deleteAvisClientById(@PathVariable @NotNull int id) {
		return avisClientBusiness.delete(id);
	}

}