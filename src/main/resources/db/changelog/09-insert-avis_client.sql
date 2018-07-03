--liquibase formatted sql
--changeset Gwennael Lonvert:09

INSERT INTO avis_client (description, note, reference_produit) VALUES ('Ce produit est top !', 5, 'A05A01');
INSERT INTO avis_client (description, note, reference_produit) VALUES ('Pas mal', 4, 'A05A01');

INSERT INTO avis_client (description, note, reference_produit) VALUES ('Parfait', 5 , 'A05A02');

INSERT INTO avis_client (description, note, reference_produit) VALUES ('Mouais ...', 2, 'A05A03');

INSERT INTO avis_client (description, note, reference_produit) VALUES ('Décevant !', 1, 'A07A03');

