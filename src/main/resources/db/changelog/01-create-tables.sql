--liquibase formatted sql
--changeset Rémy Halipré:01
CREATE TABLE IF NOT EXISTS produit
(
  reference_produit VARCHAR(255) NOT NULL,
  nom VARCHAR(255) NOT NULL,
  description TEXT DEFAULT '',
  prix_ht FLOAT NOT NULL,
  photo_principale int,
  date_ajout TIMESTAMP DEFAULT current_timestamp,
  PRIMARY KEY (reference_produit)
);

CREATE TABLE IF NOT EXISTS categorie
(
  id_categorie SERIAL,
  nom_categorie VARCHAR(255) NOT NULL,
  borne_gauche int NOT NULL,
  borne_droit int NOT NULL,
  level int NOT NULL,
  PRIMARY KEY (id_categorie)
);

CREATE TABLE IF NOT EXISTS produit_categorie
(
  reference_produit VARCHAR(255) NOT NULL,
  id_categorie int NOT NULL,
  PRIMARY KEY (reference_produit, id_categorie),
  CONSTRAINT FK_produit_categorie_referenceProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit),
  CONSTRAINT FK_idCategorie FOREIGN KEY (id_categorie)
  REFERENCES categorie(id_categorie)
);


CREATE TABLE IF NOT EXISTS photo
(
  id_photo SERIAL,
  url VARCHAR(255) NOT NULL UNIQUE,
  nom VARCHAR(255) NOT NULL,
  reference_produit VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_photo),
  CONSTRAINT FK_idProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit)
);

CREATE TABLE IF NOT EXISTS type_caracteristique
(
  id_type_caracteristique SERIAL,
  type_carac VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_type_caracteristique)
);

CREATE TABLE IF NOT EXISTS caracteristique
(
  id_caracteristique SERIAL,
  valeur VARCHAR(255) NOT NULL,
  reference_produit VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_caracteristique),
  CONSTRAINT FK_idProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit),
  id_type_caracteristique SERIAL,
  CONSTRAINT FK_idTypeCaracteristique FOREIGN KEY (id_type_caracteristique)
  REFERENCES type_caracteristique(id_type_caracteristique)
);


