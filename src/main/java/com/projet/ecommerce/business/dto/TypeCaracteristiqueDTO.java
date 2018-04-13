package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (TypeCaracteristique).
 */

public class TypeCaracteristiqueDTO {

    private int idTypeCaracteristique;

    private String type;

    /**
     * Retourne l'id du type caractéristique.
     * @return l'id du type caractéristique
     */
    public int getIdTypeCaracteristique() {
        return idTypeCaracteristique;
    }

    /**
     * Remplace l'id du type caractéristique par celui-ci mit en paramètre.
     * @param idTypeCaracteristique
     */
    public void setIdTypeCaracteristique(int idTypeCaracteristique) {
        this.idTypeCaracteristique = idTypeCaracteristique;
    }

    /**
     * Retourne le type lié à l'objet.
     * @return le type lié à l'objet.
     */

    public String getType() {
        return type;
    }

    /**
     * Remplace le type lié à l'objet par celui-ci mit en paramètre.
     * @param type Le nouveau type de caractéristique
     */
    public void setType(String type) {
        this.type = type;
    }
}
