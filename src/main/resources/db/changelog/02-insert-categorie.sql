--liquibase formatted sql
--changeset Rémy Halipré:02
INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Livre', 1, 8, 1),('Adolescent', 2, 3, 2),('Droit', 4, 7, 2),('Culture', 5, 6, 3);