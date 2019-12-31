package com.projet.ecommerce.persistance.repository;


import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")

public class TypeCaracteristiqueRepositoryTests {

    @Autowired
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;


    @Test
    public void findAllSizeShouldReturnThree() {
        List<TypeCaracteristique> typeCaracteristiques = typeCaracteristiqueRepository.findAll();
        System.out.println(typeCaracteristiques);
        Assert.assertEquals(typeCaracteristiques.size(), 3);
    }

    @Test
    public void findByIdOneShouldReturnLibelleBroche() {
        Optional<TypeCaracteristique> typeCaracteristique = typeCaracteristiqueRepository.findById(1);
        Assert.assertEquals(typeCaracteristique.get().getLibelle(), "Broché");

    }

    @Test
    public void insertNewType() {
        TypeCaracteristique typeCaracteristique = new TypeCaracteristique(4,"Nom autheur");
        TypeCaracteristique typeCaracteristiqueRetour = typeCaracteristiqueRepository.save(typeCaracteristique);
        Assert.assertEquals(typeCaracteristiqueRetour.getLibelle(), "Nom autheur");
        Assert.assertEquals(typeCaracteristiqueRepository.findAll().size(), 4);

    }

    @Test
    public void updateType() {
        Optional<TypeCaracteristique> langue = typeCaracteristiqueRepository.findById(3);
        System.out.println(langue);
        langue.get().setLibelle("Language");
        typeCaracteristiqueRepository.save(langue.get());
        Assert.assertEquals(typeCaracteristiqueRepository.findById(3).get().getLibelle(), "Language");

    }

    @Test
    public void deleteType() {
        insertNewType();
        System.out.println(typeCaracteristiqueRepository.findAll());
        typeCaracteristiqueRepository.deleteById(4);
        Assert.assertEquals(typeCaracteristiqueRepository.findAll().size(),3);

    }


}
