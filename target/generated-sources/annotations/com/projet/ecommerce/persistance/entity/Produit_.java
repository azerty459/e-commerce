package com.projet.ecommerce.persistance.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Produit.class)
public abstract class Produit_ {

	public static volatile SingularAttribute<Produit, String> referenceProduit;
	public static volatile ListAttribute<Produit, AvisClient> avisClients;
	public static volatile SingularAttribute<Produit, LocalDateTime> dateAjout;
	public static volatile SingularAttribute<Produit, String> description;
	public static volatile SingularAttribute<Produit, Float> prixHT;
	public static volatile ListAttribute<Produit, Categorie> categories;
	public static volatile SingularAttribute<Produit, Photo> photoPrincipale;
	public static volatile ListAttribute<Produit, Categorie> categoriesSupprime;
	public static volatile SingularAttribute<Produit, String> nom;
	public static volatile ListAttribute<Produit, Photo> photos;
	public static volatile ListAttribute<Produit, Caracteristique> caracteristiques;

	public static final String REFERENCE_PRODUIT = "referenceProduit";
	public static final String AVIS_CLIENTS = "avisClients";
	public static final String DATE_AJOUT = "dateAjout";
	public static final String DESCRIPTION = "description";
	public static final String PRIX_HT = "prixHT";
	public static final String CATEGORIES = "categories";
	public static final String PHOTO_PRINCIPALE = "photoPrincipale";
	public static final String CATEGORIES_SUPPRIME = "categoriesSupprime";
	public static final String NOM = "nom";
	public static final String PHOTOS = "photos";
	public static final String CARACTERISTIQUES = "caracteristiques";

}

