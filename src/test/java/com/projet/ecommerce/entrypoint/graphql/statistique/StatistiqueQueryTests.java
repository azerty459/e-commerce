package com.projet.ecommerce.entrypoint.graphql.statistique;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class StatistiqueQueryTests {

    @Mock
    private ProduitBusiness produitBusiness;

    @Mock
    private CategorieBusiness categorieBusiness;

    @Mock
    private UtilisateurBusiness utilisateurBusiness;

    @Mock
    private DataFetchingEnvironment dataFetchingEnvironment;

    @InjectMocks
    private StatistiqueQuery statistiqueQuery;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void produitWiring() {
        TypeRuntimeWiring typeRuntimeWiring = statistiqueQuery.produitWiring();
        Assert.assertEquals("Query", typeRuntimeWiring.getTypeName());
        Assert.assertNull(typeRuntimeWiring.getTypeResolver());
        Assert.assertNotNull(typeRuntimeWiring);
    }

    @Test
    public void testNbDataFetcher() {
        Map<String, DataFetcher> champs = statistiqueQuery.produitWiring().getFieldDataFetchers();
        Assert.assertNotNull(champs);
        Assert.assertEquals(4, champs.size());
    }

    @Test
    public void nbProduit() {
        Mockito.when(produitBusiness.countProduits()).thenReturn(8L);
        Map<String, DataFetcher> champs = statistiqueQuery.produitWiring().getFieldDataFetchers();
        Assert.assertEquals(8L, champs.get("nbProduit").get(dataFetchingEnvironment));
    }

    @Test
    public void nbCategorie() {
        Mockito.when(categorieBusiness.countCategories()).thenReturn(8L);
        Map<String, DataFetcher> champs = statistiqueQuery.produitWiring().getFieldDataFetchers();
        Assert.assertEquals(8L, champs.get("nbCategorie").get(dataFetchingEnvironment));
    }

    @Test
    public void nbUtilisateur() {
        Mockito.when(utilisateurBusiness.countUtilisateurs()).thenReturn(8L);
        Map<String, DataFetcher> champs = statistiqueQuery.produitWiring().getFieldDataFetchers();
        Assert.assertEquals(8L, champs.get("nbUtilisateur").get(dataFetchingEnvironment));
    }

    @Test
    public void nbProduitCategorie() {
        CategorieDTO categorieDTO1 = new CategorieDTO();
        categorieDTO1.setId(1);
        categorieDTO1.setNom("Un");
        CategorieDTO categorieDTO2 = new CategorieDTO();
        categorieDTO2.setId(2);
        categorieDTO2.setNom("Deux");
        Map<CategorieDTO, Long> retour = new HashMap<>();
        retour.put(categorieDTO1, 1L);
        retour.put(categorieDTO2, 2L);
        Mockito.when(produitBusiness.countProduitsByCategorie()).thenReturn(retour);

        Map<String, DataFetcher> champs = statistiqueQuery.produitWiring().getFieldDataFetchers();
        List<Map<String, Object>> resultat = (List<Map<String, Object>>) champs.get("nbProduitCategorie").get(dataFetchingEnvironment);
        Assert.assertEquals(2, resultat.size());
        for(Map<String, Object> donnees : resultat) {
            String nomCategorie = (String) donnees.get("categorie");
            long nb = (Long) donnees.get("nb");
            switch (nomCategorie) {
                case "Un":
                    Assert.assertEquals(1L, nb);
                    break;
                case "Deux":
                    Assert.assertEquals(2L, nb);
                    break;
                default:
                    Assert.fail();
            }
        }
    }

}
