--liquibase formatted sql
--changeset Gwennael Lonvert:09

INSERT INTO caracteristique (valeur, reference_produit, id_type_caracteristique) VALUES ('223 pages','A05A01',1),
																						('Flammarion','A05A01',2),
																						('Français','A05A01',3),
																						('22 x 15,5 x 1,7 cm','A05A01',4);
