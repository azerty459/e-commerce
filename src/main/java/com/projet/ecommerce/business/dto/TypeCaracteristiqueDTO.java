package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (TypeCaracteristique).
 */

public class TypeCaracteristiqueDTO {

    private int id;

    private String type;

    /**
     * Retourne l'id du type caractéristique.
     *
     * @return l'id du type caractéristique
     */
    public int getId() {
        return id;
    }

    /**
     * Remplace l'id du type caractéristique par celui-ci mit en paramètre.
     *
     * @param id Le nouveau ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le type lié à l'objet.
     *
     * @return le type lié à l'objet.
     */

    public String getType() {
        return type;
    }

    /**
     * Remplace le type lié à l'objet par celui-ci mit en paramètre.
     *
     * @param type Le nouveau type de caractéristique
     */
    public void setType(String type) {
        this.type = type;
    }
}
