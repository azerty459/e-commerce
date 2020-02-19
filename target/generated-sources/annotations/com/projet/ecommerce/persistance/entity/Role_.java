package com.projet.ecommerce.persistance.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

	public static volatile ListAttribute<Role, Utilisateur> utilisateurs;
	public static volatile SingularAttribute<Role, Integer> id;
	public static volatile SingularAttribute<Role, String> nom;

	public static final String UTILISATEURS = "utilisateurs";
	public static final String ID = "id";
	public static final String NOM = "nom";

}

