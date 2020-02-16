--liquibase formatted sql
--changeset Gwennael Lonvert:09


CREATE TABLE caracteristique
(
  reference_produit VARCHAR(255) NOT NULL,
  id_libelle int NOT NULL,
  valeur VARCHAR(255) NOT NULL,
  PRIMARY KEY (reference_produit, id_libelle),
  CONSTRAINT FK_idProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit),
  CONSTRAINT FK_idLibelle FOREIGN KEY (id_libelle)
  REFERENCES libelle(id_libelle)
);

INSERT INTO caracteristique (reference_produit, id_libelle, valeur) VALUES ('A06A01', 1, '223 pages');
INSERT INTO caracteristique (reference_produit, id_libelle, valeur) VALUES ('A06A01', 2, 'Flammarion');
INSERT INTO caracteristique (reference_produit, id_libelle, valeur) VALUES ('A07A03', 3, 'Français');
INSERT INTO caracteristique (reference_produit, id_libelle, valeur) VALUES ('A07A03', 4, '22 x 15,5 x 1,7 cm');



