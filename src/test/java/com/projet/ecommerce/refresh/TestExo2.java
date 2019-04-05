package com.projet.ecommerce.refresh;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.ProduitCaracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TestExo2 {
    
    @Autowired
    private CaracteristiqueRepository caracteristiqueRepository;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private EntityManager em;
    
    @Test
    public void testInserCarac() {        
        /*Caracteristique c = new Caracteristique();
        c.setLabel("Test");
        c.setProduits(null);
        System.out.println(caracteristiqueRepository.count());
        caracteristiqueRepository.save(c);
        System.out.println(caracteristiqueRepository.count());
        Query q = em.createQuery("Select c From Caracteristique c");
        c = (Caracteristique) q.getSingleResult();
        System.out.println(c);*/
        
        /*TypedQuery<Produit> tq = em.createQuery("Select p From Produit p Where p.referenceProduit='A01A05'", Produit.class);
        Produit p = tq.getResultList().get(0);
        int size = p.getCaracterisitiques().size();
        System.out.println(size);*/
        
        /*
        ProduitCaracteristique pc = new ProduitCaracteristique();
        pc.setValeur("Refresh");
        pc.setProduit(p);
        pc.setCaracteristique(c);
        produitCaracteristiqueRepository.save(pc);
        
        List<ProduitCaracteristique> lc = new ArrayList<>();
        lc.add(pc);
        p.setCaracterisitiques(lc);
        produitRepository.save(p);
        //*/
        
        TypedQuery<Caracteristique> tqc = em.createQuery("Select c From Caracteristique c", Caracteristique.class);
        Caracteristique c = tqc.getResultList().get(0);
        System.out.println(c);
        System.out.println(c.getProduits().size());
        System.out.println(c.getProduits().get(0).getProduit());
    }
    
}
