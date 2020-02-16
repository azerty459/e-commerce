package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Caracteristiques).
 */
public class CaracteristiqueDTO {
    //En tant que DTO, les infos necessaires sont le libelle et la valeur
    //C'est a dire que normalement, on saura de quel produit on parle... !
    //XXX - correct?
    private String valeur;

    private LibelleDTO libelle;

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public LibelleDTO getLibelle() {
        return libelle;
    }

    public void setLibelle(LibelleDTO libelle) {
        this.libelle = libelle;
    }

    
}
