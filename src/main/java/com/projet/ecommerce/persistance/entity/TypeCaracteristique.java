package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entité représentant la table type_caracteristique sous forme de classe.
 */

@Entity
@Table(name = "type_caracteristique")
public class TypeCaracteristique {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_caracteristique")
    private int idTypeCaracteristique;
	
	@Column(name = "nom_type_caracteristique")
	private String nomTypeCaracteristique;

	public int getIdTypeCaracteristique() {
		return idTypeCaracteristique;
	}

	public void setIdTypeCaracteristique(int idTypeCaracteristique) {
		this.idTypeCaracteristique = idTypeCaracteristique;
	}

	public String getNomTypeCaracteristique() {
		return nomTypeCaracteristique;
	}

	public void setNomTypeCaracteristique(String nomTypeCaracteristique) {
		this.nomTypeCaracteristique = nomTypeCaracteristique;
	}
	
	
}
