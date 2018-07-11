--liquibase formatted sql
--changeset Rémy Halipré:04
INSERT INTO produit_categorie (reference_produit, id_categorie) VALUES ('A05A01', 1);
INSERT INTO produit_categorie (reference_produit, id_categorie) VALUES ('A05A02', 2);
INSERT INTO produit_categorie (reference_produit, id_categorie) VALUES ('A05A03', 1);
INSERT INTO produit_categorie (reference_produit, id_categorie) VALUES ('A05A04', 3);

INSERT INTO produit_categorie (reference_produit, id_categorie) VALUES ('A06A01', 1);
INSERT INTO produit_categorie (reference_produit, id_categorie) VALUES ('A06A02', 2);
INSERT INTO produit_categorie (reference_produit, id_categorie) VALUES ('A06A03', 2);

INSERT INTO produit_categorie (reference_produit, id_categorie) VALUES ('A07A01', 2);
INSERT INTO produit_categorie (reference_produit, id_categorie) VALUES ('A07A02', 2);
INSERT INTO produit_categorie (reference_produit, id_categorie) VALUES ('A07A03', 2);
