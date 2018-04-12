package com.projet.ecommerce.graphql;

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

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestMutation {
   /* @Mock
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
        ProduitDTO produit = new ProduitDTO();
        produit.setReferenceProduit("A4224");
        Mockito.when(produitBusiness.addProduit(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(null);
        ProduitDTO retour = mutation.addProduit("A4224", "Livre", "Un livre", 4.5);

        // on s'assure que la détection des paramètres null fonctionne
        Assert.assertEquals(retour, null);

        // lorsque l'ajout a fonctionné, on reçoit le produit ajouté
        retour = mutation.addProduit(produit);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getReferenceProduit());
    }

    @Test
    public void updateProduit(){
        ProduitDTO produit = new ProduitDTO();
        produit.setReferenceProduit("A4224");
        Mockito.when(produitBusiness.updateProduit(null)).thenReturn(null);
        Mockito.when(produitBusiness.updateProduit(produit)).thenReturn(produit);
        ProduitDTO retour = mutation.updateProduit(null);

        // on s'assure que la détection des paramètres null fonctionne
        Assert.assertEquals(retour, null);

        // lorsque la mise à jour a fonctionné, on reçoit le produit modifié
        retour = mutation.updateProduit(produit);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getReferenceProduit());
    }

    @Test
    public void deleteProduit(){
        Mockito.when(produitBusiness.deleteProduit("inexistant")).thenReturn(false);
        Mockito.when(produitBusiness.deleteProduit("A4224")).thenReturn(true);

        Assert.assertFalse(mutation.deleteProduit("inexistant"));
        Assert.assertTrue((mutation.deleteProduit("A4224")));
    }*/
}
