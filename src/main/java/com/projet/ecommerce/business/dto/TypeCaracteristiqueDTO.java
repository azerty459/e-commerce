package com.projet.ecommerce.business.dto;

import java.util.Collection;
import java.util.List;

import com.projet.ecommerce.persistance.entity.Produit;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Categorie).
 */
public class TypeCaracteristiqueDTO {

	private int id;
	
	private String typeCarac;

	public String getTypeCarac() {
		return typeCarac;
	}

	public void setTypeCarac(String typeCarac) {
		this.typeCarac = typeCarac;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
