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
@Table(name = "caracteristique")
public class Caracteristique {
	
	@Id
    @Column(name = "id_caracteristique")
	private int idCaracteristique;
	
	
	@Column(name = "type_caracteristique")
	private TypeCaracteristique typeDeCaracteristique;
	
	@Column(name = "produit")
	private Produit produit;
	
	@NotNull
	@Column(name = "valeur_caracteristique")
	private String valeurCaracteristique;

	/**
	 * @return the idCaracteristique
	 */
	public int getIdCaracteristique() {
		return idCaracteristique;
	}

	/**
	 * @param idCaracteristique the idCaracteristique to set
	 */
	public void setIdCaracteristique(int idCaracteristique) {
		this.idCaracteristique = idCaracteristique;
	}

	/**
	 * @return the typeDeCaracteristique
	 */
	public TypeCaracteristique getTypeDeCaracteristique() {
		return typeDeCaracteristique;
	}

	/**
	 * @param typeDeCaracteristique the typeDeCaracteristique to set
	 */
	public void setTypeDeCaracteristique(TypeCaracteristique typeDeCaracteristique) {
		this.typeDeCaracteristique = typeDeCaracteristique;
	}

	/**
	 * @return the produit
	 */
	public Produit getProduit() {
		return produit;
	}

	/**
	 * @param produit the produit to set
	 */
	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	/**
	 * @return the valeurCaracteristique
	 */
	public String getValeurCaracteristique() {
		return valeurCaracteristique;
	}

	/**
	 * @param valeurCaracteristique the valeurCaracteristique to set
	 */
	public void setValeurCaracteristique(String valeurCaracteristique) {
		this.valeurCaracteristique = valeurCaracteristique;
	}

}
