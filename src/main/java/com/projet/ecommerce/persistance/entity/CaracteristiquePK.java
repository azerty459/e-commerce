package com.projet.ecommerce.persistance.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CaracteristiquePK implements Serializable {

	private static final long serialVersionUID = 7612836088102921172L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_caracteristique")
	private TypeCaracteristique typeCaracteristique;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reference_produit")
	private Produit produit;

	public TypeCaracteristique getTypeCaracteristique() {
		return typeCaracteristique;
	}

	public void setTypeCaracteristique(TypeCaracteristique typeCaracteristique) {
		this.typeCaracteristique = typeCaracteristique;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

}