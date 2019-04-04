INSERT INTO type_caracteristique (id_type_caracteristique, type_caracteristique) 
VALUES (1,'Broché'),(2,'Editeur'),(3,'Langue'),(4,'Dimensions du produit');

INSERT INTO caracteristique(id_caracteristique, id_type_caracteristique, reference_produit, valeur_caracteristique) 
VALUES (1, 1, 'A05A01', '223 pages'),
		(1 ,2 ,'A05A0', 'Flammarion'),
		(1 ,3 ,'A05A0', 'Français'),
		(1 ,4 ,'A05A0', '22 x 15,5 x 1,7 cm');