--liquibase formatted sql
--changeset Rémy Halipré:07

CREATE TABLE utilisateur
(
  id_utilisateur SERIAL,
  adresse_email VARCHAR(255) NOT NULL UNIQUE,
  mot_de_passe VARCHAR(255) NOT NULL,
  nom VARCHAR(50) DEFAULT '',
  prenom VARCHAR(50) DEFAULT '',
  PRIMARY KEY (id_utilisateur)
);

CREATE TABLE role
(
  id_role SERIAL,
  nom VARCHAR(100) NOT NULL UNIQUE,
  PRIMARY KEY (id_role)
);

CREATE TABLE utilisateur_role
(
  id_utilisateur int NOT NULL,
  id_role int NOT NULL,
  PRIMARY KEY (id_utilisateur, id_role),
  CONSTRAINT FK_idUtilisateur FOREIGN KEY (id_utilisateur)
  REFERENCES utilisateur(id_utilisateur),
  CONSTRAINT FK_idRole FOREIGN KEY (id_role)
  REFERENCES role(id_role)
);

INSERT INTO utilisateur (adresse_email, mot_de_passe, nom, prenom) VALUES ('rhalipre@nextoo.fr', 'rhalipre', '', ''),('utilisateur@gmail.com', 'utilisateur', '', ''),('visiteur@gmail.com', 'visiteur', '', '');
INSERT INTO role (nom) VALUES ('Administrateur'),('Utilisateur'),('Visiteur');
INSERT INTO utilisateur_role (id_utilisateur, id_role) VALUES (1, 1),(2, 2),(3, 3);