package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type_caracteristique")
public class TypeCaracteristique {
	
	@Id
	@Column(name = "id_type_carac")
	private Integer idTypeCarac;
	
	
	@Column()
	private String libelle;


	public Integer getIdTypeCarac() {
		return idTypeCarac;
	}


	public void setIdTypeCarac(Integer idTypeCarac) {
		this.idTypeCarac = idTypeCarac;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
}
