--liquibase formatted sql
--changeset Rémy Halipré:06
INSERT INTO caracteristique (reference_produit, id_type_caracteristique, valeur) VALUES ('A05A01', 3, 'Rouge'),('A05A01', 1, 'Hachette');
INSERT INTO caracteristique (reference_produit, id_type_caracteristique, valeur) VALUES ('A05A02', 3, 'Noir'),('A05A01', 2, '16x16');