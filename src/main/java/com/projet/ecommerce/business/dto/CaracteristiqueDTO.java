package com.projet.ecommerce.business.dto;

import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

public class CaracteristiqueDTO {
	/**
	 * l'id de la caractéristique
	 */
	private int idCaracteristique;
	/**
	 * la valeur de la caractéristique
	 */
	private String valeur;
	
	private Produit produit;
	
	private TypeCaracteristique typeCaracteristique;
	
	public int getIdCaracteristique() {
		return idCaracteristique;
	}
	
	public void setIdCaracteristique(int idCaracteristique) {
		this.idCaracteristique = idCaracteristique;
	}
	
	public String getValeur() {
		return valeur;
	}
	
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public TypeCaracteristique getTypeCaracteristique() {
		return typeCaracteristique;
	}

	public void setTypeCaracteristique(TypeCaracteristique typeCaracteristique) {
		this.typeCaracteristique = typeCaracteristique;
	}
	
}
