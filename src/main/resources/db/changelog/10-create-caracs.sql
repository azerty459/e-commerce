
CREATE TABLE "public"."type_caracteristique" (
    "id_type_carac" int4 NOT NULL,
    "libelle" varchar(255),
    PRIMARY KEY ("id_type_carac")
);

CREATE TABLE "public"."caracteristique" (
    "id_carac" int4 NOT NULL,
    "valeur" varchar(255),
    "prod_reference_produit" varchar(255) NOT NULL,
    "typec_id_type_carac" int4,
    CONSTRAINT "fkjsvlcmgyndjr6su8ub0fr05t0" FOREIGN KEY ("typec_id_type_carac") REFERENCES "public"."type_caracteristique"("id_type_carac"),
    CONSTRAINT "fkg5f7juxkdhsqkryvw4r866cwi" FOREIGN KEY ("prod_reference_produit") REFERENCES "public"."produit"("reference_produit"),
    PRIMARY KEY ("prod_reference_produit","id_carac")
);
