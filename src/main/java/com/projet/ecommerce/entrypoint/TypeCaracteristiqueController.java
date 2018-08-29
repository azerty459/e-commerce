package com.projet.ecommerce.entrypoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.impl.TypeCaracteristiqueBusiness;

@RestController
@RequestMapping(value = "/admin/ecommerce/typeCaracteristique")
public class TypeCaracteristiqueController {
	
	
	@Autowired
	private TypeCaracteristiqueBusiness typeCaracteristiqueBusiness;
	
	@RequestMapping("/getList")
    @ResponseBody
	public List<TypeCaracteristiqueDTO> getTypeCaracteristiqueDTO() {
		return typeCaracteristiqueBusiness.getTypeCaracteristique();
	}
	
	
	

}
