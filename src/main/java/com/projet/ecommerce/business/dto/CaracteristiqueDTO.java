package com.projet.ecommerce.business.dto;

public class CaracteristiqueDTO {

    private int idCaracteristique;

    private ProduitDTO produit;
    
    private TypeCaracteristiqueDTO typeCaracteristique;
    
    private String value;

	public int getIdCaracteristique() {
		return idCaracteristique;
	}

	public void setIdCaracteristique(int idCaracteristique) {
		this.idCaracteristique = idCaracteristique;
	}

	public ProduitDTO getProduit() {
		return produit;
	}

	public void setProduit(ProduitDTO produit) {
		this.produit = produit;
	}

	public TypeCaracteristiqueDTO getTypeCaracteristique() {
		return typeCaracteristique;
	}

	public void setTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristique) {
		this.typeCaracteristique = typeCaracteristique;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
    
    
    
}
