package com.projet.ecommerce.entrypoint.graphql;

import com.projet.ecommerce.entrypoint.graphql.pagination.PaginationQuery;
import com.projet.ecommerce.entrypoint.graphql.photo.PhotoMutation;
import com.projet.ecommerce.entrypoint.graphql.photo.PhotoQuery;
import com.projet.ecommerce.entrypoint.graphql.role.RoleMutation;
import com.projet.ecommerce.entrypoint.graphql.role.RoleQuery;
import com.projet.ecommerce.entrypoint.graphql.statistique.StatistiqueQuery;
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
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Configuration de graphql.
 */

@Configuration
public class GraphQlUtility {


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
	private PhotoMutation photoMutation;

	@Autowired
	private PaginationQuery paginationQuery;

	@Autowired
	private StatistiqueQuery statistiqueQuery;

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

				.type(photoQuery.produitWiring())
				.type(photoMutation.produitWiring())
				.type(roleQuery.produitWiring())
				.type(roleMutation.produitWiring())
				.type(utilisateurQuery.produitWiring())
				.type(utilisateurMutation.produitWiring())
				.type(paginationQuery.produitWiring())
				.type(statistiqueQuery.produitWiring())
				.build();
	}

	private GraphQLSchema graphQLSchema() {
		final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		Reader categorieSchemaFile = new InputStreamReader(classloader.getResourceAsStream("graphql/categorie.graphqls"));
		Reader photoSchemaFile = new InputStreamReader(classloader.getResourceAsStream("graphql/photo.graphqls"));
		Reader produitSchemaFile = new InputStreamReader(classloader.getResourceAsStream("graphql/produit.graphqls"));
		Reader utilisateurSchemaFile = new InputStreamReader(classloader.getResourceAsStream("graphql/utilisateur.graphqls"));
		Reader roleSchemaFile = new InputStreamReader(classloader.getResourceAsStream("graphql/role.graphqls"));
		Reader paginationSchemaFile = new InputStreamReader(classloader.getResourceAsStream("graphql/pagination.graphqls"));
		Reader avisClientSchemaFile = new InputStreamReader(classloader.getResourceAsStream("graphql/avisclient.graphqls"));
		Reader statistiqueSchemaFile = new InputStreamReader(classloader.getResourceAsStream("graphql/statistique.graphqls"));

		SchemaParser schemaParser = new SchemaParser();
		TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
		// chaque registre est fusionné dans le registre principal
		typeRegistry.merge(schemaParser.parse(categorieSchemaFile));
		typeRegistry.merge(schemaParser.parse(photoSchemaFile));
		typeRegistry.merge(schemaParser.parse(produitSchemaFile));
		typeRegistry.merge(schemaParser.parse(roleSchemaFile));
		typeRegistry.merge(schemaParser.parse(utilisateurSchemaFile));
		typeRegistry.merge(schemaParser.parse(paginationSchemaFile));
		typeRegistry.merge(schemaParser.parse(avisClientSchemaFile));
		typeRegistry.merge(schemaParser.parse(statistiqueSchemaFile));

		RuntimeWiring wiring = buildRuntimeWiring();

		return new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
	}


}
