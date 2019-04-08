package com.projet.ecommerce.business.dto;


public class ProduitCaracteristiqueDTO {
    
    private CaracteristiqueDTO caracteristique;
    
    private String valeur;

    public ProduitCaracteristiqueDTO(CaracteristiqueDTO caracteristique, String valeur) {
        this.caracteristique = caracteristique;
        this.valeur = valeur;
    }

    public CaracteristiqueDTO getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(CaracteristiqueDTO caracteristique) {
        this.caracteristique = caracteristique;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    
}
