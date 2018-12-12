/**
 * ajout caracteristique pour un produit
 * @author nextoo
 */


CREATE TABLE type_caracteristique
(
  id_type_caracteristique SERIAL,
  type varchar(255),
  PRIMARY KEY (id_type_caracteristique)
);

CREATE TABLE caracteristique
(
  id_caracteristique SERIAL,
  reference_produit varchar(255),
  id_type_caracteristique int,
  valeur varchar(255),
  PRIMARY KEY (id_caracteristique),
  CONSTRAINT fk_reference_produit FOREIGN KEY (reference_produit)
  REFERENCES produit(reference_produit),
  CONSTRAINT fk_type_caracteristique FOREIGN KEY (id_type_caracteristique)
  REFERENCES type_caracteristique(id_type_caracteristique)
);


insert into type_caracteristique (type) values ('Broché');
insert into type_caracteristique (type) values ('Editeur');
insert into type_caracteristique (type) values ('Langue');
insert into type_caracteristique (type) values ('Dimensions du produit');

insert into caracteristique (reference_produit, id_type_caracteristique, valeur) values ('A05A01', '1', '233 pages');
insert into caracteristique (reference_produit, id_type_caracteristique, valeur) values ('A05A01', '2', 'Flammarion');
insert into caracteristique (reference_produit, id_type_caracteristique, valeur) values ('A05A01', '3', 'Français');
insert into caracteristique (reference_produit, id_type_caracteristique, valeur) values ('A05A01', '4', '22 X 15,5 X 1,7 cm');