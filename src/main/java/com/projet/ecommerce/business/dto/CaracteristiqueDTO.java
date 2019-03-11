package com.projet.ecommerce.business.dto;

import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.persistance.entity.Produit;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Categorie).
 */
public class CaracteristiqueDTO {

	private int id;
	
	private String valeur;
	
	private TypeCaracteristiqueDTO typeCaracteristiqueDTO;

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public TypeCaracteristiqueDTO getTypeCaracteristiqueDTO() {
		return typeCaracteristiqueDTO;
	}

	public void setTypeCaracteristiqueDTO(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {
		this.typeCaracteristiqueDTO = typeCaracteristiqueDTO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
