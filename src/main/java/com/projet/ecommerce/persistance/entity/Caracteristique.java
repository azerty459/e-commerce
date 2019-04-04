package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "caracteristique")
public class Caracteristique {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristique")
    private int idCaracteristique;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "id_type_caracteristique")
	private TypeCaracteristique typeCaracteristique;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "reference_produit")
    private Produit produit;
	
	@Column(name = "valeur_caracteristique")
    private String valeurCaracteristique;

	public int getIdCaracteristique() {
		return idCaracteristique;
	}

	public void setIdCaracteristique(int idCaracteristique) {
		this.idCaracteristique = idCaracteristique;
	}

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

	public String getValeurCaracteristique() {
		return valeurCaracteristique;
	}

	public void setValeurCaracteristique(String valeurCaracteristique) {
		this.valeurCaracteristique = valeurCaracteristique;
	}
	
}
