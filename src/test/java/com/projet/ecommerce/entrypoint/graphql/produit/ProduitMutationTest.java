package com.projet.ecommerce.entrypoint.graphql.produit;

import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.entrypoint.graphQL.produit.ProduitMutation;
import com.projet.ecommerce.persistance.entity.Produit;
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
public class ProduitMutationTest {
    @Mock
    private ProduitBusiness produitBusiness;

    @Mock
    private DataFetchingEnvironment dataFetchingEnvironment;

    @InjectMocks
    private ProduitMutation produitMutation;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void produitWiring(){
        TypeRuntimeWiring typeRuntimeWiring = produitMutation.produitWiring();
        Assert.assertEquals(typeRuntimeWiring.getTypeName(), "Mutation");
        Assert.assertEquals(typeRuntimeWiring.getTypeResolver(), null);
        Assert.assertNotNull(typeRuntimeWiring);
    }

    @Test
    public void testNbDataFetcher() {
        Map<String, DataFetcher> retourMap = produitMutation.produitWiring().getFieldDataFetchers();
        Assert.assertNotNull(retourMap);
        Assert.assertEquals(3,retourMap.size());
    }

    @Test
    public void addProduit(){
        Map<String, DataFetcher> retourMap = produitMutation.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        Mockito.when(dataFetchingEnvironment.getArgument("ref")).thenReturn("A09A87");
        Mockito.when(dataFetchingEnvironment.getArgument("nom")).thenReturn("test");
        Mockito.when(dataFetchingEnvironment.getArgument("description")).thenReturn("test");
        Mockito.when(dataFetchingEnvironment.getArgument("prixHT")).thenReturn(4.7f);

        Assert.assertNotNull(retourMap.get("addProduit"));
        retourMap.get("addProduit").get(dataFetchingEnvironment);
        // Test avec nb appel add avec bon param
        Mockito.verify(produitBusiness, Mockito.times(1)).add("A09A87", "test", "test", 4.7f, null);
        // Test avec nb appel add avec mauvais param
        Mockito.verify(produitBusiness, Mockito.times(0)).add("A09A82", "test", "test", 4.7f, null);
    }

    @Test
    //TODO Revoir le test
    public void updateProduit(){
        Map<String, DataFetcher> retourMap = produitMutation.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        Produit produit = new Produit();
        Mockito.when(dataFetchingEnvironment.getArgument("produit")).thenReturn(produit);

        Assert.assertNotNull(retourMap.get("updateProduit"));
        retourMap.get("updateProduit").get(dataFetchingEnvironment);
        // Test avec nb appel add avec bon param
        Mockito.verify(produitBusiness, Mockito.times(1)).update(produit);
        // Test avec nb appel add avec mauvais param
        Mockito.verify(produitBusiness, Mockito.times(0)).update(new Produit());
    }

    @Test
    public void deleteProduit(){
        Map<String, DataFetcher> retourMap = produitMutation.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        Mockito.when(dataFetchingEnvironment.getArgument("ref")).thenReturn("A09A87");

        Assert.assertNotNull(retourMap.get("deleteProduit"));
        retourMap.get("deleteProduit").get(dataFetchingEnvironment);
        // Test avec nb appel add avec bon param
        Mockito.verify(produitBusiness, Mockito.times(1)).delete("A09A87");
        // Test avec nb appel add avec mauvais param
        Mockito.verify(produitBusiness, Mockito.times(0)).delete("A09A82");
    }
}
