package com.projet.ecommerce.persistance.entity;

import javax.persistence.*;

/**
 * Entité représentant la table photo sous forme de classe.
 */

@Entity
@Table(name = "photo")
public class Photo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_photo")
	private int idPhoto;

	/**
	 * L'URL de la photo, incluant en nom de photo le MD5 du fichier.
	 */
	@Column
	private String url;

	/**
	 * Nom du fichier téléchargé par l'utilisateur (nom originel, qui sera affiché sur le front-end)
	 */
	@Column(name = "nom")
	private String nom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reference_produit")
	private Produit produit;

	/**
	 * Retourne l'id de la photo.
	 *
	 * @return l'id de la photo.
	 */
	public int getIdPhoto() {
		return idPhoto;
	}

	/**
	 * Remplace l'id de la photo par celui-ci mit en paramètre.
	 *
	 * @param idPhoto Le nouveau id de la photo
	 */
	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}

	/**
	 * Retourne l'url liée à la photo.
	 *
	 * @return l'url liée à la photo.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Remplace l'url de la photo par celle-ci mit en paramètre.
	 *
	 * @param url La nouvelle URL qui pointe vers la photo
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Méthode vérifiant que 2 photos ont la même URL
	 *
	 * @param p la photo avec laquelle on compare
	 * @return true si les 2 photos ont la même URL, false sinon
	 */
	public boolean aMemeUrl(Photo p) {
		return p.getUrl().equals(getUrl());
	}

}
