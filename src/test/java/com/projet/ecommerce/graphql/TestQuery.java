package com.projet.ecommerce.graphql;

import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.entrypoint.graphQL.Query;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

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

//    @Test
//    public void getAllProduit() {
//        List<ProduitDTO> produitsDTO = new ArrayList<>();
//        Mockito.when(produitBusiness.getAll()).thenReturn(produitsDTO);
//        List<ProduitDTO> retour = query.produit();
//
//        // on s'assure que la première fois, la liste retournée est vide
//        Assert.assertNotNull(retour);
//        Assert.assertEquals(retour.size(), 0);
//
//        produitsDTO.add(new ProduitDTO());
//        retour = query.produit();
//        // on s'assure que la liste comporte bien un élément
//        Assert.assertEquals(retour.size(), 1);
//    }
//
//    @Test
//    public void getProduitByRef(){
//        ProduitDTO produit = new ProduitDTO();
//        produit.setRef("A4224");
//        Mockito.when(produitBusiness.getByRef("A2442")).thenReturn(null);
//        Mockito.when(produitBusiness.getByRef("A4224")).thenReturn(produit);
//
//        ProduitDTO retour = query.getProduitByRef("A2442");
//        // lorsque la référence produit n'existe pas, on reçoit null
//        Assert.assertNull(retour);
//
//        retour = query.getProduitByRef("A4224");
//        // lorsque la référence produit existe, on reçoit le produit correspondant
//        Assert.assertEquals(produit.getRef(), retour.getRef());
//    }
//
//    @Test
//    public void getByCategorie(){
//        List<ProduitDTO> produitsDTO = new ArrayList<>();
//        produitsDTO.add(new ProduitDTO());
//        Mockito.when(produitBusiness.getByCategorie("inexistant")).thenReturn(null);
//        Mockito.when(produitBusiness.getByCategorie("livre")).thenReturn(produitsDTO);
//
//        List<ProduitDTO> retour = query.getProduitByCategorie("inexistant");
//        // quand il n'y a aucun produit pour cette catégorie, on reçoit null
//        Assert.assertNull(retour);
//
//        retour = query.getProduitByCategorie("livre");
//        // quand il y a des produits pour cette catégorie, on les reçoit
//        Assert.assertEquals(produitsDTO, retour);
//    }
//
//    @Test
//    public void getAllCategorie(){
//        List<CategorieDTO> categories = new ArrayList<>();
//        Mockito.when(categorieBusiness.getAll()).thenReturn(categories);
//        List<CategorieDTO> retour = query.getAllCategorie();
//
//        // on s'assure que la première fois, la liste retournée est vide
//        Assert.assertNotNull(retour);
//        Assert.assertEquals(retour.size(), 0);
//
//        categories.add(new CategorieDTO());
//        retour = query.getAllCategorie();
//        // on s'assure que la liste comporte bien un élément
//        Assert.assertEquals(retour.size(), 1);
//    }
//
//    @Test
//    public void getCategorieByNom(){
//        CategorieDTO categorie = new CategorieDTO();
//        categorie.setNom("test");
//        Mockito.when(categorieBusiness.getByNom("inexistant")).thenReturn(null);
//        Mockito.when(categorieBusiness.getByNom("test")).thenReturn(categorie);
//
//        CategorieDTO retour = query.getCategorieByNom("inexistant");
//        // lorsque le nom de catégorie n'existe pas, on reçoit null
//        Assert.assertNull(retour);
//
//        retour = query.getCategorieByNom("test");
//        // lorsque le nom de catégorie existe, on reçoit la catégorie correspondante
//        Assert.assertEquals(categorie.getNom(), retour.getNom());
//    }
}
