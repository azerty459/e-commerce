package com.projet.ecommerce.entrypoint.graphql.pagination;

import com.projet.ecommerce.business.impl.PaginationBusiness;
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
public class PaginationQueryTests {
    @Mock
    private PaginationBusiness paginationBusiness;

    @Mock
    private DataFetchingEnvironment dataFetchingEnvironment;

    @InjectMocks
    private PaginationQuery paginationQuery;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void produitWiring() {
        TypeRuntimeWiring typeRuntimeWiring = paginationQuery.produitWiring();
        Assert.assertEquals(1, typeRuntimeWiring.getFieldDataFetchers().size());
        Assert.assertEquals(typeRuntimeWiring.getTypeName(), "Query");
        Assert.assertEquals(typeRuntimeWiring.getTypeResolver(), null);
        Assert.assertNotNull(typeRuntimeWiring);
    }

    @Test
    public void testNbDataFetcher() {
        Map<String, DataFetcher> retourMap = paginationQuery.produitWiring().getFieldDataFetchers();
        Assert.assertNotNull(retourMap);
        Assert.assertEquals(1, retourMap.size());
    }

    @Test
    public void pagination() {
        Map<String, DataFetcher> retourMap = paginationQuery.produitWiring().getFieldDataFetchers();

        // On imite le comportement des getArgument
        Mockito.when(dataFetchingEnvironment.getArgument("type")).thenReturn("produit");
        Mockito.when(dataFetchingEnvironment.getArgument("page")).thenReturn(1);
        Mockito.when(dataFetchingEnvironment.getArgument("npp")).thenReturn(5);

        Assert.assertNotNull(retourMap.get("pagination"));
        retourMap.get("pagination").get(dataFetchingEnvironment);
        // Test avec nb appel add avec bon param
        Mockito.verify(paginationBusiness, Mockito.times(1)).getPagination("produit", 1, 5, null);
        // Test avec nb appel add avec mauvais param
        Mockito.verify(paginationBusiness, Mockito.times(0)).getPagination(null, 0, 0, null);
    }
}
