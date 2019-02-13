package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;

@SpringBootTest
public class CaracteristiqueTransformerTests {
	
	@Test
    public void singleDtoToEntity() {
        CaracteristiqueDTO caracteristiqueDTO = this.getCaracteristiqueDTO();
        Caracteristique caracteristique = CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO);

        this.assertData(caracteristique, caracteristiqueDTO);
    }

    @Test
    public void singleEntityToDto() {
        Caracteristique caracteristique = this.getCaracteristique();
        CaracteristiqueDTO caracteristiqueDTO = CaracteristiqueTransformer.entityToDto(caracteristique);

        this.assertData(caracteristique, caracteristiqueDTO);
    }

    @Test
    public void severalDtoToEntity() {
        List<CaracteristiqueDTO> caracteristiqueDTOS = this.getListeCaracteristiqueDTO();
        List<Caracteristique> listeCaracteristique = new ArrayList<>(CaracteristiqueTransformer.listDtoToEntity(caracteristiqueDTOS));

        Assert.assertNotNull(listeCaracteristique);
        this.assertData(listeCaracteristique.get(0), caracteristiqueDTOS.get(0));
        this.assertData(listeCaracteristique.get(1), caracteristiqueDTOS.get(1));
    }

    @Test
    public void severalDtoToEntity_null() {
        List<Caracteristique> listeCaracteristique = new ArrayList<>(CaracteristiqueTransformer.listDtoToEntity(null));
        Assert.assertTrue(listeCaracteristique.isEmpty());
    }

    @Test
    public void severalEntityToDto_null() {
        List<CaracteristiqueDTO> caracteristiqueDTOS = new ArrayList<>(CaracteristiqueTransformer.listEntityToDto(null));
        Assert.assertTrue(caracteristiqueDTOS.isEmpty());
    }

    @Test
    public void severalEntityToDto() {
        List<Caracteristique> caracteristiques = this.getListeCaracteristique();
        List<CaracteristiqueDTO> caracteristiqueDTOS = new ArrayList<>(CaracteristiqueTransformer.listEntityToDto(caracteristiques));

        Assert.assertNotNull(caracteristiqueDTOS);
        this.assertData(caracteristiques.get(0), caracteristiqueDTOS.get(0));
        this.assertData(caracteristiques.get(1), caracteristiqueDTOS.get(1));
    }

    private List<CaracteristiqueDTO> getListeCaracteristiqueDTO(){
        List<CaracteristiqueDTO> liste = new ArrayList<>();
        liste.add(this.getCaracteristiqueDTO());
        liste.add(this.getCaracteristiqueDTO());
        return liste;
    }

    private List<Caracteristique> getListeCaracteristique(){
        List<Caracteristique> liste = new ArrayList<>();
        liste.add(this.getCaracteristique());
        liste.add(this.getCaracteristique());
        return liste;
    }

    private CaracteristiqueDTO getCaracteristiqueDTO(){
        CaracteristiqueDTO caracDTO = new CaracteristiqueDTO();
        caracDTO.setId(new Random().nextInt(100));
        caracDTO.setTypeCaracteristique(TypeCaracteristiqueDTO.LANGUE);
        caracDTO.setValeur("dto valeur");
        return caracDTO;
    }

    private Caracteristique getCaracteristique(){
        Caracteristique carac = new Caracteristique();
        carac.setId(new Random().nextInt(100));
        carac.setValeur("entity valeur");
        carac.setTypeCaracteristique(TypeCaracteristiqueDTO.LANGUE);
        return carac;
    }

    private void assertData(Caracteristique caracteristique, CaracteristiqueDTO caracteristiqueDTO){
        Assert.assertNotNull(caracteristique);
        Assert.assertNotNull(caracteristiqueDTO);
        Assert.assertEquals(caracteristique.getId(), caracteristiqueDTO.getId());
        Assert.assertEquals(caracteristique.getValeur(), caracteristiqueDTO.getValeur());
        Assert.assertEquals(caracteristique.getTypeCaracteristique(), caracteristiqueDTO.getTypeCaracteristique());
        if(caracteristique.getProduit() != null){
            Assert.assertEquals(caracteristique.getProduit().getReferenceProduit(), caracteristiqueDTO.getRefProduit());
        }
    }

}
