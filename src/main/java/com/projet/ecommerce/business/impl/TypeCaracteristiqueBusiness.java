package com.projet.ecommerce.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ecommerce.business.ITypeCaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;

@Service
public class TypeCaracteristiqueBusiness implements ITypeCaracteristiqueBusiness{

	@Autowired
	TypeCaracteristiqueRepository typeCaracteristiqueRepostitory;
	
	@Override
	public List<TypeCaracteristiqueDTO> getAll() {
		return new ArrayList<TypeCaracteristiqueDTO>(
				TypeCaracteristiqueTransformer.entitiesToDto(
						typeCaracteristiqueRepostitory.findAll()));
	}
	
	@Override
	public TypeCaracteristiqueDTO create(TypeCaracteristiqueDTO typeCaracDto) {
		TypeCaracteristique type = TypeCaracteristiqueTransformer.dtoToEntity(typeCaracDto);
		
		if(type.getLibelle().isEmpty()) {
			throw new GraphQLCustomException("Un type de caracteristique ne peut avoir une libelle vide.");
		}
		
		return TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueRepostitory.save(type));
	}
	
	public void delete(TypeCaracteristiqueDTO typeDto){
		if(!typeCaracteristiqueRepostitory.findById(typeDto.getIdTypeCaracteristique()).isPresent()) {
			throw new GraphQLCustomException("Le type de caracteristique n'existe pas.");
		}
		typeCaracteristiqueRepostitory.deleteById(TypeCaracteristiqueTransformer.dtoToEntity(typeDto).getIdTypeCaracteristique());
	}
	
	public TypeCaracteristiqueDTO update(TypeCaracteristiqueDTO typeDto) {
		TypeCaracteristique type = TypeCaracteristiqueTransformer.dtoToEntity(typeDto);
		if(!typeCaracteristiqueRepostitory.findById(typeDto.getIdTypeCaracteristique()).isPresent()) {
			throw new GraphQLCustomException("Le type de caracteristique n'existe pas.");
		}
		if(type.getLibelle().isEmpty()) {
			throw new GraphQLCustomException("Un type de caracteristique ne peut avoir une libelle vide.");
		}
		return TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueRepostitory.save(type));
		
	}

}
