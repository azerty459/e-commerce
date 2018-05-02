package com.projet.ecommerce.business.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.business.dto.transformer.CategorieTransformer;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CategorieTransformerTests {

    private static final Categorie CATEGORIE_1;
    private static final Categorie CATEGORIE_2;
    private static final Categorie CATEGORIE_3;
    private static final Categorie CATEGORIE_4;
    private static final Categorie CATEGORIE_5;

    private static final List<Categorie> CATEGORIE_LIST;

    static {
        CATEGORIE_1 = new Categorie();
        CATEGORIE_1.setNomCategorie("Transport");
        CATEGORIE_1.setLevel(1);
        CATEGORIE_1.setBorneGauche(1);
        CATEGORIE_1.setBorneDroit(10);
        CATEGORIE_1.setProduits(new ArrayList<>());

        CATEGORIE_2 = new Categorie();
        CATEGORIE_2.setNomCategorie("Aerien");
        CATEGORIE_2.setLevel(2);
        CATEGORIE_2.setBorneGauche(2);
        CATEGORIE_2.setBorneDroit(7);
        CATEGORIE_2.setProduits(new ArrayList<>());

        CATEGORIE_3 = new Categorie();
        CATEGORIE_3.setNomCategorie("Avion");
        CATEGORIE_3.setLevel(3);
        CATEGORIE_3.setBorneGauche(3);
        CATEGORIE_3.setBorneDroit(4);
        CATEGORIE_3.setProduits(new ArrayList<>());

        CATEGORIE_4 = new Categorie();
        CATEGORIE_4.setNomCategorie("Fusée");
        CATEGORIE_4.setLevel(3);
        CATEGORIE_4.setBorneGauche(5);
        CATEGORIE_4.setBorneDroit(6);
        CATEGORIE_4.setProduits(new ArrayList<>());

        CATEGORIE_5 = new Categorie();
        CATEGORIE_5.setNomCategorie("Terrestre");
        CATEGORIE_5.setLevel(2);
        CATEGORIE_5.setBorneGauche(8);
        CATEGORIE_5.setBorneDroit(9);
        CATEGORIE_5.setProduits(new ArrayList<>());

        CATEGORIE_LIST = new ArrayList<>();
        CATEGORIE_LIST.add(CATEGORIE_1);
        CATEGORIE_LIST.add(CATEGORIE_2);
        CATEGORIE_LIST.add(CATEGORIE_3);
        CATEGORIE_LIST.add(CATEGORIE_4);
        CATEGORIE_LIST.add(CATEGORIE_5);
    }

    @Test
    public void entityToDto(){
        CategorieDTO categorieDTO = CategorieTransformer.entityToDto(CATEGORIE_1, CATEGORIE_LIST);
        Assert.assertNotNull(categorieDTO);
        Assert.assertEquals(categorieDTO.getNom(), CATEGORIE_1.getNomCategorie());
        Assert.assertEquals(categorieDTO.getSousCategories().get(0).getNom(), CATEGORIE_2.getNomCategorie());
        Assert.assertEquals(categorieDTO.getSousCategories().get(1).getNom(), CATEGORIE_5.getNomCategorie());
        Assert.assertEquals(categorieDTO.getSousCategories().get(0).getSousCategories().get(0).getNom(), CATEGORIE_3.getNomCategorie());
        Assert.assertEquals(categorieDTO.getSousCategories().get(0).getSousCategories().get(1).getNom(), CATEGORIE_4.getNomCategorie());
    }

    @Test
    public void listEntityToDto(){
        List<CategorieDTO> categorieDTOList = new ArrayList<>(CategorieTransformer.entityToDto(CATEGORIE_LIST, true));
        Assert.assertEquals(1, categorieDTOList.size());
        Assert.assertEquals(categorieDTOList.get(0).getNom(), CATEGORIE_1.getNomCategorie());
        Assert.assertEquals(categorieDTOList.get(0).getSousCategories().get(0).getNom(), CATEGORIE_2.getNomCategorie());
        Assert.assertEquals(categorieDTOList.get(0).getSousCategories().get(1).getNom(), CATEGORIE_5.getNomCategorie());
        Assert.assertEquals(categorieDTOList.get(0).getSousCategories().get(0).getSousCategories().get(0).getNom(), CATEGORIE_3.getNomCategorie());
        Assert.assertEquals(categorieDTOList.get(0).getSousCategories().get(0).getSousCategories().get(1).getNom(), CATEGORIE_4.getNomCategorie());
    }
}