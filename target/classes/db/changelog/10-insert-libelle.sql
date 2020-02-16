--liquibase formatted sql
--changeset Gwennael Lonvert:09

CREATE TABLE libelle
(
  id_libelle SERIAL,
  nom VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (id_libelle)
);

INSERT INTO libelle (nom) VALUES ('Broché');
INSERT INTO libelle (nom) VALUES ('Editeur');
INSERT INTO libelle (nom) VALUES ('Langue');
INSERT INTO libelle (nom) VALUES ('Dimensions du produit');

