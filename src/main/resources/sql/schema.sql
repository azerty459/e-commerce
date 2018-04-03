DROP TABLE IF EXISTS produit CASCADE;
DROP TABLE IF EXISTS categorie CASCADE;
DROP TABLE IF EXISTS produit_categorie CASCADE;
DROP TABLE IF EXISTS photo CASCADE;
DROP TABLE IF EXISTS caracteristique CASCADE;
DROP TABLE IF EXISTS type_caracteristique CASCADE;

CREATE TABLE produit
(
  reference_produit VARCHAR(255) NOT NULL,
  description TEXT,
  prix_HT FLOAT NOT NULL,
  PRIMARY KEY (reference_produit)
);

CREATE TABLE categorie
(
  nom_categorie VARCHAR(255) NOT NULL,
  borne_gauche int NOT NULL,
  borne_droit int NOT NULL,
  level int NOT NULL,
  PRIMARY KEY (nom_categorie)
);

CREATE TABLE produit_categorie
(
  reference_produit VARCHAR(255) NOT NULL,
  nom_categorie VARCHAR(255) NOT NULL,
  PRIMARY KEY (reference_produit, nom_categorie),
  CONSTRAINT FK_referenceProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit),
  CONSTRAINT FK_idCategorie FOREIGN KEY (nom_categorie)
  REFERENCES categorie(nom_categorie)
);


CREATE TABLE photo
(
  id_photo SERIAL,
  url VARCHAR(255) NOT NULL,
  reference_produit VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_photo),
  CONSTRAINT FK_idProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit)
);

CREATE TABLE type_caracteristique
(
  id_type_caracteristique SERIAL,
  type VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_type_caracteristique)
);

CREATE TABLE caracteristique
(
  id_caracteristique SERIAL,
  reference_produit VARCHAR(255) NOT NULL,
  id_type_caracteristique int NOT NULL,
  valeur VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_caracteristique),
  CONSTRAINT FK_typeCaracteristique FOREIGN KEY (id_type_caracteristique)
  REFERENCES type_caracteristique(id_type_caracteristique),
  CONSTRAINT FK_referenceProduit FOREIGN KEY (reference_produit)
    REFERENCES produit(reference_produit)
);