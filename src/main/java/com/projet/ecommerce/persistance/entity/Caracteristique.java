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
	
//	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_caracteristique")
	private int idCaracteristique;

//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//	@Column(name = "type_caracteristique")
//	private TypeCaracteristique typeCaracteristique;

	@Column(name = "valeur_caracteristique")
	private String valeurCaracteristique;

//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "reference_produit")
//    private Produit produit;

	
	public int getIdCaracteristique() {
		return idCaracteristique;
	}

	public CaracteristiquePK getCaracteristiquePk() {
		return caracteristiquePk;
	}

	public void setCaracteristiquePk(CaracteristiquePK caracteristiquePk) {
		this.caracteristiquePk = caracteristiquePk;
	}

	public void setIdCaracteristique(int idCaracteristique) {
		this.idCaracteristique = idCaracteristique;
	}

	public String getValeurCaracteristique() {
		return valeurCaracteristique;
	}

	public void setValeurCaracteristique(String valeurCaracteristique) {
		this.valeurCaracteristique = valeurCaracteristique;
	}

}
