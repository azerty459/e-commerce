--liquibase formatted sql
--changeset Arthur Brandao:10

--Ajout role admin
Update utilisateur
set id_role = (Select r.id_role From role r where upper(r.nom) = 'ADMINISTRATEUR')
where adresse_email = 'a';

--Ajout role utilisateur
Update utilisateur
set id_role = (Select r.id_role From role r where upper(r.nom) = 'UTILISATEUR')
where adresse_email = 'utilisateur@gmail.com';

--Ajout role visiteur
Update utilisateur
set id_role = (Select r.id_role From role r where upper(r.nom) = 'VISITEUR')
where adresse_email = 'visiteur@gmail.com';
