package com.projet.ecommerce.persistance.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CaracteristiqueRepositoryTests {

	@Autowired
    private CaracteristiqueRepository caracteristiqueRepository;

    @Autowired
    private ProduitRepository produitRepository;
    
    private static final Produit PRODUIT = new Produit();
    private static final Produit PRODUIT_CARAC = new Produit();
    private static final Caracteristique CARAC = new Caracteristique();
    
    static {
    	PRODUIT.setReferenceProduit("PROD");
    	PRODUIT.setPrixHT(10.875f);
    	PRODUIT.setDescription("joli truc");
    	PRODUIT.setNom("produit test");
    	
    	CARAC.setTypeCaracteristique(TypeCaracteristiqueDTO.EDITEUR);
    	CARAC.setValeur("Flammarion");
    	
    	PRODUIT_CARAC.setReferenceProduit("PROD_CARAC");
    	PRODUIT_CARAC.setPrixHT(10.875f);
    	PRODUIT_CARAC.setDescription("joli truc");
    	PRODUIT_CARAC.setNom("produit_carac test");
    	PRODUIT_CARAC.addCaracteristique(CARAC);
    }
    
    @Test
    public void findByProduit_ReferenceProduit() {
    	Produit p = new Produit();
        p.setReferenceProduit("CRA PRODUIT");
        p.setNom("CRA");
        p.setDescription("Test caracteristique");
        p.setPrixHT(100);
        produitRepository.save(p);
        
        Caracteristique carac = new Caracteristique();
        carac.setValeur("Français");
        carac.setTypeCaracteristique(TypeCaracteristiqueDTO.LANGUE);
        carac.setProduit(p);
        caracteristiqueRepository.save(carac);
        
        List<Caracteristique> caracsDuProduit = new ArrayList<>(caracteristiqueRepository.findByProduit_ReferenceProduit(p.getReferenceProduit()));
        Assert.assertNotNull(caracsDuProduit);
        Assert.assertFalse(caracsDuProduit.isEmpty());
        Assert.assertEquals(caracsDuProduit.size(),1);
        Assert.assertNotNull(caracsDuProduit.get(0));
        Caracteristique caracLangue = caracsDuProduit.get(0);
        Assert.assertEquals(caracLangue.getTypeCaracteristique(), carac.getTypeCaracteristique());
        Assert.assertEquals(caracLangue.getValeur(), carac.getValeur());
        Assert.assertNotNull(caracLangue.getProduit());
        Assert.assertEquals(caracLangue.getProduit().getReferenceProduit(), carac.getProduit().getReferenceProduit());
            
    }
    
    
    @Test
    public void addCaracteristiqueToProduit() {
    	
    	Produit p = produitRepository.save(PRODUIT);
    	
    	Caracteristique carac = CARAC;
    	
    	p.addCaracteristique(carac);
    	
    	Produit pRetour = produitRepository.save(p);
    	
    	//on verifie qu'on a bien la caracteristique une fois la sauvegarde effectuée
    	Assert.assertNotNull(pRetour);
    	List<Caracteristique> retourCaract = pRetour.getCaracteristiques();
    	Assert.assertNotNull(retourCaract);
    	Assert.assertFalse(retourCaract.isEmpty());
    	Assert.assertEquals(retourCaract.get(0).getProduit().getReferenceProduit(), p.getReferenceProduit());
    	
    	//on verifie qu'il s'agit de la mm caracteristique (meme id)
    	List<Caracteristique> caracParProduit = new ArrayList<>(caracteristiqueRepository.findByProduit_ReferenceProduit(p.getReferenceProduit()));
    	Assert.assertNotNull(caracParProduit);
    	Assert.assertFalse(caracParProduit.isEmpty());
    	Assert.assertEquals(caracParProduit.get(0).getId(),retourCaract.get(0).getId());
    	
    	
    }
    
    @Test
    public void removeCaracteristiqueFromProduit() {
    	
    	Produit p = produitRepository.save(PRODUIT_CARAC);    	
    	Assert.assertEquals(p.getCaracteristiques().size(), 1);
    	
    	Caracteristique carac = p.getCaracteristiques().get(0);   	
    	p.removeCaracteristique(carac);    	
    	Assert.assertEquals(p.getCaracteristiques().size(), 0);
    	
    	Produit pRetour = produitRepository.save(p);
    	Assert.assertEquals(pRetour.getCaracteristiques().size(), 0);
    		
    	    	
    	List<Caracteristique> caracParProduit = new ArrayList<>(caracteristiqueRepository.findByProduit_ReferenceProduit(p.getReferenceProduit()));
    	Assert.assertNotNull(caracParProduit);
    	Assert.assertTrue(caracParProduit.isEmpty());
    	   	
    	
    }
    
}
