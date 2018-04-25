package com.projet.ecommerce.entrypoint.graphQL.categorie;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import graphql.schema.DataFetcher;
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
        builder.dataFetcher("addCategorieParent", new DataFetcher() {
            @Override
            public CategorieDTO get(DataFetchingEnvironment env) {
                return categorieBusiness.addParent(env.getArgument("nom"));
            }
        });
        builder.dataFetcher("addCategorieEnfant", new DataFetcher() {
            @Override
            public CategorieDTO get(DataFetchingEnvironment env) {
                return categorieBusiness.addEnfant(env.getArgument("nom"), env.getArgument("pere"));
            }
        });

        builder.dataFetcher("deleteCategorie", new DataFetcher() {
            @Override
            public Boolean get(DataFetchingEnvironment env) {
                return categorieBusiness.delete(env.getArgument("nom"));
            }
        });
        return builder.build();
    }
}
