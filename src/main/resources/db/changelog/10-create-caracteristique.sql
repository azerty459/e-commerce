CREATE TABLE caracteristique
(
  id_caracteristique SERIAL,
  type_caracteristique VARCHAR(255) NOT NULL,
  value_caracteristique VARCHAR(255),
  PRIMARY KEY (id_caracteristique)
);

CREATE TABLE produit_caracteristique
(
  reference_produit VARCHAR(255) NOT NULL,
  id_caracteristique int NOT NULL,
  PRIMARY KEY (reference_produit, id_caracteristique),
  CONSTRAINT FK_produit_caracteristique_referenceProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit),
  CONSTRAINT FK_idCaracteristique FOREIGN KEY (id_caracteristique)
  REFERENCES caracteristique(id_caracteristique)
);


