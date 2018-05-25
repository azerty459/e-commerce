package com.projet.ecommerce.entrypoint.graphQL.categorie;

import com.projet.ecommerce.business.ICategorieBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategorieMutation {

    @Autowired
    private ICategorieBusiness categorieBusiness;

    public TypeRuntimeWiring produitWiring() {
        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Mutation");
        builder.dataFetcher("addCategorieParent", (DataFetchingEnvironment env) ->
                categorieBusiness.addParent(env.getArgument("nom"))
        );

        builder.dataFetcher("addCategorieEnfant", (DataFetchingEnvironment env) ->
                categorieBusiness.addEnfant(env.getArgument("nom"), env.getArgument("pere"))
        );

        builder.dataFetcher("deleteCategorie", (DataFetchingEnvironment env) ->
                categorieBusiness.delete(env.getArgument("id"))
        );

        builder.dataFetcher("moveCategorie", (DataFetchingEnvironment env) ->
                categorieBusiness.moveCategorie(env.getArgument("idADeplacer"), env.getArgument("idNouveauParent"))
        );
        return builder.build();
    }
}
