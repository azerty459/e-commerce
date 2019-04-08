package com.projet.ecommerce.business.dto;

public class ValeurCaracteristiqueDTO {

	private ProduitDTO referenceProduit;
	
	private CaracteristiqueDTO idCaracteristique;
	
	private String valeur;

	public ProduitDTO getReferenceProduit() {
		return referenceProduit;
	}

	public void setReferenceProduit(ProduitDTO referenceProduit) {
		this.referenceProduit = referenceProduit;
	}

	public CaracteristiqueDTO getIdCaracteristique() {
		return idCaracteristique;
	}

	public void setIdCaracteristique(CaracteristiqueDTO idCaracteristique) {
		this.idCaracteristique = idCaracteristique;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	
	
}
