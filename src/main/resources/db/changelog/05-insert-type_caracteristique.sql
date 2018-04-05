--liquibase formatted sql
--changeset Rémy Halipré:05
INSERT INTO type_caracteristique (type) VALUES ('Marque'),('Dimension'),('Couleur');