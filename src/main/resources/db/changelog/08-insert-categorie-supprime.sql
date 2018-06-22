--liquibase formatted sql
--changeset Rémy Halipré:07

CREATE TABLE categorie_supprime
(
  id_categorie SERIAL,
  nom_categorie VARCHAR(255) NOT NULL,
  borne_gauche int NOT NULL,
  borne_droit int NOT NULL,
  level int NOT NULL,
  PRIMARY KEY (id_categorie)
);

CREATE TABLE produit_categorie_supprime
(
  reference_produit VARCHAR(255) NOT NULL,
  id_categorie int NOT NULL,
  PRIMARY KEY (reference_produit, id_categorie),
  CONSTRAINT FK_produit_categorie_referenceProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit),
  CONSTRAINT FK_idCategorie FOREIGN KEY (id_categorie)
  REFERENCES categorie(id_categorie)
);
