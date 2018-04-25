package com.projet.ecommerce.graphql;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.entrypoint.graphQL.Mutation;
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

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestMutation {
    @Mock
    private ProduitBusiness produitBusiness;

    @Mock
    private CategorieBusiness categorieBusiness;

    @InjectMocks
    private Mutation mutation;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addProduit(){
        Mockito.when(produitBusiness.add(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyDouble())).thenReturn(new ProduitDTO());
        ProduitDTO retour = mutation.addProduit("Test", "Test", "Test" , 4.7);
        Assert.assertNotNull(retour);
        Assert.assertThat(retour, instanceOf(ProduitDTO.class));
    }

    @Test
    public void updateProduit(){
        Mockito.when(produitBusiness.update(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyFloat())).thenReturn(new ProduitDTO());
        ProduitDTO retour = mutation.updateProduit("A4224", "Livre", "Un livre", 4.5f);

        // lorsque la mise à jour a fonctionné, on reçoit le produit modifié
        Assert.assertNotNull(retour);
        Assert.assertThat(retour, instanceOf(ProduitDTO.class));
    }

    @Test
    public void deleteProduit(){
        Mockito.when(produitBusiness.delete("inexistant")).thenReturn(false);
        Mockito.when(produitBusiness.delete("A4224")).thenReturn(true);

        Assert.assertFalse(mutation.deleteProduit("inexistant"));
        Assert.assertTrue((mutation.deleteProduit("A4224")));
    }

    @Test
    public void addCategorieParent(){
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setNom("Transpole");
        categorieDTO.setSousCategories(new ArrayList<>());

        Mockito.when(categorieBusiness.addParent(Mockito.anyString())).thenReturn(categorieDTO);
        CategorieDTO retour = mutation.addCategorieParent("Transpole");

        Assert.assertNotNull(retour);
        Assert.assertEquals(categorieDTO.getNom(), retour.getNom());
        Assert.assertEquals(categorieDTO.getSousCategories().size(), 0);
    }

    @Test
    public void addCategorieEnfant(){
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setNom("Transpole");
        categorieDTO.setSousCategories(new ArrayList<>());

        Mockito.when(categorieBusiness.addEnfant(Mockito.anyString(), Mockito.anyString())).thenReturn(categorieDTO);
        CategorieDTO retour = mutation.addCategorieEnfant("Test", "Test");
        Assert.assertNotNull(retour);
    }

    @Test
    public void addCategorieEnfant_ParentNoFound(){
        Mockito.when(categorieBusiness.addEnfant(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        CategorieDTO retour = mutation.addCategorieEnfant("Test", "Test");
        Assert.assertNull(retour);
    }

    @Test
    public void deleteCategorie(){
        Mockito.when(categorieBusiness.delete("inexistant")).thenReturn(false);
        Mockito.when(categorieBusiness.delete("A4224")).thenReturn(true);

        Assert.assertFalse(mutation.deleteCategorie("inexistant"));
        Assert.assertTrue((mutation.deleteCategorie("A4224")));
    }
}
