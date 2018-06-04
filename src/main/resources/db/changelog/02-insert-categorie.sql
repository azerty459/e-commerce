--liquibase formatted sql
--changeset Rémy Halipré:02

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Graphisme', 1, 12, 1),
('Photoshop', 2, 9, 2),('Retouche Photo', 3, 4, 3),('Dessin', 5, 6, 3),('Typographie', 7, 8, 3),('GIMP', 10, 11, 2);

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Vidéo/3D', 13, 22, 1),('After Effects', 14, 15, 2),('Premiere Pro', 16, 17, 2),('3D Studio Max', 18, 19, 2),
('Cinema 4D', 20, 21, 2);

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Programmation', 23, 46, 1),('Web', 24, 39, 2),('PHP', 25, 28, 3),('Symfony', 26, 27, 4),
('Wordpress', 29, 30, 3),('JavaScript', 31, 36, 3),('jQuery', 32, 33, 4),('Mootools', 34, 35, 4),('Java', 37, 38, 3),
('Base de donnée', 40, 45, 2),('MySQL', 41, 42, 3),('MongoDB', 43, 44, 3);

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Livre', 46, 62, 1),('Adolescent', 47, 48, 2),('Bandes dessinées', 49, 56, 2),('Humour', 50, 51, 3),('Fantastiques', 52, 53, 3),('Commics', 54, 55, 3),('Droit', 58, 59, 2),('Histoire', 60, 61, 2);

INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Trannsport', 62, 105, 1),('Aerien', 63, 82, 2),('Planeur', 64, 65, 3),('Parachute', 66, 67, 3),('Hélico', 68, 69, 3),('Fusée', 70, 71, 3),('ULM', 72, 73, 3),('Avion', 74, 81, 3),('Militaire', 75, 76, 4),('Tourisme', 77, 78, 4),('Civil', 79, 80, 4),('Terrestre', 83, 96, 2),('Vélo', 84, 85, 3),('Voiture', 86, 87, 3),('Camion', 88, 89, 3),('Moto', 90, 95, 3),('Side-car', 91, 92, 4),('Trail', 93, 94, 4),('Marin', 96, 103, 2),('Planche à voile', 97, 98, 3),('Paquebot', 99, 100, 3),('Voilier', 101, 102, 3);