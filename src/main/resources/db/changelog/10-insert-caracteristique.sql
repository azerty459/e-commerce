CREATE TABLE caracteristique
(
  id_carac SERIAL,
  valeur VARCHAR(255) NOT NULL,
  typeCaracteristique VARCHAR(255) NOT NULL,
  reference_produit VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_carac),
  CONSTRAINT FK_carac_idProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit)
);

/*
CREATE TABLE produit_caracteristique
(
  reference_produit VARCHAR(255) NOT NULL,
  id_carac int NOT NULL,
  PRIMARY KEY (reference_produit, id_carac),
  CONSTRAINT FK_produit_caracteristique_referenceProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit),
  CONSTRAINT FK_idCaracteristique FOREIGN KEY (id_carac)
  REFERENCES caracteristique(id_carac)
);
*/

INSERT INTO caracteristique (valeur, typeCaracteristique) VALUES ('Flammarion', 'EDITEUR');
INSERT INTO caracteristique (valeur, typeCaracteristique) VALUES ('Français', 'LANGUE');
INSERT INTO caracteristique (valeur, typeCaracteristique) VALUES ('21 x 29.7', 'DIMENSIONS');
INSERT INTO caracteristique (valeur, typeCaracteristique) VALUES ('178g', 'POIDS');
