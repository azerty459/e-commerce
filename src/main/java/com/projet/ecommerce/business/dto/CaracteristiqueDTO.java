package com.projet.ecommerce.business.dto;

public class CaracteristiqueDTO {

    private int idCaracteristique;

    private TypeCaracteristiqueDTO typeCaracteristique;

    private String valeur;

    private ProduitDTO produit;

    public int getIdCaracteristique() {
        return idCaracteristique;
    }

    public void setIdCaracteristique(int idCaracteristique) {
        this.idCaracteristique = idCaracteristique;
    }

    public TypeCaracteristiqueDTO getTypeCaracteristique() {
        return typeCaracteristique;
    }

    public void setTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristique) {
        this.typeCaracteristique = typeCaracteristique;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }
}
