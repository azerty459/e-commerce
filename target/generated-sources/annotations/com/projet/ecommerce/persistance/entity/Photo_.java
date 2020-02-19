package com.projet.ecommerce.persistance.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Photo.class)
public abstract class Photo_ {

	public static volatile SingularAttribute<Photo, Produit> produit;
	public static volatile SingularAttribute<Photo, Integer> idPhoto;
	public static volatile SingularAttribute<Photo, String> nom;
	public static volatile SingularAttribute<Photo, String> url;

	public static final String PRODUIT = "produit";
	public static final String ID_PHOTO = "idPhoto";
	public static final String NOM = "nom";
	public static final String URL = "url";

}

