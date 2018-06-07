package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.business.dto.PaginationDTO;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
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
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PaginationBusinessTests {

    @Mock
    private IProduitBusiness produitBusiness;

    @Mock
    private ICategorieBusiness categorieBusiness;

    @Mock
    private Page page;

    @InjectMocks
    private PaginationBusiness paginationBusiness;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPaginationEmpty() {
        PaginationDTO paginationDTO = paginationBusiness.getPagination("toto", 1, 5);
        Assert.assertNull(paginationDTO);
    }

    @Test
    public void getPaginationByTypeProduit() {
        ArrayList<Produit> produitArrayList = new ArrayList<>();
        Produit produit = new Produit();
        produit.setCaracteristiques(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
        produit.setReferenceProduit("A01A47");
        produit.setPrixHT(4.7f);
        produit.setNom("Un test");
        produit.setDescription("");
        produitArrayList.add(produit);

        Mockito.when(page.getTotalPages()).thenReturn(5);
        Mockito.when(page.getTotalElements()).thenReturn(Long.valueOf(25));
        Mockito.when(page.getContent()).thenReturn(produitArrayList);
        Mockito.when(produitBusiness.getPage(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);

        PaginationDTO paginationDTO = paginationBusiness.getPagination("produit", 2, 5);
        Mockito.verify(produitBusiness, Mockito.times(1)).getPage(Mockito.anyInt(), Mockito.anyInt());
        Assert.assertNotNull(paginationDTO);

        Assert.assertEquals(25, paginationDTO.getTotal());
        Assert.assertEquals(2, paginationDTO.getPageActuelle());
        Assert.assertEquals(1, paginationDTO.getPageMin());
        Assert.assertEquals(5, paginationDTO.getPageMax());
        Assert.assertEquals(1, paginationDTO.getProduits().size());
        Assert.assertEquals(new ArrayList<Categorie>(), paginationDTO.getCategories());

        // Permets de tester "if (pageActuelle > pageProduit.getTotalPages()) pour la pagination de produit"
        paginationDTO = paginationBusiness.getPagination("produit", Integer.MAX_VALUE, 5);
        Mockito.verify(produitBusiness, Mockito.times(3)).getPage(Mockito.anyInt(), Mockito.anyInt());
        Assert.assertNotNull(paginationDTO);
        Assert.assertEquals(page.getTotalPages(), paginationDTO.getPageActuelle());
    }

    @Test
    public void getPaginationByTypeCategorie() {
        ArrayList<Categorie> categorieArrayList = new ArrayList<>();
        Categorie categorie = new Categorie();
        categorie.setProduits(new ArrayList<>());
        categorie.setNomCategorie("Livre");
        categorie.setLevel(1);
        categorie.setBorneDroit(1);
        categorie.setBorneGauche(8);
        categorieArrayList.add(categorie);

        Mockito.when(page.getTotalPages()).thenReturn(5);
        Mockito.when(page.getTotalElements()).thenReturn(Long.valueOf(25));
        Mockito.when(page.getContent()).thenReturn(categorieArrayList);
        Mockito.when(categorieBusiness.getPage(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);

        PaginationDTO paginationDTO = paginationBusiness.getPagination("categorie", 2, 5);
        Mockito.verify(categorieBusiness, Mockito.times(1)).getPage(Mockito.anyInt(), Mockito.anyInt());
        Assert.assertNotNull(paginationDTO);

        Assert.assertEquals(25, paginationDTO.getTotal());
        Assert.assertEquals(2, paginationDTO.getPageActuelle());
        Assert.assertEquals(1, paginationDTO.getPageMin());
        Assert.assertEquals(5, paginationDTO.getPageMax());
        Assert.assertEquals(1, paginationDTO.getCategories().size());
        Assert.assertEquals(new ArrayList<Produit>(), paginationDTO.getProduits());

        // Permets de tester "if (pageActuelle > pageCategorie.getTotalPages()) pour la pagination de categorie"
        paginationDTO = paginationBusiness.getPagination("categorie", Integer.MAX_VALUE, 50);
        Mockito.verify(categorieBusiness, Mockito.times(3)).getPage(Mockito.anyInt(), Mockito.anyInt());
        Assert.assertNotNull(paginationDTO);
        Assert.assertEquals(page.getTotalPages(), paginationDTO.getPageActuelle());
    }
}