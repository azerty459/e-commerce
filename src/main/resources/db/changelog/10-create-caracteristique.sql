CREATE TABLE caracteristique (
reference_produit varchar(255) not null,
type_caracteristique VARCHAR(255) NOT NULL,
valeur_caracteristique VARCHAR(255) NOT NULL,
PRIMARY KEY (reference_produit, type_caracteristique)
CONSTRAINT FK_produit_caracteristique FOREIGN KEY (reference_produit)
references produit(reference_produit),
constraint FK_type_caracteristique FOREIGN KEY (type_caracteristique)
references  type_caracteristique(type)
);

CREATE TABLE type_caracteristique (
type varchar(255) not null,
PRIMARY KEY (type)
);



