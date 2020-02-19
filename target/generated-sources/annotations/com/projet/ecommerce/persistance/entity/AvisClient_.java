package com.projet.ecommerce.persistance.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AvisClient.class)
public abstract class AvisClient_ {

	public static volatile SingularAttribute<AvisClient, LocalDateTime> date;
	public static volatile SingularAttribute<AvisClient, Integer> note;
	public static volatile SingularAttribute<AvisClient, Produit> produit;
	public static volatile SingularAttribute<AvisClient, String> description;
	public static volatile SingularAttribute<AvisClient, Integer> id;

	public static final String DATE = "date";
	public static final String NOTE = "note";
	public static final String PRODUIT = "produit";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

