package com.projet.ecommerce.persistance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "caracteristique")
public class Caracteristique implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6549983677666199972L;

	
	@Column(name = "valeur")
	private String valeur;

	@EmbeddedId
	private CaracPK caracPK;

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	
	
	public CaracPK getCaracPK() {
		return caracPK;
	}

	public void setCaracPK(CaracPK caracPK) {
		this.caracPK = caracPK;
	}


	@Embeddable
	public static class CaracPK implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2577114152605211165L;

		@ManyToOne
	    protected Produit produit;
	    
		@ManyToOne
	    protected TypeCaracteristique typeC;

	    public CaracPK() {}

	    public CaracPK(Produit p, TypeCaracteristique tc) {
	        this.produit = p;
	        this.typeC = tc;
	    }

		public Produit getProduit() {
			return produit;
		}

		public void setProduit(Produit produit) {
			this.produit = produit;
		}

		public TypeCaracteristique getTypeC() {
			return typeC;
		}

		public void setTypeC(TypeCaracteristique typeC) {
			this.typeC = typeC;
		}
	    
	    
	}
	
}
