package com.projet.ecommerce.entrypoint.graphql.statistique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.IUtilisateurBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class StatistiqueQuery {

    @Autowired
    private IProduitBusiness produitBusiness;

    @Autowired
    private ICategorieBusiness categorieBusiness;

    @Autowired
    private IUtilisateurBusiness utilisateurBusiness;

    public TypeRuntimeWiring produitWiring() {
        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Query");
        builder.dataFetcher("nbProduit", (DataFetchingEnvironment environment) -> produitBusiness.countProduits());
        builder.dataFetcher("nbCategorie", (DataFetchingEnvironment environment) -> categorieBusiness.countCategories());
        builder.dataFetcher("nbUtilisateur", (DataFetchingEnvironment environment) -> utilisateurBusiness.countUtilisateurs());
        builder.dataFetcher("nbProduitCategorie", (DataFetchingEnvironment environment) -> {
            return produitBusiness.countProduitsByCategorie();
        });

        return builder.build();
    }

}
