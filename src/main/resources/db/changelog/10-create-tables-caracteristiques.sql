CREATE TABLE public.type_caracteristique (
	id_type_caracteristique serial NOT NULL,
	libelle varchar(255) NULL,
	CONSTRAINT type_caracteristique_pkey PRIMARY KEY (id_type_caracteristique),
	CONSTRAINT uk_8wdi2h7kjkt3xno00run9ab7u UNIQUE (libelle)
);


CREATE TABLE public.caracteristique (
	id_caracteristique serial NOT NULL,
	value varchar(255) NULL,
	reference_produit varchar(255) NULL,
	id_type_caracteristique int4 NULL,
	CONSTRAINT caracteristique_pkey PRIMARY KEY (id_caracteristique),
	CONSTRAINT unique_type_par_produit UNIQUE (reference_produit, id_type_caracteristique),
	CONSTRAINT fk9i2bxr1lygmsex7jl79jxjiov FOREIGN KEY (reference_produit) REFERENCES produit(reference_produit),
	CONSTRAINT fkc03nisaf6tijm34gi4kuy2mqa FOREIGN KEY (id_type_caracteristique) REFERENCES type_caracteristique(id_type_caracteristique),
	CONSTRAINT fknwl5ak3iaw5jwjm8wcgbor390 FOREIGN KEY (id_caracteristique) REFERENCES caracteristique(id_caracteristique)
);