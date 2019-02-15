package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;


/**
 * Entité représentant la table caracteristique sous forme de classe.
 */

@Entity
@Table(name = "caracteristique")
public class Caracteristique {
	
	
	/**
	 * L'id de la caractéristique
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_carac")
	private int id;
	
	/**
	 * Valeur de la caractéristique
	 */
	@Column
	private String valeur;
	
	
	/**
	 * Le type de la caractéristique
	 * @see TypeCaracteristiqueDTO
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="typecaracteristique")
    private TypeCaracteristiqueDTO typeCaracteristique;
	
	
	/**
	 * L'entité produit possedant cette caractéristique
	 * @see Produit
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "reference_produit")
	private Produit produit;


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


	public Produit getProduit() {
		return produit;
	}


	public void setProduit(Produit produit) {
		this.produit = produit;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.id;
		result = prime * result + ((this.typeCaracteristique == null) ? 0 : this.typeCaracteristique.hashCode());
		result = prime * result + ((this.valeur == null) ? 0 : this.valeur.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass() )
			return false;

		Caracteristique other = (Caracteristique) obj;
		if (this.id != other.id)
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
