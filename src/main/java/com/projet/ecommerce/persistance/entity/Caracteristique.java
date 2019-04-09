package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "caracteristique")
public class Caracteristique {

	@EmbeddedId
	private CaracteristiquePK  caracteristiquePk;

	@Column(name = "valeur_caracteristique")
	private String valeurCaracteristique;

	public CaracteristiquePK getCaracteristiquePk() {
		return caracteristiquePk;
	}

	public void setCaracteristiquePk(CaracteristiquePK caracteristiquePk) {
		this.caracteristiquePk = caracteristiquePk;
	}


	public String getValeurCaracteristique() {
		return valeurCaracteristique;
	}

	public void setValeurCaracteristique(String valeurCaracteristique) {
		this.valeurCaracteristique = valeurCaracteristique;
	}

}
