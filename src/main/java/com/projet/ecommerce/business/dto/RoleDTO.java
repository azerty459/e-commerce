package com.projet.ecommerce.business.dto;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Role).
 */
public class RoleDTO {

	/**
	 * L'id du rôle
	 */
	private int id;

	/**
	 * Le nom du rôle
	 */
	private String nom;

	/**
	 * Obtenir l'id du rôle
	 *
	 * @return l'id du rôle
	 */
	public int getId() {
		return id;
	}

	/**
	 * Définir la nouvelle id du rôle
	 *
	 * @param id La nouvelle id du rôle
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtenir le nom du rôle
	 *
	 * @return le nom du rôle
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Définir le nouveau nom du rôle
	 *
	 * @param nom Le nouveau nom du rôle
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

}
