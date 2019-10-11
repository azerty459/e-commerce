--liquibase formatted sql
--changeset Rémy Halipré:07

CREATE TABLE utilisateur
(
  id_utilisateur SERIAL
  ,
  adresse_email VARCHAR(255) NOT NULL UNIQUE,
  mot_de_passe VARCHAR(255) NOT NULL,
  nom VARCHAR(50) DEFAULT '',
  id_role VARCHAR(255),
  prenom VARCHAR(50) DEFAULT '',
  PRIMARY KEY (id_utilisateur)
);

CREATE TABLE role
(
  id_role SERIAL,

  nom VARCHAR(100) ,
  PRIMARY KEY (id_role)
);



INSERT INTO utilisateur (adresse_email, mot_de_passe, nom, prenom) VALUES ('a', '$2a$10$tKoa.0HtlizobJijofNHIeJkIXOBYFFJMelG075rNqupjJAtKcFSW', '', ''),('utilisateur@gmail.com', 'utilisateur', '', ''),('visiteur@gmail.com', 'visiteur', '', '');
INSERT INTO role (nom) VALUES ('Administrateur'),('Utilisateur'),('Visiteur');
