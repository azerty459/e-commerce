package com.projet.ecommerce.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.projet.ecommerce.business.ITypeCaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;

public class TypeCaracteristiqueBusiness implements ITypeCaracteristiqueBusiness {

	@Autowired
	private TypeCaracteristiqueRepository typeCaracteristiqueRepository;
	
	@Override
	public List<TypeCaracteristiqueDTO> getAll() {
		List<TypeCaracteristiqueDTO> typeCaracteristiqueList = 
				new ArrayList<>(TypeCaracteristiqueTransformer
						.entityToDto(typeCaracteristiqueRepository.findAll()));
		
		return typeCaracteristiqueList;
	}
	
	@Override
	public TypeCaracteristiqueDTO createTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristiqueDto) {
		TypeCaracteristique typeCaracteristique = TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDto);
		
		if(typeCaracteristique.getTypeCaracteristique().isEmpty()) {
			throw new GraphQLCustomException("Le type de caractéristique entrez est vide.");
		}
		
		return TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueRepository.save(typeCaracteristique));
	}
	
	public TypeCaracteristiqueDTO update(TypeCaracteristiqueDTO typeDto) {
		TypeCaracteristique typeCaracteristique = TypeCaracteristiqueTransformer.dtoToEntity(typeDto);
		if(!typeCaracteristiqueRepository.findById(typeDto.getIdTypeCaracteristique()).isPresent()) {
			throw new GraphQLCustomException("Le type de caractéristique n'existe pas.");
		}
		if(typeCaracteristique.getTypeCaracteristique().isEmpty()) {
			throw new GraphQLCustomException("Le type de caractéristique entrez est vide.");
		}
		return TypeCaracteristiqueTransformer.entityToDto(typeCaracteristiqueRepository.save(typeCaracteristique));
		
	}

}
