package com.projet.ecommerce.persistance.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CategorieSupprime.class)
public abstract class CategorieSupprime_ {

	public static volatile SingularAttribute<CategorieSupprime, String> nomCategorie;
	public static volatile ListAttribute<CategorieSupprime, Produit> produits;
	public static volatile SingularAttribute<CategorieSupprime, Integer> level;
	public static volatile SingularAttribute<CategorieSupprime, Integer> idCategorie;
	public static volatile SingularAttribute<CategorieSupprime, Integer> borneDroit;
	public static volatile SingularAttribute<CategorieSupprime, Integer> borneGauche;

	public static final String NOM_CATEGORIE = "nomCategorie";
	public static final String PRODUITS = "produits";
	public static final String LEVEL = "level";
	public static final String ID_CATEGORIE = "idCategorie";
	public static final String BORNE_DROIT = "borneDroit";
	public static final String BORNE_GAUCHE = "borneGauche";

}

