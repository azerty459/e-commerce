--liquibase formatted sql
--changeset Ludovic Lahousse:10

CREATE TABLE valeur_caracteristique
(
  reference_produit VARCHAR(255) NOT NULL,
  id_caracteristique int NOT NULL,
  valeur VARCHAR(255) NOT NULL,
  PRIMARY KEY (reference_produit, id_caracteristique),
  CONSTRAINT FK_referenceProduit FOREIGN KEY (reference_produit)
  	REFERENCES produit(reference_produit),
  CONSTRAINT FK_idCaracteristique FOREIGN KEY (id_caracteristique)
  	REFERENCES caracteristique(id_caracteristique)
);

CREATE TABLE caracteristique
(
  id_caracteristique SERIAL
  libelle TEXT NOT NULL, 
  PRIMARY KEY (id_caracteristique)
);