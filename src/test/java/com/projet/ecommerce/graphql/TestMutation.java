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
        Mockito.when(produitBusiness.add(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyFloat())).thenReturn(new ProduitDTO());
        ProduitDTO retour = mutation.addProduit("A4224", "Livre", "Un livre", 4.5f);

        // lorsque l'ajout a fonctionné, on reçoit le produit ajouté
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

    /*@Test
    public void addCategorie(){
        Mockito.when(categorieBusiness.addCategorie(Mockito.anyString(), Mockito.anyString())).thenReturn(new CategorieDTO());
        ProduitDTO retour = mutation.addProduit("A4224", "Livre", "Un livre", 4.5f);

        // lorsque l'ajout a fonctionné, on reçoit le produit ajouté
        Assert.assertNotNull(retour);
        Assert.assertThat(retour, instanceOf(ProduitDTO.class));
    }*/

    @Test
    public void deleteCategorie(){
        Mockito.when(categorieBusiness.delete("inexistant")).thenReturn(false);
        Mockito.when(categorieBusiness.delete("A4224")).thenReturn(true);

        Assert.assertFalse(mutation.deleteCategorie("inexistant"));
        Assert.assertTrue((mutation.deleteCategorie("A4224")));
    }
}
