package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "caracteristique")
public class Caracteristique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristique")
    private int idCaracteristique;

    @ManyToOne
    @JoinColumn(name = "reference_produit")
    private Produit produit;
    
    @ManyToOne
    @JoinColumn(name="id_type_caracteristique")
    private TypeCaracteristique typeCaracteristique;
    
    private String value;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
    
    
}
