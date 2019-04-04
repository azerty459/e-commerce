package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (TypeCategorie).
 */

public class TypeCaracteristiqueDTO {
	
	/**
     * L'id du type dans la base de données/
     */
    private int id;
    
    /**
     * Le nom du type de caracteristique dans la base de données/
     */
    private String nomType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomType() {
		return nomType;
	}

	public void setNomType(String nomType) {
		this.nomType = nomType;
	}
    
    
}
