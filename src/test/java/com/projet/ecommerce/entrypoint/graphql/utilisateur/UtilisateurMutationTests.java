package com.projet.ecommerce.entrypoint.graphql.utilisateur;

import com.projet.ecommerce.business.IUtilisateurBusiness;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
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

import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UtilisateurMutationTests {

	@Mock
	private IUtilisateurBusiness utilisateurBusiness;

	@Mock
	private DataFetchingEnvironment dataFetchingEnvironment;

	@InjectMocks
	private UtilisateurMutation utilisateurMutation;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void produitWiring() {
		TypeRuntimeWiring typeRuntimeWiring = utilisateurMutation.produitWiring();
		Assert.assertEquals(typeRuntimeWiring.getTypeName(), "Mutation");
		Assert.assertEquals(typeRuntimeWiring.getTypeResolver(), null);
		Assert.assertNotNull(typeRuntimeWiring);
	}

	@Test
	public void testNbDataFetcher() {
		Map<String, DataFetcher> retourMap = utilisateurMutation.produitWiring().getFieldDataFetchers();
		Assert.assertNotNull(retourMap);
		Assert.assertEquals(5, retourMap.size());
	}

	@Test
	public void addUtilisateur() {
		Map<String, DataFetcher> retourMap = utilisateurMutation.produitWiring().getFieldDataFetchers();

		Map<String, String> linkedHashMap = new LinkedHashMap<>();
		linkedHashMap.put("email", "test@gmail.com");
		linkedHashMap.put("mdp", "azerty");

		// On imite le comportement des getArgument
		Mockito.when(dataFetchingEnvironment.getArgument("utilisateur")).thenReturn(linkedHashMap);

		String nomFieldDataFetcher = "addUtilisateur";
		Assert.assertNotNull(retourMap.get(nomFieldDataFetcher));
		retourMap.get(nomFieldDataFetcher).get(dataFetchingEnvironment);

		// Test avec nb appel add avec bon param
		Mockito.verify(utilisateurBusiness, Mockito.times(1)).add(Mockito.any(UtilisateurDTO.class));
	}

	public void updateUtilisateur() {
		Map<String, DataFetcher> retourMap = utilisateurMutation.produitWiring().getFieldDataFetchers();

		Map<String, String> linkedHashMap = new LinkedHashMap<>();
		linkedHashMap.put("email", "test@gmail.com");
		linkedHashMap.put("mdp", "azerty");

		// On imite le comportement des getArgument
		Mockito.when(dataFetchingEnvironment.getArgument("utilisateur")).thenReturn(linkedHashMap);

		String nomFieldDataFetcher = "updateUtilisateur";
		Assert.assertNotNull(retourMap.get(nomFieldDataFetcher));
		retourMap.get(nomFieldDataFetcher).get(dataFetchingEnvironment);

		// Test avec nb appel add avec bon param
		Mockito.verify(utilisateurBusiness, Mockito.times(1)).update(Mockito.any(UtilisateurDTO.class));
	}

	@Test
	public void deleteUtilisateurByID() {
		Map<String, DataFetcher> retourMap = utilisateurMutation.produitWiring().getFieldDataFetchers();

		// On imite le comportement des getArgument
		Mockito.when(dataFetchingEnvironment.getArgument("email")).thenReturn(null);
		Mockito.when(dataFetchingEnvironment.getArgument("id")).thenReturn(1);

		String nomFieldDataFetcher = "deleteUtilisateur";
		Assert.assertNotNull(retourMap.get(nomFieldDataFetcher));
		retourMap.get(nomFieldDataFetcher).get(dataFetchingEnvironment);
		// Test avec nb appel add avec bon param pour ID
		Mockito.verify(utilisateurBusiness, Mockito.times(1)).delete(null, 1);
		// Test avec nb appel add avec mauvais param
		Mockito.verify(utilisateurBusiness, Mockito.times(0)).delete(null, 999);
	}

	@Test
	public void deleteUtilisateurByEmail() {
		Map<String, DataFetcher> retourMap = utilisateurMutation.produitWiring().getFieldDataFetchers();

		// On imite le comportement des getArgument
		Mockito.when(dataFetchingEnvironment.getArgument("email")).thenReturn("test");
		Mockito.when(dataFetchingEnvironment.getArgument("id")).thenReturn(null);

		String nomFieldDataFetcher = "deleteUtilisateur";
		Assert.assertNotNull(retourMap.get(nomFieldDataFetcher));
		retourMap.get(nomFieldDataFetcher).get(dataFetchingEnvironment);
		// Test avec nb appel add avec bon param pour ID
		Mockito.verify(utilisateurBusiness, Mockito.times(1)).delete("test", 0);
		// Test avec nb appel add avec mauvais param
		Mockito.verify(utilisateurBusiness, Mockito.times(0)).delete("fgdfgf", 0);
	}

	public void isLogged() {
		// FIXME à faire
	}

	public void signinUtilisateur() {
		// FIXME à faire
	}

}
