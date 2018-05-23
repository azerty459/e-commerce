--liquibase formatted sql
--changeset Rémy Halipré:01
ALTER TABLE produit
ALTER COLUMN description SET DEFAULT '';
