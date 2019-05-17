package com.projet.ecommerce.persistance.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

/**
 * Entité représentant la table categorie sous forme de classe.
 */

@Entity
@Table(name = "categorie")
public class Categorie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categorie")
	private int idCategorie;


	@Column(name = "nom_categorie")
	private String nomCategorie;

	@Column(name = "borne_gauche")
	private int borneGauche;

	@Column(name = "borne_droit")
	private int borneDroit;

	@Column(name = "level")
	private int level;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinTable(
			name = "produit_categorie",
			inverseJoinColumns = {@JoinColumn(name = "reference_produit")},
			joinColumns = {@JoinColumn(name = "id_categorie")}
	)
	private List<Produit> produits;

	/**
	 * Retourne le nom de la catégorie.
	 *
	 * @return le nom de la catégorie
	 */
	public String getNomCategorie() {
		return nomCategorie;
	}

	/**
	 * Remplace le nom de la catégorie par celui-ci mit en paramètre.
	 *
	 * @param nomCategorie Le nouveau nom de la catégorie
	 */
	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

	/**
	 * Retourne l'indice de la borne gauche de la catégorie.
	 *
	 * @return l'indice de la borne gauche
	 */
	public int getBorneGauche() {
		return borneGauche;
	}

	/**
	 * Remplace l'indice de la borne gauche par celui-ci mit en paramètre.
	 *
	 * @param borneGauche Le nouvel indice de la borne gauche
	 */
	public void setBorneGauche(int borneGauche) {
		this.borneGauche = borneGauche;
	}

	/**
	 * Retourne l'indice de la borne droit de la catégorie.
	 *
	 * @return l'indice de la borne droit
	 */
	public int getBorneDroit() {
		return borneDroit;
	}

	/**
	 * Remplace l'indice de la borne droit par celui-ci mit en paramètre.
	 *
	 * @param borneDroit Le nouvel indice de la borne droit
	 */
	public void setBorneDroit(int borneDroit) {
		this.borneDroit = borneDroit;
	}

	/**
	 * Retourne une liste de produits liés à la catégorie.
	 *
	 * @return
	 */
	public List<Produit> getProduits() {
		return produits;
	}

	/**
	 * Remplace la liste produit par celle-ci mit en paramètre.
	 *
	 * @param produits Une liste de produit
	 */
	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	/**
	 * Retourne le level de la catégorie.
	 *
	 * @return le level de la catégorie.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Remplace le level par celui-ci mit en paramètre.
	 *
	 * @param level Level de la catégorie.
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Retourne l'id de la catégorie.
	 *
	 * @return l'id de la catégorie
	 */
	public int getIdCategorie() {
		return idCategorie;
	}

	/**
	 * Remplace l'id de la catégorie par celui-ci mit en paramètre.
	 *
	 * @param idCategorie La nouvelle ID de la catégorie
	 */
	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj == null || !(obj instanceof Categorie)) {
			return false;
		}
		Categorie other = (Categorie) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.idCategorie, other.idCategorie);
		builder.append(this.borneGauche, other.borneGauche);
		builder.append(this.borneDroit, other.borneDroit);
		builder.append(this.level, other.level);
		builder.append(this.nomCategorie, other.nomCategorie);
		return builder.build();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(idCategorie);
		builder.append(borneDroit);
		builder.append(borneGauche);
		builder.append(level);
		builder.append(nomCategorie);
		return builder.build();
	}

}
