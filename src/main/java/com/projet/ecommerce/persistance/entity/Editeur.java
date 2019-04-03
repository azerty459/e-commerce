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
@Table(name = "editeur")
public class Editeur {
	
	/**
	 * Represente l'id de l'editeur en BD
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editeur")
	private int idEditeur;
	
	
	/**
	 * Represente le nom de l'editeur
	 */
	@Column(name = "nom_editeur", unique = true)
	private String nomEditeur;


	/**
	 * @return the idEditeur
	 */
	public int getIdEditeur() {
		return idEditeur;
	}


	/**
	 * @param idEditeur the idEditeur to set
	 */
	public void setIdEditeur(int idEditeur) {
		this.idEditeur = idEditeur;
	}


	/**
	 * @return the nomEditeur
	 */
	public String getNomEditeur() {
		return nomEditeur;
	}


	/**
	 * @param nomEditeur the nomEditeur to set
	 */
	public void setNomEditeur(String nomEditeur) {
		this.nomEditeur = nomEditeur;
	}
}
