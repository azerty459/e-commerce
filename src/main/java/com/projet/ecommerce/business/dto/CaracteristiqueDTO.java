package com.projet.ecommerce.business.dto;


public class CaracteristiqueDTO {

	private TypeCaracteristiqueDTO typeC;
	
	private ProduitDTO prod;
	
	private String valeur;

	public TypeCaracteristiqueDTO getTypeC() {
		return typeC;
	}

	public void setTypeC(TypeCaracteristiqueDTO typeC) {
		this.typeC = typeC;
	}

	public ProduitDTO getProd() {
		return prod;
	}

	public void setProd(ProduitDTO prod) {
		this.prod = prod;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	
	
	
}
