package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type_caracteristique")
public class TypeCaracteristique {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_caracteristique")
    private int idTypeCaracteristique;

	@Column(unique=true)
	private String libelle;

	public int getIdTypeCaracteristique() {
		return idTypeCaracteristique;
	}

	public void setIdTypeCaracteristique(int idTypeCaracteristique) {
		this.idTypeCaracteristique = idTypeCaracteristique;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public boolean equals(Object o) {
		if(this == o){
			return true;
		}
		if(o instanceof TypeCaracteristique) {
			TypeCaracteristique compared = (TypeCaracteristique) o;
			if(compared.getIdTypeCaracteristique() == this.getIdTypeCaracteristique()) {
				return true;
			}
			if(compared.getLibelle().equals(this.getLibelle())) {
				return true;
			}
		}
		return false;
	}
}

