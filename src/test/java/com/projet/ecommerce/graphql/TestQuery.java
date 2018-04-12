package com.projet.ecommerce.graphql;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.entrypoint.graphQL.Query;
import com.projet.ecommerce.persistance.entity.Categorie;
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

    @Test
    public void getAllProduit() {
        List<ProduitDTO> produitsDTO = new ArrayList<>();
        Mockito.when(produitBusiness.getProduit()).thenReturn(produitsDTO);
        List<ProduitDTO> retour = query.getAllProduit();

        // on s'assure que la première fois, la liste retournée est vide
        Assert.assertNotNull(retour);
        Assert.assertEquals(retour.size(), 0);

        produitsDTO.add(new ProduitDTO());
        retour = query.getAllProduit();
        // on s'assure que la liste comporte bien un élément
        Assert.assertEquals(retour.size(), 1);
    }

    @Test
    public void getProduitById(){
        ProduitDTO produit = new ProduitDTO();
        produit.setReferenceProduit("A4224");
        Mockito.when(produitBusiness.getProduitByID("A2442")).thenReturn(null);
        Mockito.when(produitBusiness.getProduitByID("A4224")).thenReturn(produit);

        ProduitDTO retour = query.getProduitByID("A2442");
        // lorsque la référence produit n'existe pas, on reçoit null
        Assert.assertNull(retour);

        retour = query.getProduitByID("A4224");
        // lorsque la référence produit existe, on reçoit le produit correspondant
        Assert.assertEquals(produit.getReferenceProduit(), retour.getReferenceProduit());
    }

    @Test
    public void getProduitByCategorie(){
        List<ProduitDTO> produitsDTO = new ArrayList<>();
        produitsDTO.add(new ProduitDTO());
        Mockito.when(produitBusiness.getProduitByCategorie("inexistant")).thenReturn(null);
        Mockito.when(produitBusiness.getProduitByCategorie("livre")).thenReturn(produitsDTO);

        List<ProduitDTO> retour = query.getProduitByCategorie("inexistant");
        // quand il n'y a aucun produit pour cette catégorie, on reçoit null
        Assert.assertNull(retour);

        retour = query.getProduitByCategorie("livre");
        // quand il y a des produits pour cette catégorie, on les reçoit
        Assert.assertEquals(produitsDTO, retour);
    }

    @Test
    public void getAllCategorie(){
        List<CategorieDTO> categories = new ArrayList<>();
        Mockito.when(categorieBusiness.getCategorie()).thenReturn(categories);
        List<CategorieDTO> retour = query.getAllCategorie();

        // on s'assure que la première fois, la liste retournée est vide
        Assert.assertNotNull(retour);
        Assert.assertEquals(retour.size(), 0);

        categories.add(new CategorieDTO());
        retour = query.getAllCategorie();
        // on s'assure que la liste comporte bien un élément
        Assert.assertEquals(retour.size(), 1);
    }

    @Test
    public void getCategorieByID(){
        CategorieDTO categorie = new CategorieDTO();
        categorie.setNomCategorie("test");
        Mockito.when(categorieBusiness.getCategorieByID("inexistant")).thenReturn(null);
        Mockito.when(categorieBusiness.getCategorieByID("test")).thenReturn(categorie);

        CategorieDTO retour = query.getCategorieByID("inexistant");
        // lorsque le nom de catégorie n'existe pas, on reçoit null
        Assert.assertNull(retour);

        retour = query.getCategorieByID("test");
        // lorsque le nom de catégorie existe, on reçoit la catégorie correspondante
        Assert.assertEquals(categorie.getNomCategorie(), retour.getNomCategorie());
    }
}
