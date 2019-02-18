package com.projet.ecommerce.persistance.repository;

import java.util.ArrayList;
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
    	Caracteristique carac = getDummyCaracteristique(TypeCaracteristiqueDTO.EDITEUR);
    	
    	
    	Produit p = getDummyProduit("123456");
        p.addCaracteristique(carac);
        produitRepository.save(p);
        
    	
        List<Caracteristique> caracsDuProduit = new ArrayList<>(caracteristiqueRepository.findByProduit_ReferenceProduit(p.getReferenceProduit()));
        Assert.assertNotNull(caracsDuProduit);
        Assert.assertFalse(caracsDuProduit.isEmpty());
        Assert.assertEquals(caracsDuProduit.size(),1);
        Assert.assertNotNull(caracsDuProduit.get(0));
        Caracteristique caracEditeur = caracsDuProduit.get(0);
        Assert.assertEquals(caracEditeur.getTypeCaracteristique(), carac.getTypeCaracteristique());
        Assert.assertEquals(caracEditeur.getValeur(), carac.getValeur());
        Assert.assertNotNull(caracEditeur.getProduit());
        Assert.assertEquals(caracEditeur.getProduit().getReferenceProduit(), carac.getProduit().getReferenceProduit());
            
    }
    
    
    @Test
    public void addCaracteristiqueToProduit() {
    	
    	Produit p = produitRepository.save(getDummyProduit("TEST"));
    	
    	Caracteristique carac = getDummyCaracteristique(TypeCaracteristiqueDTO.EDITEUR);
    	
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
    	Assert.assertEquals(caracParProduit.get(0),retourCaract.get(0));
    	
    	
    }
    
    @Test
    public void deleteCaracteristique() {
    	
    	Produit p = produitRepository.save(getDummyProduitWithCaracteristique("123456", TypeCaracteristiqueDTO.EDITEUR));
    	Assert.assertNotNull(p.getCaracteristiques());
    	Assert.assertEquals(p.getCaracteristiques().size(), 1);
    	
    	Caracteristique carac = p.getCaracteristiques().get(0);   	
    	p.removeCaracteristique(carac);  
    	
    	caracteristiqueRepository.delete(carac);    	
    	  	
    	Assert.assertEquals(p.getCaracteristiques().size(), 0);
    	
    	Produit pRetour = produitRepository.findById(p.getReferenceProduit()).get();
    	Assert.assertEquals(pRetour.getCaracteristiques().size(), 0);
    		
    	    	
    	List<Caracteristique> caracParProduit = new ArrayList<>(caracteristiqueRepository.findByProduit_ReferenceProduit(p.getReferenceProduit()));
    	Assert.assertNotNull(caracParProduit);
    	Assert.assertTrue(caracParProduit.isEmpty());
        	
    }
    
    
    @Test
    public void deleteProduitWithCaracteristique() {
    	
    	Produit p = getDummyProduitWithCaracteristique("TEST",TypeCaracteristiqueDTO.EDITEUR);
    	Caracteristique c = getDummyCaracteristique(TypeCaracteristiqueDTO.LANGUE);
    	p.addCaracteristique(c);
    	    	
    	Produit pSaved = produitRepository.save(p); 
    	Assert.assertEquals(pSaved.getReferenceProduit(), p.getReferenceProduit());
    	Assert.assertNotNull(pSaved.getCaracteristiques());
    	Assert.assertEquals(pSaved.getCaracteristiques().size(), 2);
    	
    	produitRepository.delete(pSaved);
    	
    	//on verifie que le produit est supprimé
    	Optional<Produit> pRead = produitRepository.findById(pSaved.getReferenceProduit());  	
    	Assert.assertFalse(pRead.isPresent());
    	
    	//on verifie que les caracteristique sont supprimée
    	List<Caracteristique> caracParProduit = new ArrayList<>(caracteristiqueRepository.findByProduit_ReferenceProduit(pSaved.getReferenceProduit()));
    	Assert.assertNotNull(caracParProduit);
    	Assert.assertTrue(caracParProduit.isEmpty());
    	   	
    	
    }
    
    
    @Test
    public void updateValeurCarateristique() {
    	
    	Produit p = getDummyProduitWithCaracteristique("TEST",TypeCaracteristiqueDTO.EDITEUR);
    	Caracteristique dummy = getDummyCaracteristique(TypeCaracteristiqueDTO.EDITEUR);
    	
    	Produit pSaved = produitRepository.save(p);  
    	Assert.assertEquals(pSaved.getReferenceProduit(), p.getReferenceProduit());
    	Assert.assertNotNull(pSaved.getCaracteristiques());
    	Assert.assertEquals(pSaved.getCaracteristiques().size(), 1);
    	Assert.assertNotNull(pSaved.getCaracteristiques().get(0));
    	Assert.assertEquals(pSaved.getCaracteristiques().get(0).getValeur(), dummy.getValeur());
    	
    	Caracteristique c = pSaved.getCaracteristiques().get(0);
    	c.setValeur("Nouvelle Valeur");
    	
    	produitRepository.save(pSaved);
    	
    	List<Caracteristique> caracParProduit = new ArrayList<>(caracteristiqueRepository.findByProduit_ReferenceProduit(pSaved.getReferenceProduit()));
    	Assert.assertNotNull(caracParProduit);
    	Assert.assertEquals(caracParProduit.size(), 1);
    	Assert.assertNotNull(caracParProduit.get(0));
    	Assert.assertNotEquals(caracParProduit.get(0).getValeur(), dummy.getValeur());
    	Assert.assertEquals(caracParProduit.get(0).getValeur(), c.getValeur());
    	   	
    	
    }
    
    
    private Produit getDummyProduit(String refProduit) {
    	Produit p = new Produit();
    	p.setReferenceProduit(refProduit);
    	p.setPrixHT(10.875f);
    	p.setDescription("DUMMY");
    	p.setNom("produit dummy");
    	p.setCategories(new ArrayList<>());
    	p.setPhotos(new ArrayList<>());
    	p.setAvisClients(new ArrayList<>());
    	
    	return p;
    	
    }
    
    private Caracteristique getDummyCaracteristique(TypeCaracteristiqueDTO type) {
    	Caracteristique c = new Caracteristique();
    	c.setTypeCaracteristique(type);
    	c.setValeur("valeur dummy");
    	   	
    	return c;
    }
    
    private Produit getDummyProduitWithCaracteristique(String refProduit, TypeCaracteristiqueDTO typeCarac) {
    	Produit p = getDummyProduit(refProduit);
    	Caracteristique c = getDummyCaracteristique(typeCarac);
    	p.addCaracteristique(c);
    	return p;
    }
    
}
