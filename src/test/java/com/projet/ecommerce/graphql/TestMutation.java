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
    public void add(){
        ProduitDTO produit = new ProduitDTO();
        produit.setReferenceProduit("A4224");
        Mockito.when(produitBusiness.add(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(null);
        ProduitDTO retour = mutation.add("A4224", "Livre", "Un livre", 4.5);

        // on s'assure que la détection des paramètres null fonctionne
        Assert.assertEquals(retour, null);

        // lorsque l'ajout a fonctionné, on reçoit le produit ajouté
        retour = mutation.add(produit);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getReferenceProduit());
    }

    @Test
    public void update(){
        ProduitDTO produit = new ProduitDTO();
        produit.setReferenceProduit("A4224");
        Mockito.when(produitBusiness.update(null)).thenReturn(null);
        Mockito.when(produitBusiness.update(produit)).thenReturn(produit);
        ProduitDTO retour = mutation.update(null);

        // on s'assure que la détection des paramètres null fonctionne
        Assert.assertEquals(retour, null);

        // lorsque la mise à jour a fonctionné, on reçoit le produit modifié
        retour = mutation.update(produit);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getReferenceProduit());
    }

    @Test
    public void delete(){
        Mockito.when(produitBusiness.delete("inexistant")).thenReturn(false);
        Mockito.when(produitBusiness.delete("A4224")).thenReturn(true);

        Assert.assertFalse(mutation.delete("inexistant"));
        Assert.assertTrue((mutation.delete("A4224")));
    }*/
}
