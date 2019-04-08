package com.projet.ecommerce.refresh;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.business.impl.CaracteristiqueBusiness;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CaracteristiqueBusinessTest {
    
    @Autowired
    private CaracteristiqueBusiness caracteristiqueBusiness;
    
    @Autowired
    private EntityManager entityManager;
    
    @Test
    public void testSelect() {
        TypedQuery<Caracteristique> tqc = entityManager.createQuery("Select c From Caracteristique c", Caracteristique.class);
        Collection<Caracteristique> expected = tqc.getResultList();
        Collection<Caracteristique> listCarac = CaracteristiqueTransformer.dtoToEntity(caracteristiqueBusiness.getAll());
        Assert.assertEquals(expected, listCarac);
    }
    
    @Test
    public void testAdd() {
        Collection<CaracteristiqueDTO> before = caracteristiqueBusiness.getAll();
        CaracteristiqueDTO cdto = caracteristiqueBusiness.add("Test");
        Collection<CaracteristiqueDTO> after = caracteristiqueBusiness.getAll();
        Assert.assertEquals(before.size() + 1, after.size());
        Assert.assertTrue("Test".equals(cdto.getLibelle()));
        TypedQuery<Caracteristique> tqc = entityManager.createQuery("Select c From Caracteristique c Where c.idCaracteristique=:id", Caracteristique.class);
        tqc.setParameter("id", cdto.getId());
        Caracteristique c = tqc.getSingleResult();
        Assert.assertTrue("Test".equals(c.getLibelle()));
        Assert.assertTrue(cdto.getId() == c.getIdCaracteristique());
        Assert.assertEquals(c, CaracteristiqueTransformer.dtoToEntity(cdto));  
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testAddNull() {
        CaracteristiqueDTO cdto = caracteristiqueBusiness.add(null);
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testAddEmpty() {
        CaracteristiqueDTO cdto = caracteristiqueBusiness.add("\t");
    }
    
    @Test
    public void testDelete() {
        Collection<CaracteristiqueDTO> before = caracteristiqueBusiness.getAll();
        Assert.assertTrue(caracteristiqueBusiness.delete(1));
        Collection<CaracteristiqueDTO> after = caracteristiqueBusiness.getAll();
        Assert.assertEquals(before.size() - 1, after.size());
        TypedQuery<Caracteristique> tqc = entityManager.createQuery("Select c From Caracteristique c Where c.idCaracteristique=:id", Caracteristique.class);
        tqc.setParameter("id", 1);
        Assert.assertEquals(0, tqc.getResultList().size());
    }
    
    @Test
    public void testDeleteNonExistent() {
        Assert.assertFalse(caracteristiqueBusiness.delete(0));
    }
    
    @Test
    public void testUpdate() {
        TypedQuery<Caracteristique> tqc = entityManager.createQuery("Select c From Caracteristique c", Caracteristique.class);
        Caracteristique c = tqc.getResultList().get(0);
        String before = c.getLibelle();
        c.setLibelle("Test");
        CaracteristiqueDTO cdto = caracteristiqueBusiness.update(CaracteristiqueTransformer.entityToDto(c));
        tqc = entityManager.createQuery("Select c From Caracteristique c Where c.idCaracteristique=:id", Caracteristique.class);
        tqc.setParameter("id", c.getIdCaracteristique());
        c = tqc.getSingleResult();
        Assert.assertFalse("Test".equals(before));
        Assert.assertEquals(c, CaracteristiqueTransformer.dtoToEntity(cdto));
        tqc = entityManager.createQuery("Select c From Caracteristique c Where c.libelle=:lib", Caracteristique.class);
        tqc.setParameter("lib", before);
        Assert.assertEquals(0, tqc.getResultList().size());
        tqc = entityManager.createQuery("Select c From Caracteristique c Where c.libelle=:lib", Caracteristique.class);
        tqc.setParameter("lib", "Test");
        Assert.assertEquals(1, tqc.getResultList().size());
    }
    
    @Test
    public void testUpdateNull() {
        Assert.assertNull(caracteristiqueBusiness.update(null));
    }
    
    @Test(expected = GraphQLCustomException.class)
    public void testUpdateNew() {
        TypedQuery<Integer> tqc = entityManager.createQuery("Select max(c.idCaracteristique) From Caracteristique c", Integer.class);
        int lastid = tqc.getSingleResult();
        Caracteristique c = new Caracteristique();
        c.setIdCaracteristique(lastid + 1);
        c.setLibelle("Bonjour");
        caracteristiqueBusiness.update(CaracteristiqueTransformer.entityToDto(c));
    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void testUnique() {
        caracteristiqueBusiness.add("Test");
        caracteristiqueBusiness.add("Test");
    }
    
}
