package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/**
 * Entité représentant la table caracteristique sous forme de classe.
 */

@Entity
@Table(name = "caracteristique")
public class Caracteristique {

	@Id
	@Column(name = "id_caracteristique")
	private String idCaracteristique;
	
	@Column(name = "libelle")
	private String libelle;
	
	public String getIdCaracteristique() {
		return idCaracteristique;
	}

	public void setIdCaracteristique(String idCaracteristique) {
		this.idCaracteristique = idCaracteristique;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
}
