package com.projet.ecommerce.refresh;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.ProduitCaracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        TypedQuery<Caracteristique> tqc = em.createQuery("Select c From Caracteristique c", Caracteristique.class);
        Caracteristique carac = tqc.getResultList().get(0);
        System.out.println(carac);
        System.out.println(carac.getProduits().size());
        Produit p = carac.getProduits().get(0).getProduit();
        
        Caracteristique c = new Caracteristique();
        c.setLabel("Loul");
        caracteristiqueRepository.save(c);
        
        ProduitCaracteristique pc = new ProduitCaracteristique(p, c);
        p.getCaracterisitiques().add(pc);
        c.getProduits().add(pc);
        produitRepository.save(p);
        caracteristiqueRepository.save(c);
        
        tqc = em.createQuery("Select c From Caracteristique c", Caracteristique.class);
        System.out.println(tqc.getResultList().size());
        
        TypedQuery<Produit> tqp = em.createQuery("Select p From Produit p Where p.referenceProduit=:ref", Produit.class);
        tqp.setParameter("ref", p.getReferenceProduit());
        p = tqp.getSingleResult();
        System.out.println(p.getCaracterisitiques().size());
        for(ProduitCaracteristique var : p.getCaracterisitiques()) {
            System.out.println(var.getCaracteristique());
        }
    }
    
}
