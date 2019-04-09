package com.projet.ecommerce.business.dto;

import com.projet.ecommerce.persistance.entity.ValeurCaracteristiquePK;

public class ValeurCaracteristiqueDTO {

	
	private ValeurCaracteristiquePK valeurCaracteristiquePK;
	
	private String valeur;

	public ValeurCaracteristiquePK getValeurCaracteristiquePK() {
		return valeurCaracteristiquePK;
	}

	public void setValeurCaracteristiquePK(ValeurCaracteristiquePK valeurCaracteristiquePK) {
		this.valeurCaracteristiquePK = valeurCaracteristiquePK;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	
	
}
