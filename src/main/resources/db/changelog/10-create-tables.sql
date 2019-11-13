CREATE TABLE characteristic_type
(
    id_characteristic_type SERIAL,
    type_characteristic    VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id_characteristic_type)
);

CREATE TABLE characteristic_value
(
    value_characteristic VARCHAR(255) NOT NULL,
    reference_product VARCHAR(255) NOT NULL,
    id_characteristic_type INT NOT NULL,
    PRIMARY KEY (reference_product, id_characteristic_type),
    CONSTRAINT FK_reference_product FOREIGN KEY (reference_product) REFERENCES produit (reference_produit),
    CONSTRAINT FK_id_characteristic_type FOREIGN KEY (id_characteristic_type) REFERENCES characteristic_type (id_characteristic_type)
);