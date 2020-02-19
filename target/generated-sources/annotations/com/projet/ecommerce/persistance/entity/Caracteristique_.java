package com.projet.ecommerce.persistance.entity;

import com.projet.ecommerce.persistance.primarykey.CaracteristiqueID;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Caracteristique.class)
public abstract class Caracteristique_ {

	public static volatile SingularAttribute<Caracteristique, String> valeur;
	public static volatile SingularAttribute<Caracteristique, Produit> produit;
	public static volatile SingularAttribute<Caracteristique, Libelle> libelle;
	public static volatile SingularAttribute<Caracteristique, CaracteristiqueID> id;

	public static final String VALEUR = "valeur";
	public static final String PRODUIT = "produit";
	public static final String LIBELLE = "libelle";
	public static final String ID = "id";

}

