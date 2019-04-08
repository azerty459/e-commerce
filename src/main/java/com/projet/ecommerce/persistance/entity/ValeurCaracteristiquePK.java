package com.projet.ecommerce.persistance.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ValeurCaracteristiquePK implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "reference_produit")
	private Produit produit;
	
	@ManyToOne
	@JoinColumn(name = "id_caracteristique")
	private Caracteristique caracteristique;
	
	
	public ValeurCaracteristiquePK() {}
	
	public ValeurCaracteristiquePK(Produit produit, Caracteristique caracteristique) {
		this.produit = produit;
		this.caracteristique = caracteristique;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Caracteristique getCaracteristique() {
		return caracteristique;
	}

	public void setCaracteristique(Caracteristique caracteristique) {
		this.caracteristique = caracteristique;
	}

}
