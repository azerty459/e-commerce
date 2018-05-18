package com.projet.ecommerce.entrypoint.graphql.categorie;

import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.entrypoint.graphQL.categorie.CategorieQuery;
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
public class TestCategorieQuery {
    @Mock
    private CategorieBusiness categorieBusiness;

    @Mock
    private DataFetchingEnvironment dataFetchingEnvironment;

    @InjectMocks
    private CategorieQuery categorieQuery;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void produitWiring(){
        TypeRuntimeWiring typeRuntimeWiring = categorieQuery.produitWiring();
        Assert.assertEquals(typeRuntimeWiring.getTypeName(), "Query");
        Assert.assertEquals(typeRuntimeWiring.getTypeResolver(), null);
        Assert.assertNotNull(typeRuntimeWiring);
    }

    @Test
    public void testNbDataFetcher() {
        Map<String, DataFetcher> retourMap = categorieQuery.produitWiring().getFieldDataFetchers();
        Assert.assertNotNull(retourMap);
        Assert.assertEquals(1,retourMap.size());
    }

    @Test
    public void categories(){
        Map<String, DataFetcher> retourMap = categorieQuery.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        Mockito.when(dataFetchingEnvironment.getArgument("nom")).thenReturn("Livre");

        Assert.assertNotNull(retourMap.get("categories"));
        retourMap.get("categories").get(dataFetchingEnvironment);
        // Test avec nb appel add avec bon param
        Mockito.verify(categorieBusiness, Mockito.times(1)).getCategorie("Livre", false, false);
        // Test avec nb appel add avec mauvais param
        Mockito.verify(categorieBusiness, Mockito.times(0)).getCategorie(null, false, false);
    }
}
