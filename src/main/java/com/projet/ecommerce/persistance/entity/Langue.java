/**
 * 
 */
package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liam
 *
 */
@Entity
@Table(name = "langue")
public class Langue {
	
	/**
	 * Represente l'id de la langue en BD
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_langue")
	private int idLangue;
	
	@Column(name = "langue", unique = true)
	private String langue;

	/**
	 * @return the idLangue
	 */
	public int getIdLangue() {
		return idLangue;
	}

	/**
	 * @param idLangue the idLangue to set
	 */
	public void setIdLangue(int idLangue) {
		this.idLangue = idLangue;
	}

	/**
	 * @return the langue
	 */
	public String getLangue() {
		return langue;
	}

	/**
	 * @param langue the langue to set
	 */
	public void setLangue(String langue) {
		this.langue = langue;
	}
	
	

}
