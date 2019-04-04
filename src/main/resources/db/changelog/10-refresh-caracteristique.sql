--liquibase formatted sql
--changeset Arthur Brandao:10

CREATE TABLE caracteristique (
  id_caracteristique SERIAL,
  label_caracteristique VARCHAR(255),
  PRIMARY KEY (id_caracteristique)
);

CREATE TABLE produit_caracteristique (
  reference_produit VARCHAR(255) NOT NULL,
  id_caracteristique int NOT NULL,
  valeur VARCHAR(255),
  PRIMARY KEY (reference_produit, id_caracteristique),
  CONSTRAINT FK_posseder_produit FOREIGN KEY (reference_produit) REFERENCES produit(reference_produit),
  CONSTRAINT FK_posseder_caracteristique FOREIGN KEY (id_caracteristique) REFERENCES caracteristique(id_caracteristique)
);