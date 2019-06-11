package com.projet.ecommerce.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Produit).
 */
public class ProduitDTO implements Comparable {

	/**
	 * la référence du produit
	 */
	private String ref;
	/**
	 * le nom du produit
	 */
	private String nom;
	/**
	 * description du produit
	 */
	private String description;
	/**
	 * le prix hors taxes du produit
	 */
	private float prixHT;
	/**
	 * La note moyenne des avis clients sur ce produit
	 */
	private float noteMoyenne;
	/**
	 * la liste des catégories auxquelles appartient le produit
	 */
	private List<CategorieDTO> categories;
	/**
	 * la liste des photos du produit
	 */
	private List<PhotoDTO> photos;
	/**
	 * La photo pricnipale
	 */
	private PhotoDTO photoPrincipale;

	/**
	 * Obtenir la référence du produit
	 *
	 * @return la référence du produit
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * Définir la référence du produit
	 *
	 * @param ref la référence du produit
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

	/**
	 * Obtenir le nom du produit
	 *
	 * @return le nom du produit
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Définir le nom du produit
	 *
	 * @param nom le nom du produit
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Obtenir la description du produit
	 *
	 * @return la description du produit
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Définir la description du produit
	 *
	 * @param description la description du produit
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Obtenir le prix hors taxes du produit
	 *
	 * @return le prix hors taxes du produit
	 */
	public float getPrixHT() {
		return prixHT;
	}

	/**
	 * Définir le prix hors taxes du produit
	 *
	 * @param prixHT le prix hors taxes du produit
	 */
	public void setPrixHT(float prixHT) {
		this.prixHT = prixHT;
	}

	/**
	 * Obtenir les catégories aux quelles appartient le produit
	 *
	 * @return la liste des catégories auxquelles appartient le produit
	 */
	@JsonProperty("categories")
	public List<CategorieDTO> getCategories() {
		return categories;
	}

	/**
	 * Définir les catégories auxquelles appartient le produit
	 *
	 * @param categories la liste des catégories auxquelles appartient le produit
	 */
	@JsonProperty("arrayCategorie")
	public void setCategories(List<CategorieDTO> categories) {
		this.categories = categories;
	}


	/**
	 * Obtenir les photos du produit
	 *
	 * @return la liste des photos du produit
	 */
	@JsonProperty("photos")
	public List<PhotoDTO> getPhotos() {
		return photos;
	}

	/**
	 * Définir les photos du produit
	 *
	 * @param photos la liste des photos du produit
	 */
	@JsonProperty("arrayPhoto")
	public void setPhotos(List<PhotoDTO> photos) {
		this.photos = photos;
	}

	/**
	 * Permet d'obtenir la photo principale DTO
	 *
	 * @return
	 */
	public PhotoDTO getPhotoPrincipale() {
		return photoPrincipale;
	}

	/**
	 * Permet de définir la photo principale DTO
	 *
	 * @param photoPrincipale
	 */
	public void setPhotoPrincipale(PhotoDTO photoPrincipale) {
		this.photoPrincipale = photoPrincipale;
	}

	/**
	 * Permet d'obtenir la note moyenne du produit
	 *
	 * @return moyenne
	 */
	public float getNoteMoyenne() {
		return noteMoyenne;
	}

	/**
	 * Permet de définir la note moyenne du produit
	 *
	 * @param d
	 */
	public void setNoteMoyenne(float noteMoyenne) {
		this.noteMoyenne = noteMoyenne;
	}

	@Override
	public int compareTo(Object o) {
		if (this.getClass() == o.getClass()) {
			ProduitDTO produitDTO = (ProduitDTO) o;
			return this.getRef().compareTo(produitDTO.getRef());
		}
		return -1;
	}

}
