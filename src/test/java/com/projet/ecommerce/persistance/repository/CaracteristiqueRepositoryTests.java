package com.projet.ecommerce.persistance.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
}
