--liquibase formatted sql
--changeset Rémy Halipré:02

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Graphisme', 1, 12, 1),
('Photoshop', 2, 9, 2),('Retouche Photo', 3, 4, 3),('Dessin', 5, 6, 3),('Typographie', 7, 8, 3),('GIMP', 10, 11, 2);
