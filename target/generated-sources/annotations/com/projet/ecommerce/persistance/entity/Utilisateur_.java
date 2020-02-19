package com.projet.ecommerce.persistance.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Utilisateur.class)
public abstract class Utilisateur_ {

	public static volatile SingularAttribute<Utilisateur, Role> role;
	public static volatile SingularAttribute<Utilisateur, String> mdp;
	public static volatile SingularAttribute<Utilisateur, Integer> id;
	public static volatile SingularAttribute<Utilisateur, String> nom;
	public static volatile SingularAttribute<Utilisateur, String> prenom;
	public static volatile SingularAttribute<Utilisateur, String> email;

	public static final String ROLE = "role";
	public static final String MDP = "mdp";
	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String EMAIL = "email";

}

