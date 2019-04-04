package com.projet.ecommerce.business.dto;

public class TypeCaracteristiqueDTO {
	/**
	 * l'id du type de caractéristique
	 */
	private int idTypeCaracteristique;
	
	/**
	 * le libelle du type de caractéristique
	 */
	private String typeCaracteristique;

	public int getIdTypeCaracteristique() {
		return idTypeCaracteristique;
	}

	public void setIdTypeCaracteristique(int idTypeCaracteristique) {
		this.idTypeCaracteristique = idTypeCaracteristique;
	}

	public String getTypeCaracteristique() {
		return typeCaracteristique;
	}

	public void setTypeCaracteristique(String typeCaracteristique) {
		this.typeCaracteristique = typeCaracteristique;
	}
}
