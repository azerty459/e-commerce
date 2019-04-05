DROP TABLE caracteristique;
DROP TABLE type_caracteristique;


CREATE TABLE type_caracteristique
(
  id_type_caracteristique SERIAL,
  nom_type_caracteristique VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (id_type_caracteristique)
);

CREATE TABLE caracteristique
(
  id_caracteristique SERIAL,
  valeur_caracteristique VARCHAR(255) NOT NULL,
  reference_produit VARCHAR(255) NOT NULL,
  type_caracteristique int NOT NULL,
  PRIMARY KEY (id_caracteristique),
  CONSTRAINT fk_produit FOREIGN KEY (reference_produit)
      REFERENCES produit (reference_produit),
  CONSTRAINT fk_type_caracteristique FOREIGN KEY (id_type_caracteristique)
      REFERENCES type_caracteristique (id_type_caracteristique)
);