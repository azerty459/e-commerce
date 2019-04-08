package com.projet.ecommerce.persistance.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "valeur_caracteristique")
public class ValeurCaracteristique {
	
	@EmbeddedId
	ValeurCaracteristiquePK valeurCaracteristiquePK;
	
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
