--liquibase formatted sql
--changeset Arthur Brandao:11

Insert into caracteristique (libelle_caracteristique) Values ('Couleur');
Insert into caracteristique (libelle_caracteristique) Values ('Taille');
Insert into caracteristique (libelle_caracteristique) Values ('Poid');
Insert into caracteristique (libelle_caracteristique) Values ('Nombre de pages');
Insert into caracteristique (libelle_caracteristique) Values ('Vitesse');

Insert into produit_caracteristique Values ('A05A01', 4, '52');
Insert into produit_caracteristique Values ('A07A03', 1, 'Noir');
Insert into produit_caracteristique Values ('A07A03', 3, '14');