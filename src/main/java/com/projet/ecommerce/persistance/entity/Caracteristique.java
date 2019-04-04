package com.projet.ecommerce.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "caracteristique")
public class Caracteristique {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristique")
    private int idCaracteristique;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "id_type_caracteristique")
	private TypeCaracteristque typeCaracteristique;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "reference_produit")
    private Produit produit;
	
	@Column(name = "valeur_caracteristique")
    private String valeurCaracteristique;
	
	
}
