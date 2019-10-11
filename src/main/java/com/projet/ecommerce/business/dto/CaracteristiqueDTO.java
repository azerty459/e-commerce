package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Caracteristique).
 */
public class CaracteristiqueDTO {

    /**
     * la référence du produit
     */
    private String referenceProduit;
    
    /**
     * le type de la caractéristique
     */
    private String type;
   
    /**
     * la valeur de la caractéristique
     */
    private String valeur;

    public String getReferenceProduit() {
        return referenceProduit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
