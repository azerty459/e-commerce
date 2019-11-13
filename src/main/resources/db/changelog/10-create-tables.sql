CREATE TABLE characteristic_type
(
    id_characteristic_type INT          NOT NULL,
    type_characteristic    VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id_characteristic_type)
);

CREATE TABLE characteristic_value
(
    value_characteristic VARCHAR(255) NOT NULL,
    reference_product VARCHAR(255) NOT NULL,
    id_characteristic_type INT NOT NULL,
    PRIMARY KEY (reference_product, id_characteristic_type)
);