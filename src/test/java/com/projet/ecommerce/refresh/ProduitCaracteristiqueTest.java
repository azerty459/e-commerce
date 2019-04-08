/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projet.ecommerce.refresh;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.business.dto.transformer.ProduitCaracteristiqueTransformer;
import com.projet.ecommerce.business.impl.ProduitBusiness;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.ProduitCaracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import java.util.Collection;
import java.util.List;
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
        
        //Ajoute une caracteristique
        TypedQuery<Integer> tqc = entityManager.createQuery("Select max(c.idCaracteristique) From Caracteristique c", Integer.class);
        int lastid = tqc.getSingleResult();
        Caracteristique carac = new Caracteristique();
        carac.setIdCaracteristique(lastid + 1);
        carac.setLibelle("Bonjour");
        produitBusiness.addCaracteristique(p.getReferenceProduit(), CaracteristiqueTransformer.entityToDto(carac), "Test");
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
    public void testAddBadProduit() {
        CaracteristiqueDTO carac = CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.findById(1).get());
        produitBusiness.addCaracteristique("Ceci n'est pas une reference", carac, "Test");
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
    
    @Test
    public void testDelete() {
        //Recup un produit
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p Inner join p.caracteristiques pc", Produit.class);
        Produit p = tqp.getResultList().get(0);
        int before = p.getCaracterisitiques().size();
        
        //Supprime la caracteristique
        Caracteristique carac = p.getCaracterisitiques().get(0).getCaracteristique();
        ProduitDTO pdto = produitBusiness.deleteCaracterisitque(p.getReferenceProduit(), CaracteristiqueTransformer.entityToDto(carac));
        Assert.assertEquals(before - 1, pdto.getCaracteristiques().size());
        boolean have = false;
        for(ProduitCaracteristique pc : p.getCaracterisitiques()) {
            have = have || (pc.getValeur().equals(pc.getValeur()) && pc.getCaracteristique().getLibelle().equals(carac.getLibelle()));
        }
        Assert.assertFalse(have);
        p = produitRepository.findById(p.getReferenceProduit()).get();
        Assert.assertEquals(0, p.getCaracterisitiques().size());
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testDeleteNullProduit() {
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p Inner join p.caracteristiques pc", Produit.class);
        Produit p = tqp.getResultList().get(0);
        Caracteristique carac = p.getCaracterisitiques().get(0).getCaracteristique();
        produitBusiness.deleteCaracterisitque(null, CaracteristiqueTransformer.entityToDto(carac));
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testDeleteEmptyProduit() {
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p Inner join p.caracteristiques pc", Produit.class);
        Produit p = tqp.getResultList().get(0);
        Caracteristique carac = p.getCaracterisitiques().get(0).getCaracteristique();
        produitBusiness.deleteCaracterisitque("\t", CaracteristiqueTransformer.entityToDto(carac));
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testDeleteBadProduit() {
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p Inner join p.caracteristiques pc", Produit.class);
        Produit p = tqp.getResultList().get(0);
        Caracteristique carac = p.getCaracterisitiques().get(0).getCaracteristique();
        produitBusiness.deleteCaracterisitque("Ceci n'est pas une reference", CaracteristiqueTransformer.entityToDto(carac));
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testDeleteNullCaracteristique() {
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p Inner join p.caracteristiques pc", Produit.class);
        Produit p = tqp.getResultList().get(0);
        produitBusiness.deleteCaracterisitque(p.getReferenceProduit(), null);
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testDeleteBadCaracteristique() {
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p Inner join p.caracteristiques pc", Produit.class);
        Produit p = tqp.getResultList().get(0);
        TypedQuery<Integer> tqc = entityManager.createQuery("Select max(c.idCaracteristique) From Caracteristique c", Integer.class);
        int lastid = tqc.getSingleResult();
        Caracteristique carac = new Caracteristique();
        carac.setIdCaracteristique(lastid + 1);
        carac.setLibelle("Bonjour");
        produitBusiness.deleteCaracterisitque(p.getReferenceProduit(), CaracteristiqueTransformer.entityToDto(carac));
    }
    
    @Test
    public void testGetAll() {
        TypedQuery<Produit> tqp = entityManager.createQuery("Select p From Produit p Inner join p.caracteristiques pc", Produit.class);
        Produit p = tqp.getResultList().get(0);
        List<ProduitCaracteristiqueDTO> pdto = produitBusiness.getAllCaracteristiques(p.getReferenceProduit());
        Assert.assertEquals(p.getCaracterisitiques().size(), pdto.size());
        for(int i = 0; i < pdto.size(); i++) {
            ProduitCaracteristique pc = p.getCaracterisitiques().get(i);
            ProduitCaracteristiqueDTO pcdto = pdto.get(i);
            Assert.assertEquals(pc.getValeur(), pcdto.getValeur());
            Assert.assertEquals(pc.getCaracteristique().getLibelle(), pcdto.getCaracteristique().getLibelle());
        }
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testGetAllNullProduit() {
        produitBusiness.getAllCaracteristiques(null);
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testGetAllEmptyProduit() {
        produitBusiness.getAllCaracteristiques("\t");
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testGetAllBadProduit() {
        produitBusiness.getAllCaracteristiques("Ceci n'est pas une reference");
    }
    
}
