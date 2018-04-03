INSERT INTO categorie (nom_categorie, borne_gauche, borne_droit, level) VALUES ('Livre', 1, 8, 1),('Adolescent', 2, 3, 2),('Droit', 4, 5, 2),('Culture', 6, 7, 3);

INSERT INTO produit (reference_produit, description, prix_HT) VALUES ('A05A01', 'Paw Patrol-La Pat Patrouille - Joyeuses Pâques', 2.0);
INSERT INTO produit (reference_produit, description, prix_HT) VALUES ('A05A02', 'Sami et Julie - Français CP : le zoos', 2.0);
INSERT INTO produit (reference_produit, description, prix_HT) VALUES ('A05A03', 'Journal d"un Noob tome 6', 10.5);
INSERT INTO produit (reference_produit, description, prix_HT) VALUES ('A05A04', 'Lefranc, Tome 29 : La stratégie du chaos', 12.0);

INSERT INTO produit (reference_produit, description, prix_HT) VALUES ('A06A01', 'Directs du droit', 6.70);
INSERT INTO produit (reference_produit, description, prix_HT) VALUES ('A06A02', 'MEMENTO SOCIAL 2018', 14.5);
INSERT INTO produit (reference_produit, description, prix_HT) VALUES ('A06A03', 'Code civil 2015 - 114 e éd.', 30.0);

INSERT INTO produit (reference_produit, description, prix_HT) VALUES ('A07A01', 'L"Officiel du jeu Scrabble®', 30.0);
INSERT INTO produit (reference_produit, description, prix_HT) VALUES ('A07A02', 'Guinness World Records 2018', 29.95);
INSERT INTO produit (reference_produit, description, prix_HT) VALUES ('A07A03', 'DRH. Le livre noir', 7.30);

INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A05A01', 'Livre');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A05A02', 'Livre');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A05A03', 'Livre');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A05A04', 'Livre');

INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A05A01', 'Adolescent');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A05A02', 'Adolescent');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A05A03', 'Adolescent');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A05A04', 'Adolescent');

INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A06A01', 'Droit');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A06A02', 'Droit');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A06A03', 'Droit');

INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A06A01', 'Livre');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A06A02', 'Livre');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A06A03', 'Livre');

INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A07A01', 'Livre');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A07A02', 'Livre');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A07A03', 'Livre');

INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A07A01', 'Culture');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A07A02', 'Culture');
INSERT INTO produit_categorie (reference_produit, nom_categorie) VALUES ('A07A03', 'Culture');

INSERT INTO type_caracteristique (type) VALUES ('Marque'),('Dimension'),('Couleur');

INSERT INTO caracteristique (reference_produit, id_type_caracteristique, valeur) VALUES ('A05A01', 3, 'Rouge'),('A05A01', 1, 'Hachette');
INSERT INTO caracteristique (reference_produit, id_type_caracteristique, valeur) VALUES ('A05A02', 3, 'Noir'),('A05A01', 2, '16x16');

INSERT INTO photo (url, reference_produit) VALUES ('/etc/picture/test.jpg', 'A05A01');
INSERT INTO photo (url, reference_produit) VALUES ('/etc/picture/test1.jpg', 'A05A01');
INSERT INTO photo (url, reference_produit) VALUES ('/etc/picture/test2.jpg', 'A05A01');

INSERT INTO photo (url, reference_produit) VALUES ('/etc/picture/test4.jpg', 'A05A02');
INSERT INTO photo (url, reference_produit) VALUES ('/etc/picture/test5.jpg', 'A05A02');

INSERT INTO photo (url, reference_produit) VALUES ('/etc/picture/test6.jpg', 'A05A03');