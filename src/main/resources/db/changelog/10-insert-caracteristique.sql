CREATE TABLE type_caracteristique (

    id_type_caracteristique SERIAL,
    libelle VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_type_caracteristique)

);

CREATE TABLE caracteristique (

    id_caracteristique SERIAL,
    id_type_caracteristique SERIAL,
    valeur VARCHAR(255),
    reference_produit VARCHAR(255),
    PRIMARY KEY (id_caracteristique),
    CONSTRAINT FK_idType_caracteristique FOREIGN KEY (id_type_caracteristique)
    REFERENCES type_caracteristique(id_type_caracteristique),
    CONSTRAINT FK_idProduit FOREIGN KEY (reference_produit)
    REFERENCES produit(reference_produit)

);