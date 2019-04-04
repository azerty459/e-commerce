package com.projet.ecommerce.business.dto;

public class TypeCaracteristiqueDTO {
	
    private int idTypeCaracteristique;

	private String libelle;

	
	public int getIdTypeCaracteristique() {
		return idTypeCaracteristique;
	}


	public void setIdTypeCaracteristique(int idTypeCaracteristique) {
		this.idTypeCaracteristique = idTypeCaracteristique;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public boolean equals(Object o) {
		if(this == o){
			return true;
		}
		if(o instanceof TypeCaracteristiqueDTO) {
			TypeCaracteristiqueDTO compared = (TypeCaracteristiqueDTO) o;
			if(compared.getIdTypeCaracteristique() == this.getIdTypeCaracteristique()) {
				return true;
			}
			if(compared.getLibelle().equals(this.getLibelle())) {
				return true;
			}
		}
		return false;
	}
}

