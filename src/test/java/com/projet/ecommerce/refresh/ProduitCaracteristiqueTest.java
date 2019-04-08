/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.ecommerce.refresh;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.ProduitCaracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProduitCaracteristiqueTest {
    
    @Autowired
    private ProduitBusiness produitBusiness;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Test
    public void testAdd() {
        //Recup un produit
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p", Produit.class);
        Produit p = tqp.getResultList().get(0);
        int before = p.getCaracterisitiques().size();
        
        //Ajoute une caracteristique
        CaracteristiqueDTO carac = CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.findById(1).get());
        ProduitDTO pdto = produitBusiness.addCaracteristique(p.getReferenceProduit(), carac, "Test");

        //Verifie que l'ajout est bon
        Assert.assertEquals(before + 1, pdto.getCaracteristiques().size());
        Assert.assertEquals(pdto.getCaracteristiques().size(), p.getCaracterisitiques().size());
        boolean have = false;
        for(ProduitCaracteristique pc : p.getCaracterisitiques()) {
            have = have || ("Test".equals(pc.getValeur()) && pc.getCaracteristique().getLibelle().equals(carac.getLibelle()));
        }
        Assert.assertTrue(have);
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testAddNullCaracteristique() {
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p", Produit.class);
        Produit p = tqp.getResultList().get(0);
        int before = p.getCaracterisitiques().size();    
        ProduitDTO pdto = produitBusiness.addCaracteristique(p.getReferenceProduit(), null, "Test");
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testAddBadCaracteristique() {
        //Recup un produit
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p", Produit.class);
        Produit p = tqp.getResultList().get(0);
        int before = p.getCaracterisitiques().size();
        
        //Ajoute une caracteristique
        TypedQuery<Integer> tqc = entityManager.createQuery("Select max(c.idCaracteristique) From Caracteristique c", Integer.class);
        int lastid = tqc.getSingleResult();
        Caracteristique carac = new Caracteristique();
        carac.setIdCaracteristique(lastid + 1);
        carac.setLibelle("Bonjour");
        ProduitDTO pdto = produitBusiness.addCaracteristique(p.getReferenceProduit(), CaracteristiqueTransformer.entityToDto(carac), "Test");
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testAddNullProduit() {
        CaracteristiqueDTO carac = CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.findById(1).get());
        produitBusiness.addCaracteristique(null, carac, "Test");
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testAddEmptyProduit() {
        CaracteristiqueDTO carac = CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.findById(1).get());
        produitBusiness.addCaracteristique("\t", carac, "Test");
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testAddNullValeur() {
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p", Produit.class);
        Produit p = tqp.getResultList().get(0);
        CaracteristiqueDTO carac = CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.findById(1).get());
        produitBusiness.addCaracteristique(p.getReferenceProduit(), carac, null);
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testAddEmptyValeur() {
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p", Produit.class);
        Produit p = tqp.getResultList().get(0);
        CaracteristiqueDTO carac = CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.findById(1).get());
        produitBusiness.addCaracteristique(p.getReferenceProduit(), carac, "\t");
    }
    
}
