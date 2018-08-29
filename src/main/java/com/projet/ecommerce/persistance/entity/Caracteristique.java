package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="caracteristique")
public class Caracteristique {

	@Column(name ="id_caracteristique")
	@Id
	private int idCaracteristique;
	
	@JoinColumn(name = "reference_produit")
	@ManyToOne(fetch=FetchType.LAZY)
	private Produit produit;
	
	@OneToOne
	@JoinColumn(name="id_type_caracteristique")
	private TypeCaracteristique typeCaracteristique;
		
	@Column
	private String valeur;

	public int getIdCaracteristique() {
		return idCaracteristique;
	}

	public void setIdCaracteristique(int idCaracteristique) {
		this.idCaracteristique = idCaracteristique;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public TypeCaracteristique getTypeCaracteristique() {
		return typeCaracteristique;
	}

	public void setTypeCaracteristique(TypeCaracteristique typeCaracteristique) {
		this.typeCaracteristique = typeCaracteristique;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	} 
}
