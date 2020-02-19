package com.projet.ecommerce.persistance.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Categorie.class)
public abstract class Categorie_ {

	public static volatile SingularAttribute<Categorie, String> nomCategorie;
	public static volatile ListAttribute<Categorie, Produit> produits;
	public static volatile SingularAttribute<Categorie, Integer> level;
	public static volatile SingularAttribute<Categorie, Integer> idCategorie;
	public static volatile SingularAttribute<Categorie, Integer> borneDroit;
	public static volatile SingularAttribute<Categorie, Integer> borneGauche;

	public static final String NOM_CATEGORIE = "nomCategorie";
	public static final String PRODUITS = "produits";
	public static final String LEVEL = "level";
	public static final String ID_CATEGORIE = "idCategorie";
	public static final String BORNE_DROIT = "borneDroit";
	public static final String BORNE_GAUCHE = "borneGauche";

}

