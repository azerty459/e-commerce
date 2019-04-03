/**
 * 
 */
package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author liam
 *
 */
@Entity
@Table(name = "type_caracteristique")
public class TypeCaracteristique {
	
	@Id
    @Column(name = "id_type_caracteristique")
	private int idTypeCaracteristique;
	
	@NotNull
	@Column(name = "nom_caracteristique")
	private String nomCaracteristique;

	/**
	 * @return the idTypeCaracteristique
	 */
	public int getIdTypeCaracteristique() {
		return idTypeCaracteristique;
	}

	/**
	 * @param idTypeCaracteristique the idTypeCaracteristique to set
	 */
	public void setIdTypeCaracteristique(int idTypeCaracteristique) {
		this.idTypeCaracteristique = idTypeCaracteristique;
	}

	/**
	 * @return the nomCaracteristique
	 */
	public String getNomCaracteristique() {
		return nomCaracteristique;
	}

	/**
	 * @param nomCaracteristique the nomCaracteristique to set
	 */
	public void setNomCaracteristique(String nomCaracteristique) {
		this.nomCaracteristique = nomCaracteristique;
	}
	
	

}
