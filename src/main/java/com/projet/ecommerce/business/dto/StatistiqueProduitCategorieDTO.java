package com.projet.ecommerce.business.dto;

public class StatistiqueProduitCategorieDTO {

	/**
	 * Le nom de la categorie
	 */
	private String categorie;

	/**
	 * Le nombre de produit dans la categorie
	 */
	private long nb;

	public StatistiqueProduitCategorieDTO(String categorie, long nb) {
		this.categorie = categorie;
		this.nb = nb;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public long getNb() {
		return nb;
	}

	public void setNb(long nb) {
		this.nb = nb;
	}

}
