package com.projet.ecommerce.business.dto;

public class CaracteristiqueDTO {
	
	private int id;
	
	private String valeur;
	
	private TypeCaracteristiqueDTO type;
	
	private String refProduit;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public TypeCaracteristiqueDTO getType() {
		return type;
	}

	public void setType(TypeCaracteristiqueDTO type) {
		this.type = type;
	}

	public String getRefProduit() {
		return refProduit;
	}

	public void setRefProduit(String refProduit) {
		this.refProduit = refProduit;
	}
	
	
	

}
