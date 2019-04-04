CREATE TABLE type_caracteristique
(
  id_type_caracteristique SERIAL,
  type_caracteristique VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_type_caracteristique)
);

CREATE TABLE caracteristique
(
  id_caracteristique SERIAL NOT NULL,
  id_type_caracteristique SERIAL NOT NULL,
  reference_produit VARCHAR(255) NOT NULL,
  valeur_caracteristique VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_caracteristique),
  CONSTRAINT FK_TypeCategorieCaracteristique FOREIGN KEY (id_type_caracteristique)
  REFERENCES type_caracteristique(id_type_caracteristique),
  CONSTRAINT FK_idProduitCaracteristique FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit)
);
