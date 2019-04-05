package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "valeur_caracteristique")
public class ValeurCaracteristique {

	
	@EmbeddedId
	private ValeurCaracteristiquePK valeurCaracteristiquePK = new ValeurCaracteristiquePK();
	
	@Column(name = "valeur")
	private String valeur;

	public ValeurCaracteristiquePK getValeurCaracteristiquePK() {
		return valeurCaracteristiquePK;
	}

	public void setVc(ValeurCaracteristiquePK vc) {
		this.valeurCaracteristiquePK = vc;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	
	
}
