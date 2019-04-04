/**
 * 
 */
package com.projet.ecommerce.business;

import org.springframework.stereotype.Service;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;


/**
 * @author liam
 *
 */
@Service
public interface ITypeCaracteristiqueBusiness {
	
	public TypeCaracteristique creerTypeCaracteristique(TypeCaracteristique tc);
	
	public TypeCaracteristique modifierTypeCaracteristique(TypeCaracteristique tc);
	
	public boolean supprimerTypeCaracteristiqueParId(int id);
	
	public TypeCaracteristique trouverParId(int id);
	
	public TypeCaracteristique trouverParNom(String nom);
	
	public Iterable<TypeCaracteristique> recupererTousLesTypes();
}
