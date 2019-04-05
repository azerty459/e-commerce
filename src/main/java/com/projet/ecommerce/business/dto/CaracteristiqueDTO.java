package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et
 * les objets métier stockés sur le serveur (Caracteristique).
 */

public class CaracteristiqueDTO {

	/**
	 * L'id de la caractéristique dans la base de données/
	 */
	private int id;

	private TypeCaracteristiqueDTO typeCaracteristiqueDto;

	private String valeurCaracteristique;

	private ProduitDTO produitDto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TypeCaracteristiqueDTO getTypeCaracteristiqueDto() {
		return typeCaracteristiqueDto;
	}

	public void setTypeCaracteristiqueDto(TypeCaracteristiqueDTO typeCaracteristiqueDto) {
		this.typeCaracteristiqueDto = typeCaracteristiqueDto;
	}

	public String getValeurCaracteristique() {
		return valeurCaracteristique;
	}

	public void setValeurCaracteristique(String valeurCaracteristique) {
		this.valeurCaracteristique = valeurCaracteristique;
	}

	public ProduitDTO getProduitDto() {
		return produitDto;
	}

	public void setProduitDto(ProduitDTO produitDto) {
		this.produitDto = produitDto;
	}

}
