package com.projet.ecommerce.entrypoint.graphql.statistique;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.IUtilisateurBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
