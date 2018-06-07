package com.projet.ecommerce.business.dto;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Cracteristique).
 */

public class CaracteristiqueDTO {

    private TypeCaracteristique typeCaracteristique;

    private String valeur;

    /**
     * Retourne l'objet TypeCaracteristique contenu dans la caracteristique.
     *
     * @return l'objet TypeCaracteristique
     */
    public TypeCaracteristique getTypeCaracteristique() {
        return typeCaracteristique;
    }

    /**
     * Remplace l'objet TypeCaracteristique contenu dans la caracteristique par celui-ci mit en paramètre.
     *
     * @param typeCaracteristique Un objet TypeCaracteristique
     */
    public void setTypeCaracteristique(TypeCaracteristique typeCaracteristique) {
        this.typeCaracteristique = typeCaracteristique;
    }

    /**
     * Retourne la valeur contenu dans la caractéristique.
     *
     * @return la valeur
     */
    public String getValeur() {
        return valeur;
    }

    /**
     * Remplace la valeur contenu dans la caractéristique par celle-ci mit en paramètre.
     *
     * @param valeur La nouvelle valeur
     */
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
