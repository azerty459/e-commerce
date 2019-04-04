/**
 * 
 */
package com.projet.ecommerce.business.dto;


/**
 * @author liam
 *
 */
public class CaracteristiqueDTO {
	
	private int idCaracteristique;
	

	private TypeCaracteristiqueDTO typeCaracteristique;
	

	private ProduitDTO produit;
	

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
	 * @return the typeCaracteristique
	 */
	public TypeCaracteristiqueDTO getTypeCaracteristique() {
		return typeCaracteristique;
	}


	/**
	 * @param typeCaracteristique the typeCaracteristique to set
	 */
	public void setTypeCaracteristique(TypeCaracteristiqueDTO typeCaracteristique) {
		this.typeCaracteristique = typeCaracteristique;
	}


	/**
	 * @return the produit
	 */
	public ProduitDTO getProduit() {
		return produit;
	}


	/**
	 * @param produit the produit to set
	 */
	public void setProduit(ProduitDTO produit) {
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
