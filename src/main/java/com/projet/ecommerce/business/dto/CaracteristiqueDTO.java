package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Caracteristique).
 */

public class CaracteristiqueDTO {
	
	/**
     * L'id de la caractéristique dans la base de données/
     */
    private int id;
    
    private TypeCaracteristiqueDTO typeCaracteristique;
    
    private String valeur;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TypeCaracteristiqueDTO getTypeCaracteristique() {
		return typeCaracteristique;
	}

	public void setTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristique) {
		this.typeCaracteristique = typeCaracteristique;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
    
    
}
