package com.projet.ecommerce.entrypoint.graphql.categorie;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.ICategorieSupprimeBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategorieMutation {

	@Autowired
	private ICategorieBusiness categorieBusiness;
	@Autowired
	private ICategorieSupprimeBusiness categorieSupprimeBusiness;

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

		builder.dataFetcher("updateCategorie", (DataFetchingEnvironment env) ->
				categorieBusiness.updateCategorie(env.getArgument("id"), env.getArgument("nom"))
		);
		builder.dataFetcher("restoreCategorie", (DataFetchingEnvironment env) ->
				categorieSupprimeBusiness.restoreLastDeletedCategorie(env.getArgument("idNouveauParent"))
		);

		return builder.build();
	}

}
