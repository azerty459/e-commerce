package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/**
 * Entité représentant la table caracteristique sous forme de classe.
 */

@Entity
@Table(name = "caracteristique")
public class Caracteristique {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_caracteristique", nullable = false)
	private int idCaracteristique;
	
	@Column(unique=true)
	private String libelle;

	public int getIdCaracteristique() {
		return idCaracteristique;
	}

	public void setIdCaracteristique(int idCaracteristique) {
		this.idCaracteristique = idCaracteristique;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
}
