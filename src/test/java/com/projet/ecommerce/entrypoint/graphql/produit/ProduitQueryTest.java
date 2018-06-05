package com.projet.ecommerce.entrypoint.graphql.produit;

import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.entrypoint.graphQL.produit.ProduitQuery;
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
public class ProduitQueryTest {
    @Mock
    private ProduitBusiness produitBusiness;

    @Mock
    private DataFetchingEnvironment dataFetchingEnvironment;

    @InjectMocks
    private ProduitQuery produitQuery;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void produitWiring(){
        TypeRuntimeWiring typeRuntimeWiring = produitQuery.produitWiring();
        Assert.assertEquals(typeRuntimeWiring.getTypeName(), "Query");
        Assert.assertEquals(typeRuntimeWiring.getTypeResolver(), null);
        Assert.assertNotNull(typeRuntimeWiring);
    }

    @Test
    public void testNbDataFetcher() {
        Map<String, DataFetcher> retourMap = produitQuery.produitWiring().getFieldDataFetchers();
        Assert.assertNotNull(retourMap);
        Assert.assertEquals(1,retourMap.size());
    }

    @Test
    public void produitsWithParamRef(){
        Map<String, DataFetcher> retourMap = produitQuery.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        Mockito.when(dataFetchingEnvironment.getArgument("ref")).thenReturn("A047A");
        Mockito.when(dataFetchingEnvironment.getArgument("cat")).thenReturn(null);

        Assert.assertNotNull(retourMap.get("produits"));
        retourMap.get("produits").get(dataFetchingEnvironment);
        // Test avec nb appel add avec bon param
        Mockito.verify(produitBusiness, Mockito.times(1)).getAll("A047A", null);
        // Test avec nb appel add avec mauvais param
        Mockito.verify(produitBusiness, Mockito.times(0)).getAll(null, null);
    }

    @Test
    public void produitsWithParamCat(){
        Map<String, DataFetcher> retourMap = produitQuery.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        Mockito.when(dataFetchingEnvironment.getArgument("ref")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("cat")).thenReturn("Livre");

        Assert.assertNotNull(retourMap.get("produits"));
        retourMap.get("produits").get(dataFetchingEnvironment);
        // Test avec nb appel add avec bon param
        Mockito.verify(produitBusiness, Mockito.times(1)).getAll(null, "Livre");
        // Test avec nb appel add avec mauvais param
        Mockito.verify(produitBusiness, Mockito.times(0)).getAll(null, null);
    }

    @Test
    public void produitsWithParamRefAndCat(){
        Map<String, DataFetcher> retourMap = produitQuery.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        Mockito.when(dataFetchingEnvironment.getArgument("ref")).thenReturn(null);
        Mockito.when(dataFetchingEnvironment.getArgument("cat")).thenReturn(null);

        Assert.assertNotNull(retourMap.get("produits"));
        retourMap.get("produits").get(dataFetchingEnvironment);
        // Test avec nb appel add avec bon param
        Mockito.verify(produitBusiness, Mockito.times(1)).getAll(null, null);
        // Test avec nb appel add avec mauvais param
        Mockito.verify(produitBusiness, Mockito.times(0)).getAll("", null);
    }
}
