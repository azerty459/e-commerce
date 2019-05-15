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

--Chiffrement mot de passe utilisateur
Update utilisateur
set mot_de_passe = '$2a$10$cdtSutUgNq8smztvyF.KYe6fO94Uc8gj5eB8DQ2Q8wYnXHkE/CIIO'
where adresse_email = 'utilisateur@gmail.com';

--Chiffrement mot de passe visiteur
Update utilisateur
set mot_de_passe = '$2a$10$3vM22.YrQhrtKFIBvVaf.OXi7zh4z5qG.blW3YDqhtYXSkKqOH3Ya'
where adresse_email = 'visiteur@gmail.com';
