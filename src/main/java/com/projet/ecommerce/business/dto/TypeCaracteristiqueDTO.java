package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (TypeCaracteristique).
 */
public class TypeCaracteristiqueDTO {
    
    /**
     * le type
     */
	private String type;

	  /**
     * Obtenir le type
     *
     * @return le type
     */
    public String getType() {
        return type;
    }

    /**
     * Définir le type
     *
     * @param type Le type
     */
    public void setType(String type) {
        this.type = type;
    }
}
