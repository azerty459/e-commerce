--liquibase formatted sql
--changeset aioossen


create table caracteristique
(
    reference_produit varchar(255) not null
        constraint caracteristique_reference_produit_fkey
            references produit,
    id_type           integer      not null
        constraint caracteristique_id_type_fkey
            references type_caracteristique,
    valeur            varchar(255),
    constraint caracteristique_pkey
        primary key (reference_produit, id_type)
);


INSERT INTO public.caracteristique (reference_produit, type_id, valeur) VALUES ('A05A01', 3, 'FRANCAIS');
INSERT INTO public.caracteristique (reference_produit, type_id, valeur) VALUES ('A06A02', 2, 'BAUDELAIRE');
