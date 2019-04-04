/**
 * 
 */
package com.projet.ecommerce.business.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.projet.ecommerce.business.ITypeCaracteristiqueBusiness;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;

/**
 * @author liam
 *
 */
public class TypeCaracteristiqueBusiness implements ITypeCaracteristiqueBusiness{

	@Autowired
	TypeCaracteristiqueRepository typeCaracteristiqueRepository;

	@Override
	public TypeCaracteristique creerTypeCaracteristique(TypeCaracteristique tc) {
		if(typeCaracteristiqueRepository.findById(tc.getIdTypeCaracteristique()).isPresent())
			throw new GraphQLCustomException("Le Type de caracteristique existe deja !");

		return typeCaracteristiqueRepository.save(tc);
	}

	@Override
	public TypeCaracteristique modifierTypeCaracteristique(TypeCaracteristique tc) {
		Optional<TypeCaracteristique> typeCaracteristiqueOptional = typeCaracteristiqueRepository.findById(tc.getIdTypeCaracteristique());
		if(typeCaracteristiqueOptional.isPresent())
			return typeCaracteristiqueRepository.save(tc);
		return null;
	}

	@Override
	public boolean supprimerTypeCaracteristiqueParId(int id) {
		Optional<TypeCaracteristique> typeCaracteristiqueOptional = typeCaracteristiqueRepository.findById(id);
		if(typeCaracteristiqueOptional.isPresent()) {
			typeCaracteristiqueRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public TypeCaracteristique trouverParId(int id) {
		Optional<TypeCaracteristique> typeCaracteristique = typeCaracteristiqueRepository.findById(id);
		if(typeCaracteristique.isPresent())
			return typeCaracteristique.get();
		return null;
	}

	@Override
	public TypeCaracteristique trouverParNom(String nom) {
		Optional<TypeCaracteristique> typeCaracteristique = typeCaracteristiqueRepository.findByName(nom);
		if(typeCaracteristique.isPresent())
			return typeCaracteristique.get();
		return null;
	}

	@Override
	public Iterable<TypeCaracteristique> recupererTousLesTypes() {
		return typeCaracteristiqueRepository.findAll();
	}

}
