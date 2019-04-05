package com.projet.ecommerce.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.projet.ecommerce.business.ITypeCaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;;

public class TypeCaracteristiqueBusiness implements ITypeCaracteristiqueBusiness {

	@Autowired
	TypeCaracteristiqueRepository typeCaracRepo;
	
	
	@Override
	public List<TypeCaracteristiqueDTO> getAll() {
		// TODO Auto-generated method stub
		return TypeCaracteristiqueTransformer.listEntityToDto(typeCaracRepo.findAll());
	}

	@Override
	public TypeCaracteristiqueDTO create(TypeCaracteristiqueDTO typeCDto) {	
		
		return TypeCaracteristiqueTransformer.entityToDto(typeCaracRepo.save(TypeCaracteristiqueTransformer.dtoToEntity(typeCDto)));
	}

	@Override
	public TypeCaracteristiqueDTO update(TypeCaracteristiqueDTO typeCDto) {
			if(!typeCaracRepo.findById(typeCDto.getIdTypeC()).isPresent())
				 throw new GraphQLCustomException("Le type de caracteristique recherché n'existe pas.");
			else 
				return TypeCaracteristiqueTransformer.entityToDto(typeCaracRepo.save(TypeCaracteristiqueTransformer.dtoToEntity(typeCDto)));

	}

	@Override
	public void delete(TypeCaracteristiqueDTO typeCDto) {
		if(!typeCaracRepo.findById(typeCDto.getIdTypeC()).isPresent())
			 throw new GraphQLCustomException("Le type de caracteristique recherché n'existe pas.");
		else 
			typeCaracRepo.deleteById(typeCDto.getIdTypeC());
	}
	
}
