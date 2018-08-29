package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type_caracteristique")
public class TypeCaracteristique {

	@Id
	@Column(name="id_type_caracteristique", updatable = false)
	private int idTypeCaracteristique;
	
	@Column(name="type")
	private String type;
	
	public int getIdTypeCaracteristique() {
		return idTypeCaracteristique;
	}

	public void setIdTypeCaracteristique(int idTypeCaracteristique) {
		this.idTypeCaracteristique = idTypeCaracteristique;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	
	
}
