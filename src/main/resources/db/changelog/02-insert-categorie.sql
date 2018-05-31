--liquibase formatted sql
--changeset Rémy Halipré:02

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Graphisme', 1, 12, 1),
('Photoshop', 2, 9, 2),('Retouche Photo', 3, 4, 3),('Dessin', 5, 6, 3),('Typographie', 7, 8, 3),('GIMP', 10, 11, 2);

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Vidéo/3D', 13, 22, 1),('After Effects', 14, 15, 2),('Premiere Pro', 16, 17, 2),('3D Studio Max', 18, 19, 2),
('Cinema 4D', 20, 21, 2);

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Programmation', 23, 44, 1),('Web', 24, 39, 2),('PHP', 25, 28, 3),('Symfony', 26, 27, 4),
('Wordpress', 29, 30, 3),('JavaScript', 31, 36, 3),('jQuery', 32, 33, 4),('Mootools', 34, 35, 4),('Java', 37, 38, 3),
('Base de donnée', 40, 45, 2),('MySQL', 41, 42, 3),('MongoDB', 43, 44, 3);

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Livre', 45, 60, 1),('Adolescent', 46, 47, 2),('Bandes dessinées', 48, 55, 2),('Humour', 49, 50, 3),('Fantastiques', 51, 52, 3),('Commics', 53, 54, 3),('Droit', 56, 57, 2),('Histoire', 58, 59, 2);

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Trannsport', 61, 104, 1),('Aerien', 62, 81, 2),('Planeur', 63, 64, 3),('Parachute', 65, 66, 3),('Hélico', 67, 68, 3),('Fusée', 69, 70, 3),('ULM', 71, 72, 3),('Avion', 73, 80, 3),('Militaire', 74, 75, 4),('Tourisme', 76, 77, 4),('Civil', 78, 79, 4),('Terrestre', 82, 95, 2),('Vélo', 83, 84, 3),('Voiture', 85, 86, 3),('Camion', 87, 88, 3),('Moto', 89, 94, 3),('Side-car', 90, 91, 4),('Trail', 92, 93, 4),('Marin', 96, 103, 2),('Planche à voile', 97, 98, 3),('Paquebot', 99, 100, 3),('Voilier', 101, 102, 3);