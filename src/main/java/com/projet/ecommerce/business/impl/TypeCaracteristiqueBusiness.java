package com.projet.ecommerce.business.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.projet.ecommerce.business.ITypeCaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCaracteristiqueTransformer;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;

public class TypeCaracteristiqueBusiness implements ITypeCaracteristiqueBusiness {
	
	@Autowired
	TypeCaracteristiqueRepository typeCaractRepo;

	@Override
	public Collection<TypeCaracteristiqueDTO> getToutLesTypesCaracteristiques() {
		
		return TypeCaracteristiqueTransformer.entityToDto(typeCaractRepo.findAll());
	}

	@Override
	public TypeCaracteristiqueDTO ajouterTypeCaractéristique(String nomType) {
		TypeCaracteristique typeCaracteristique = new TypeCaracteristique();
		typeCaracteristique.setNomTypeCaracteristique(nomType);
		return TypeCaracteristiqueTransformer.entityToDto(typeCaractRepo.save(typeCaracteristique));
	}

	@Override
	public TypeCaracteristiqueDTO modifierTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristiqueDto) {
		TypeCaracteristique typeCaracteristique = TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDto);
		return TypeCaracteristiqueTransformer.entityToDto(typeCaractRepo.save(typeCaracteristique));
	}

	@Override
	public void supprimerTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristiqueDto) {
		TypeCaracteristique typeCaracteristique = TypeCaracteristiqueTransformer.dtoToEntity(typeCaracteristiqueDto);
		typeCaractRepo.delete(typeCaracteristique);

	}

}
