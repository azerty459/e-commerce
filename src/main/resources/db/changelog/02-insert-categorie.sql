--liquibase formatted sql
--changeset Rémy Halipré:02
INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Graphisme', 1, 12, 1),
('Photoshop', 2, 9, 2),('Retouche Photo', 3, 4, 3),('Dessin', 5, 6, 3),('Typographie', 7, 8, 3),('GIMP', 10, 11, 2),
('Vidéo/3D', 13, 22, 1),('After Effects', 14, 15, 2),('Premiere Pro', 16, 17, 2),('3D Studio Max', 18, 19, 2),
('Cinema 4D', 20, 21, 2),('Programmation', 23, 44, 1),('Web', 24, 39, 2),('PHP', 25, 28, 3),('Symfony', 26, 27, 4),
('Wordpress', 29, 30, 3),('JavaScript', 31, 36, 3),('jQuery', 32, 33, 4),('Mootools', 34, 35, 4),('Java', 37, 38, 3),
('Base de donnée', 40, 45, 2),('MySQL', 41, 42, 3),('MongoDB', 43, 44, 3);