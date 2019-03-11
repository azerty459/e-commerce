package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TypeCaracteristiqueRepositoryTests {

    private static final TypeCaracteristique TEMP_INSERT = new TypeCaracteristique();
    private static final TypeCaracteristique TEMP_DELETE = new TypeCaracteristique();
    private static final TypeCaracteristique TEMP_UPDATE = new TypeCaracteristique();
    private static final TypeCaracteristique TEMP_GET = new TypeCaracteristique();

    static {
        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        TEMP_INSERT.setIdTypeCaracteristique(5);
        TEMP_INSERT.setType("Brochépp");

        TEMP_DELETE.setIdTypeCaracteristique(6);
        TEMP_DELETE.setType("Editeurpp");

        TEMP_UPDATE.setIdTypeCaracteristique(7);
        TEMP_UPDATE.setType("Languepp");

        TEMP_GET.setIdTypeCaracteristique(8);
        TEMP_GET.setType("Dimensions du produitpp");
        
    }

    @Autowired
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;

    @Test
    public void insertTypeCaracteristique() {
        Assert.assertNotNull(typeCaracteristiqueRepository.save(TEMP_INSERT));
        TypeCaracteristique temp = typeCaracteristiqueRepository.findById(TEMP_INSERT.getIdTypeCaracteristique()).orElse(null);
        Assert.assertNotNull(temp);
    }

    @Test
    public void getTypeCaracteristique() {
    	typeCaracteristiqueRepository.deleteAll();
        Collection<TypeCaracteristique> typeCaracteristiqueCollection = (Collection<TypeCaracteristique>) typeCaracteristiqueRepository.findAll();
        Assert.assertEquals(0, typeCaracteristiqueCollection.size());

        typeCaracteristiqueRepository.save(TEMP_INSERT);
        typeCaracteristiqueCollection = (Collection<TypeCaracteristique>) typeCaracteristiqueRepository.findAll();
        Assert.assertEquals(1, typeCaracteristiqueCollection.size());
    }

    @Test
    public void getTypeCaracteristiqueByID() {
        
        Assert.assertNotNull(typeCaracteristiqueRepository.save(TEMP_GET));
        TypeCaracteristique temp = typeCaracteristiqueRepository.findById(TEMP_GET.getIdTypeCaracteristique()).orElse(null);
        Assert.assertNotNull("TypeCaracteristique ne peut pas être null", temp);
        Assert.assertEquals(TEMP_GET.getIdTypeCaracteristique(), temp.getIdTypeCaracteristique());
        Assert.assertEquals(TEMP_GET.getType(), temp.getType());
        
    }

    @Test
    public void updateTypeCaracteristique() {
    	typeCaracteristiqueRepository.save(TEMP_UPDATE);
    	TypeCaracteristique temp = typeCaracteristiqueRepository.findById(TEMP_UPDATE.getIdTypeCaracteristique()).orElse(null);
    	Assert.assertNotNull("TypeCaracteristique ne peut pas être null", temp);
        Assert.assertEquals(TEMP_UPDATE.getIdTypeCaracteristique(), temp.getIdTypeCaracteristique());
        Assert.assertEquals(TEMP_UPDATE.getType(), temp.getType());

        TEMP_UPDATE.setType("Nouveau type type type");
        Assert.assertNotNull(typeCaracteristiqueRepository.save(TEMP_UPDATE));
        temp = typeCaracteristiqueRepository.findById(Integer.toString(TEMP_UPDATE.getIdTypeCaracteristique())).orElse(null);
        Assert.assertNotNull("TypeCaracteristique ne peut pas être null", temp);
        Assert.assertEquals(TEMP_UPDATE.getIdTypeCaracteristique(), temp.getIdTypeCaracteristique());
        Assert.assertEquals(TEMP_UPDATE.getType(), temp.getType());

    }

    @Test
    public void deleteTypeCaracteristique() {
        Assert.assertNotNull(typeCaracteristiqueRepository.save(TEMP_DELETE));
        typeCaracteristiqueRepository.delete(TEMP_DELETE);
        Assert.assertFalse(typeCaracteristiqueRepository.findById(TEMP_DELETE.getIdTypeCaracteristique()).isPresent());
    }
}
