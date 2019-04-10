--liquibase formatted sql
--changeset Gwennael Lonvert:09


CREATE TABLE avis_client
(
  id_avis SERIAL,
  date_avis TIMESTAMP DEFAULT current_timestamp,
  description VARCHAR(255) NOT NULL,
  note int NOT NULL,
  reference_produit VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_avis),
  CONSTRAINT FK_idProduit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit)
);



INSERT INTO avis_client (description, note, reference_produit) VALUES ('Ce produit est top !', 5, 'A05A01');
INSERT INTO avis_client (description, note, reference_produit) VALUES ('Pas mal', 4, 'A05A01');

INSERT INTO avis_client (description, note, reference_produit) VALUES ('Parfait', 5 , 'A05A02');

INSERT INTO avis_client (description, note, reference_produit) VALUES ('Mouais ...', 2, 'A05A03');

INSERT INTO avis_client (description, note, reference_produit) VALUES ('Décevant !', 1, 'A07A03');


