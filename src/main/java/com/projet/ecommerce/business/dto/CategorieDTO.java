package com.projet.ecommerce.business.dto;

import java.util.Collection;
import java.util.List;

/**
 * Entité qui permet d'assurer le découplage entre la couche de présentation et les objets métier stockés sur le serveur (Categorie).
 */
public class CategorieDTO {

	/**
	 * L'id de la catégorie.
	 */
	private int id;

	/**
	 * Le nom de la catégorie.
	 */
	private String nom;

	/**
	 * La liste des sous-catégories de la catégorie.
	 */
	private List<CategorieDTO> sousCategories;

	/**
	 * Liste des parents de la catégories.
	 */
	private Collection<CategorieDTO> chemin;

	/**
	 * Niveau de la catégorie dans l'arborescence des catégories (Niveau 1 = niveau le plus élevé).
	 */
	private int level;

	/**
	 * Le parent direct de la catégorie
	 */
	private CategorieDTO parent;

	/**
	 * La profondeur de l'arbre formé par ses sous-catégories
	 */
	private int profondeur;

	/**
	 * Retourne le nom de la categorieDTO.
	 *
	 * @return le nom de la categorie.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Remplace le nom de la CategorieDTO par celui-ci mit en paramètre.
	 *
	 * @param nom Le nouveau nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne une liste de CategorieDTO.
	 *
	 * @return une liste de CategorieDTO.
	 */
	public List<CategorieDTO> getSousCategories() {
		return sousCategories;
	}

	/**
	 * Remplace la liste de CategorieDTO par celle-ci mit en paramètre.
	 *
	 * @param sousCategories Une liste de CategorieDTO
	 */
	public void setSousCategories(List<CategorieDTO> sousCategories) {
		this.sousCategories = sousCategories;
	}

	/**
	 * Retourne l'id de la catégorie.
	 *
	 * @return l'id de la catégorie
	 */
	public int getId() {
		return id;
	}

	/**
	 * Remplace l'id par celle-ci mit en paramètre.
	 *
	 * @param id La nouvelle id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Retourne le chemin pour arriver à la catégorie depuis la catégorie parente de niveau 1
	 *
	 * @return une chaîne de caractères représentant ce chemin.
	 */
	public Collection<CategorieDTO> getChemin() {
		return chemin;
	}

	/**
	 * Fixe le chemin de la catégorie.
	 *
	 * @param c la chaîne de caractères représentant le chemin.
	 */
	public void setChemin(Collection<CategorieDTO> c) {
		chemin = c;
	}

	/**
	 * Retourne le niveau de la catégorie dans l'arborescence des catégories.
	 *
	 * @return le niveau (1 = catégorie de niveau 1, tout en haut de l'arborescence).
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Fixe le niveau de la catégorie.
	 *
	 * @param l le niveau de la catégorie.
	 */
	public void setLevel(int l) {
		level = l;
	}

	/**
	 * Retourne le parent direct de la catégorie
	 *
	 * @return le CategorieDTO représentant le parent
	 */
	public CategorieDTO getParent() {
		return parent;
	}

	/**
	 * Fixe le parent de la catégorie
	 *
	 * @param p le parent
	 */
	public void setParent(CategorieDTO p) {
		parent = p;
	}

	/**
	 * Retourne la profondeur formé par ses sous-catégories
	 *
	 * @return la profondeur formé par ses sous-catégories
	 */
	public int getProfondeur() {
		return profondeur;
	}

	/**
	 * Remplace la profondeur par celui mis en paramètre.
	 *
	 * @param profondeur la nouvelle profondeur
	 */
	public void setProfondeur(int profondeur) {
		this.profondeur = profondeur;
	}

	@Override
	public int hashCode() {
		int prime = 17;
		int result = 1;
		result = prime * result + id;
		result = prime * result + profondeur;
		result = prime * result + level;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof CategorieDTO)) {
			return false;
		}
		CategorieDTO other = (CategorieDTO) obj;
		boolean result = this.id == other.id
				&& this.profondeur == other.profondeur
				&& this.level == other.level;
		result &= (this.nom != null) ? this.nom.equals(other.nom) : other.nom == null;
		return result;
	}

}
