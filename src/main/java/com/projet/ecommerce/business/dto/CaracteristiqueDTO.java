package com.projet.ecommerce.business.dto;

public class CaracteristiqueDTO {
	
	private int id;
	
	private String valeur;
	
	private TypeCaracteristiqueDTO typeCaracteristique;
	
	private String refProduit;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public TypeCaracteristiqueDTO getTypeCaracteristique() {
		return typeCaracteristique;
	}

	public void setTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristique) {
		this.typeCaracteristique = typeCaracteristique;
	}

	public String getRefProduit() {
		return refProduit;
	}

	public void setRefProduit(String refProduit) {
		this.refProduit = refProduit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.id;
		result = prime * result + ((this.refProduit == null) ? 0 : this.refProduit.hashCode());
		result = prime * result + ((this.typeCaracteristique == null) ? 0 : this.typeCaracteristique.hashCode());
		result = prime * result + ((this.valeur == null) ? 0 : this.valeur.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		
		CaracteristiqueDTO other = (CaracteristiqueDTO) obj;
		if (this.id != other.id)
			return false;
		if (this.refProduit == null) {
			if (other.refProduit != null)
				return false;
		} else if (!this.refProduit.equals(other.refProduit))
			return false;
		if (this.typeCaracteristique != other.typeCaracteristique)
			return false;
		if (this.valeur == null) {
			if (other.valeur != null)
				return false;
		} else if (!this.valeur.equals(other.valeur))
			return false;
		return true;
	}

}
