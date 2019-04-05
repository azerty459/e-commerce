package com.projet.ecommerce.business.dto;

public class CaracteristiqueDTO {
	/**
	 * l'id de la caractéristique
	 */
	private int idCaracteristique;
	/**
	 * la valeur de la caractéristique
	 */
	private String valeur;
	
	private ProduitDTO produitDTO;
	
	private TypeCaracteristiqueDTO typeCaracteristiqueDTO;
	
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

	public ProduitDTO getProduitDTO() {
		return produitDTO;
	}

	public void setProduitDTO(ProduitDTO produitDTO) {
		this.produitDTO = produitDTO;
	}

	public TypeCaracteristiqueDTO getTypeCaracteristiqueDTO() {
		return typeCaracteristiqueDTO;
	}

	public void setTypeCaracteristiqueDTO(TypeCaracteristiqueDTO typeCaracteristiqueDTO) {
		this.typeCaracteristiqueDTO = typeCaracteristiqueDTO;
	}
	
}
