package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class TypeCaracteristique {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_caracteristique")
    private int idTypeCaracteristique;
	
	@NotNull
	@Column(name = "type_caracteristique")
    private String typeCaracteristique;

	public int getIdTypeCaracteristique() {
		return idTypeCaracteristique;
	}

	public void setIdTypeCaracteristique(int idTypeCaracteristique) {
		this.idTypeCaracteristique = idTypeCaracteristique;
	}

	public String getTypeCaracteristique() {
		return typeCaracteristique;
	}

	public void setTypeCaracteristique(String typeCaracteristique) {
		this.typeCaracteristique = typeCaracteristique;
	}
	
}
