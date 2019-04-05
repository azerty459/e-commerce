package com.projet.ecommerce.business.dto;


public class ProduitCaracteristiqueDto {
    
    private ProduitDTO produit;
    
    private CaracteristiqueDto caracteristique;
    
    private String valeur;

    public ProduitCaracteristiqueDto(ProduitDTO produit, CaracteristiqueDto caracteristique, String valeur) {
        this.produit = produit;
        this.caracteristique = caracteristique;
        this.valeur = valeur;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    public CaracteristiqueDto getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(CaracteristiqueDto caracteristique) {
        this.caracteristique = caracteristique;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    
}
