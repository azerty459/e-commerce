package com.projet.ecommerce.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;

@Service
@Transactional
public class TypeCaracteristiqueBusiness {
	
	@Autowired
	private TypeCaracteristiqueRepository typeCaracteristiqueRepository;
	
	public List<TypeCaracteristiqueDTO> getTypeCaracteristique() {
		return new ArrayList<>(TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueRepository.findAll()));
	}

}
