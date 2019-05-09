package com.projet.ecommerce.entrypoint.graphql.categorie;

import com.projet.ecommerce.business.impl.CategorieBusiness;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategorieMutationTests {

	@Mock
	private CategorieBusiness categorieBusiness;

	@Mock
	private DataFetchingEnvironment dataFetchingEnvironment;

	@InjectMocks
	private CategorieMutation categorieMutation;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void produitWiring() {
		TypeRuntimeWiring typeRuntimeWiring = categorieMutation.produitWiring();
		Assert.assertEquals(typeRuntimeWiring.getTypeName(), "Mutation");
		Assert.assertEquals(typeRuntimeWiring.getTypeResolver(), null);
		Assert.assertNotNull(typeRuntimeWiring);
	}

	@Test
	public void testNbDataFetcher() {
		Map<String, DataFetcher> retourMap = categorieMutation.produitWiring().getFieldDataFetchers();
		Assert.assertNotNull(retourMap);
		Assert.assertEquals(6, retourMap.size());
	}

	@Test
	public void addCategorieParent() {
		Map<String, DataFetcher> retourMap = categorieMutation.produitWiring().getFieldDataFetchers();

		// On imite le comportement des getArgument
		Mockito.when(dataFetchingEnvironment.getArgument("nom")).thenReturn("Livre");

		Assert.assertNotNull(retourMap.get("addCategorieParent"));
		retourMap.get("addCategorieParent").get(dataFetchingEnvironment);
		// Test avec nb appel add avec bon param
		Mockito.verify(categorieBusiness, Mockito.times(1)).addParent("Livre");
		// Test avec nb appel add avec mauvais param
		Mockito.verify(categorieBusiness, Mockito.times(0)).addParent("Test");
	}

	@Test
	public void addCategorieEnfant() {
		Map<String, DataFetcher> retourMap = categorieMutation.produitWiring().getFieldDataFetchers();

		// On imite le comportement des getArgument
		Mockito.when(dataFetchingEnvironment.getArgument("nom")).thenReturn("Test");
		Mockito.when(dataFetchingEnvironment.getArgument("pere")).thenReturn(1);

		Assert.assertNotNull(retourMap.get("addCategorieEnfant"));
		retourMap.get("addCategorieEnfant").get(dataFetchingEnvironment);
		// Test avec nb appel add avec bon param
		Mockito.verify(categorieBusiness, Mockito.times(1)).addEnfant("Test", 1);
		// Test avec nb appel add avec mauvais param
		Mockito.verify(categorieBusiness, Mockito.times(0)).addEnfant("Test", 55);
	}

	@Test
	public void deleteCategorie() {
		Map<String, DataFetcher> retourMap = categorieMutation.produitWiring().getFieldDataFetchers();

		// On imite le comportement des getArgument
		Mockito.when(dataFetchingEnvironment.getArgument("id")).thenReturn(1);

		Assert.assertNotNull(retourMap.get("deleteCategorie"));
		retourMap.get("deleteCategorie").get(dataFetchingEnvironment);
		// Test avec nb appel add avec bon param
		Mockito.verify(categorieBusiness, Mockito.times(1)).delete(1);
		// Test avec nb appel add avec mauvais param
		Mockito.verify(categorieBusiness, Mockito.times(0)).delete(55);
	}

	@Test
	public void moveCategorie() {
		Map<String, DataFetcher> retourMap = categorieMutation.produitWiring().getFieldDataFetchers();

		// On imite le comportement des getArgument
		Mockito.when(dataFetchingEnvironment.getArgument("idADeplacer")).thenReturn(1);
		Mockito.when(dataFetchingEnvironment.getArgument("idNouveauParent")).thenReturn(3);

		Assert.assertNotNull(retourMap.get("moveCategorie"));
		retourMap.get("moveCategorie").get(dataFetchingEnvironment);
		// Test avec nb appel add avec bon param
		Mockito.verify(categorieBusiness, Mockito.times(1)).moveCategorie(1, 3);
		// Test avec nb appel add avec mauvais param
		Mockito.verify(categorieBusiness, Mockito.times(0)).moveCategorie(5, 7);
	}

	@Test
	public void updateCategorie() {
		Map<String, DataFetcher> retourMap = categorieMutation.produitWiring().getFieldDataFetchers();

		// On imite le comportement des getArgument
		Mockito.when(dataFetchingEnvironment.getArgument("id")).thenReturn(1);
		Mockito.when(dataFetchingEnvironment.getArgument("nom")).thenReturn("Toto");

		Assert.assertNotNull(retourMap.get("updateCategorie"));
		retourMap.get("updateCategorie").get(dataFetchingEnvironment);
		// Test avec nb appel add avec bon param
		Mockito.verify(categorieBusiness, Mockito.times(1)).updateCategorie(1, "Toto");
		// Test avec nb appel add avec mauvais param
		Mockito.verify(categorieBusiness, Mockito.times(0)).updateCategorie(7, "Test");
	}

	public void restoreCategorie() {
		// FIXME à faire
	}

}
