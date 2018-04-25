package com.projet.ecommerce.graphql;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.entrypoint.graphQL.Query;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestQuery {
    @Mock
    private ProduitBusiness produitBusiness;

    @Mock
    private CategorieBusiness categorieBusiness;

    @InjectMocks
    private Query query;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /* Tests des Query pour les produits */

      @Test
      public void getAllProduits() {
        List<ProduitDTO> produitsDTO = new ArrayList<>();
        Mockito.when(produitBusiness.getAll(null,null)).thenReturn(produitsDTO);
        List<ProduitDTO> retour = query.produits(null,null);

        // on s'assure que la première fois, la liste retournée est vide
        Assert.assertNotNull(retour);
        Assert.assertEquals(retour.size(), 0);

        // Ajout de produits
        produitsDTO.add(new ProduitDTO());
        retour = query.produits(null,null);
        // on s'assure que la liste comporte bien un élément
        Assert.assertEquals(retour.size(), 1);
       }

    @Test
    public void getProduitByRef(){
        ProduitDTO produit = new ProduitDTO();
        produit.setRef("A01A01");
        Mockito.when(produitBusiness.getByRef("A01A01")).thenReturn(null);
        Mockito.when(produitBusiness.getByRef("A01A01")).thenReturn(produit);

        List<ProduitDTO> retour = query.produits("A2442", null);
        // lorsque la référence produit n'existe pas, on reçoit une liste vide
        Assert.assertTrue(retour.isEmpty());

        retour = query.produits("A01A01", null);
        // lorsque la référence produit existe, on reçoit une liste contenant un seul élément
        Assert.assertTrue(retour.size() == 1);
        Assert.assertTrue(retour.contains(produit));
        String reference = produit.getRef();
        Assert.assertEquals(reference, "A01A01");
    }

    @Test
    public void getByCategorie(){
        List<ProduitDTO> produitsDTO = new ArrayList<>();
        produitsDTO.add(new ProduitDTO());
        Mockito.when(produitBusiness.getByCategorie("inexistant")).thenReturn(null);
        Mockito.when(produitBusiness.getByCategorie("livre")).thenReturn(produitsDTO);

        List<ProduitDTO> retour = query.produits(null, "inexistant");
        // quand il n'y a aucun produit pour cette catégorie, on reçoit null
        Assert.assertNull(retour);

        retour = query.produits(null,"livre");
        // quand il y a des produits pour cette catégorie, on les reçoit
        Assert.assertEquals(produitsDTO, retour);
    }

    /* Tests des Query pour les catégories */

    @Test
    public void getAllCategorie(){
        List<CategorieDTO> categories = new ArrayList<>();
        Mockito.when(categorieBusiness.getAll()).thenReturn(categories);
        List<CategorieDTO> retour = query.categories(null);

        // on s'assure que la première fois, la liste retournée est vide
        Assert.assertNotNull(retour);
        Assert.assertEquals(retour.size(), 0);

        categories.add(new CategorieDTO());
        retour = query.categories(null);
        // on s'assure que la liste comporte bien un élément
        Assert.assertEquals(retour.size(), 1);
    }

    @Test
    public void getCategorieByNom(){
        CategorieDTO categorie = new CategorieDTO();
        categorie.setNom("test");
        Mockito.when(categorieBusiness.getByNom("inexistant")).thenReturn(null);
        Mockito.when(categorieBusiness.getByNom("test")).thenReturn(categorie);

        List<CategorieDTO> retour = query.categories("inexistant");
        // lorsque le nom de catégorie n'existe pas, on reçoit null
        Assert.assertEquals(retour.size(), 0); ;

        retour = query.categories("test");
        // lorsque le nom de catégorie existe, on reçoit la catégorie correspondante
        Assert.assertEquals(retour.size(), 1);
        Assert.assertTrue(retour.contains(categorie));
    }
}
