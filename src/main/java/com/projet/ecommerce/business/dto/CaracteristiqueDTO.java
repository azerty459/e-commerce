package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Cracteristique).
 */

public class CaracteristiqueDTO {


    private ProduitDTO produit;
    private TypeCaracteristiqueDTO type;
    private String valeur;

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    /**
     * Retourne l'objet TypeCaracteristique contenu dans la caracteristique.
     *
     * @return l'objet TypeCaracteristique
     */
    public TypeCaracteristiqueDTO getType() {
        return type;
    }

    /**
     * Remplace l'objet TypeCaracteristique contenu dans la caracteristique par celui-ci mit en paramètre.
     *
     * @param type Un objet TypeCaracteristique
     */
    public void setType(TypeCaracteristiqueDTO type) {
        this.type = type;
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
