--liquibase formatted sql
--changeset Rémy Halipré:07
INSERT INTO photo (url,reference_produit) VALUES  ('/etc/picture/test1.jpg', 'A05A01'),('/etc/picture/test2.jpg', 'A05A01'),('/etc/picture/test3.jpg', 'A05A01');
INSERT INTO photo (url,reference_produit) VALUES  ('/etc/picture/a02-1.jpg', 'A05A02'),('/etc/picture/a02-2.jpg', 'A05A02');
INSERT INTO photo (url,reference_produit) VALUES  ('/etc/picture/a03-1.jpg', 'A05A03');