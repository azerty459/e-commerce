package com.projet.ecommerce.entrypoint.graphql.utilisateur;

import com.projet.ecommerce.business.IUtilisateurBusiness;
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
public class UtilisateurQueryTests {

    @Mock
    private IUtilisateurBusiness utilisateurBusiness;

    @Mock
    private DataFetchingEnvironment dataFetchingEnvironment;

    @InjectMocks
    private UtilisateurQuery utilisateurQuery;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void produitWiring() {
        TypeRuntimeWiring typeRuntimeWiring = utilisateurQuery.produitWiring();
        Assert.assertEquals(typeRuntimeWiring.getTypeName(), "Query");
        Assert.assertEquals(typeRuntimeWiring.getTypeResolver(), null);
        Assert.assertNotNull(typeRuntimeWiring);
    }

    @Test
    public void testNbDataFetcher() {
        Map<String, DataFetcher> retourMap = utilisateurQuery.produitWiring().getFieldDataFetchers();
        Assert.assertNotNull(retourMap);
        Assert.assertEquals(1, retourMap.size());
    }

    @Test
    public void utilisateursByID() {
        Map<String, DataFetcher> retourMap = utilisateurQuery.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        Mockito.when(dataFetchingEnvironment.getArgument("id")).thenReturn(1);
        Mockito.when(dataFetchingEnvironment.getArgument("email")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("nom")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("prenom")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("role")).thenReturn(null);

        // Tests
        String nomDateFetcher = "utilisateurs";
        Assert.assertNotNull(retourMap.get(nomDateFetcher));
        retourMap.get(nomDateFetcher).get(dataFetchingEnvironment);
        // Test avec nb appel
        Mockito.verify(utilisateurBusiness, Mockito.times(1)).getUtilisateur(1, null, null, null, null);
    }

    @Test
    public void utilisateursByEmail() {
        Map<String, DataFetcher> retourMap = utilisateurQuery.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        String email = "test@gmail.com";
        Mockito.when(dataFetchingEnvironment.getArgument("id")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("email")).thenReturn(email);
        Mockito.when(dataFetchingEnvironment.getArgument("nom")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("prenom")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("role")).thenReturn(null);

        // Tests
        String nomDateFetcher = "utilisateurs";
        Assert.assertNotNull(retourMap.get(nomDateFetcher));
        retourMap.get(nomDateFetcher).get(dataFetchingEnvironment);
        // Test avec nb appel
        Mockito.verify(utilisateurBusiness, Mockito.times(1)).getUtilisateur(0, email, null, null, null);
    }

    @Test
    public void utilisateursByNom() {
        Map<String, DataFetcher> retourMap = utilisateurQuery.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        String nom = "Toto";
        Mockito.when(dataFetchingEnvironment.getArgument("id")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("email")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("nom")).thenReturn(nom);
        Mockito.when(dataFetchingEnvironment.getArgument("prenom")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("role")).thenReturn(null);

        // Tests
        String nomDateFetcher = "utilisateurs";
        Assert.assertNotNull(retourMap.get(nomDateFetcher));
        retourMap.get(nomDateFetcher).get(dataFetchingEnvironment);
        // Test avec nb appel
        Mockito.verify(utilisateurBusiness, Mockito.times(1)).getUtilisateur(0, null, nom, null, null);
    }

    @Test
    public void utilisateursByPrenom() {
        Map<String, DataFetcher> retourMap = utilisateurQuery.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        String prenom = "Toto";
        Mockito.when(dataFetchingEnvironment.getArgument("id")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("email")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("nom")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("prenom")).thenReturn(prenom);
        Mockito.when(dataFetchingEnvironment.getArgument("role")).thenReturn(null);

        // Tests
        String nomDateFetcher = "utilisateurs";
        Assert.assertNotNull(retourMap.get(nomDateFetcher));
        retourMap.get(nomDateFetcher).get(dataFetchingEnvironment);
        // Test avec nb appel
        Mockito.verify(utilisateurBusiness, Mockito.times(1)).getUtilisateur(0, null, null, prenom, null);
    }

    @Test
    public void utilisateursByRole() {
        Map<String, DataFetcher> retourMap = utilisateurQuery.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        String role = "Utilisateur";
        Mockito.when(dataFetchingEnvironment.getArgument("id")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("email")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("nom")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("prenom")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("role")).thenReturn(role);

        // Tests
        String nomDateFetcher = "utilisateurs";
        Assert.assertNotNull(retourMap.get(nomDateFetcher));
        retourMap.get(nomDateFetcher).get(dataFetchingEnvironment);
        // Test avec nb appel
        Mockito.verify(utilisateurBusiness, Mockito.times(1)).getUtilisateur(0, null, null, null, role);
    }
}
