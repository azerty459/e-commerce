package com.projet.ecommerce.entrypoint.graphql;

import com.projet.ecommerce.entrypoint.graphql.categorie.CategorieMutation;
import com.projet.ecommerce.entrypoint.graphql.categorie.CategorieQuery;
import com.projet.ecommerce.entrypoint.graphql.pagination.PaginationQuery;
import com.projet.ecommerce.entrypoint.graphql.photo.PhotoQuery;
import com.projet.ecommerce.entrypoint.graphql.produit.ProduitMutation;
import com.projet.ecommerce.entrypoint.graphql.produit.ProduitQuery;
import com.projet.ecommerce.entrypoint.graphql.role.RoleMutation;
import com.projet.ecommerce.entrypoint.graphql.role.RoleQuery;
import com.projet.ecommerce.entrypoint.graphql.utilisateur.UtilisateurMutation;
import com.projet.ecommerce.entrypoint.graphql.utilisateur.UtilisateurQuery;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Configuration de graphql.
 */

@Configuration
public class GraphQlUtility {

    @Autowired
    private ProduitQuery produitQuery;

    @Autowired
    private ProduitMutation produitMutation;

    @Autowired
    private CategorieQuery categorieQuery;

    @Autowired
    private CategorieMutation categorieMutation;

    @Autowired
    private RoleQuery roleQuery;

    @Autowired
    private RoleMutation roleMutation;

    @Autowired
    private UtilisateurQuery utilisateurQuery;

    @Autowired
    private UtilisateurMutation utilisateurMutation;

    @Autowired
    private PhotoQuery photoQuery;

    @Autowired
    private PaginationQuery paginationQuery;

    @PostConstruct
    public GraphQL createGraphQlObject() {
        return GraphQL.newGraphQL(graphQLSchema())
                .build();
    }

    public List<GraphQLError> graphQLErrorHandler(List<GraphQLError> graphQLErrors) {
        List<GraphQLError> clientErrors = graphQLErrors.stream()
                .filter(this::isClientError)
                .collect(Collectors.toList());

        List<GraphQLError> serverErrors = graphQLErrors.stream()
                .filter(e -> !isClientError(e))
                .map(GraphQLErrorAdapter::new)
                .collect(Collectors.toList());

        List<GraphQLError> returnGraphQLErrors = new ArrayList<>();
        returnGraphQLErrors.addAll(clientErrors);
        returnGraphQLErrors.addAll(serverErrors);
        return returnGraphQLErrors;
    }

    private boolean isClientError(GraphQLError error) {
        return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(produitQuery.produitWiring())
                .type(produitMutation.produitWiring())
                .type(photoQuery.produitWiring())
                .type(categorieQuery.produitWiring())
                .type(categorieMutation.produitWiring())
                .type(roleQuery.produitWiring())
                .type(roleMutation.produitWiring())
                .type(utilisateurQuery.produitWiring())
                .type(utilisateurMutation.produitWiring())
                .type(paginationQuery.produitWiring())
                .build();
    }

    private GraphQLSchema graphQLSchema() {

        File categorieSchemaFile = new File("src/main/resources/graphql/categorie.graphqls");
        File photoSchemaFile = new File("src/main/resources/graphql/photo.graphqls");
        File produitSchemaFile = new File("src/main/resources/graphql/produit.graphqls");
        File utilisateurSchemaFile = new File("src/main/resources/graphql/utilisateur.graphqls");
        File roleSchemaFile = new File("src/main/resources/graphql/role.graphqls");
        File paginationSchemaFile = new File("src/main/resources/graphql/pagination.graphqls");

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        // chaque registre est fusionné dans le registre principal
        typeRegistry.merge(schemaParser.parse(categorieSchemaFile));
        typeRegistry.merge(schemaParser.parse(photoSchemaFile));
        typeRegistry.merge(schemaParser.parse(produitSchemaFile));
        typeRegistry.merge(schemaParser.parse(roleSchemaFile));
        typeRegistry.merge(schemaParser.parse(utilisateurSchemaFile));
        typeRegistry.merge(schemaParser.parse(paginationSchemaFile));

        RuntimeWiring wiring = buildRuntimeWiring();


        return new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
    }
}