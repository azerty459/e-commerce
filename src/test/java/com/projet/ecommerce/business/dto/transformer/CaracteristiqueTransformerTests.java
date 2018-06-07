package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CaracteristiqueTransformerTests {

    private static final TypeCaracteristique TYPE_CARACTERISTIQUE;
    private static final CaracteristiqueDTO CARACTERISTIQUE_DTO1;
    private static final CaracteristiqueDTO  CARACTERISTIQUE_DTO2;
    private static final Caracteristique CARACTERISTIQUE1;
    private static final Caracteristique CARACTERISTIQUE2;

	static {
        TYPE_CARACTERISTIQUE = new TypeCaracteristique();
        TYPE_CARACTERISTIQUE.setIdTypeCaracteristique(0);
        TYPE_CARACTERISTIQUE.setType("testType");

        CARACTERISTIQUE_DTO1 = new CaracteristiqueDTO();
        CARACTERISTIQUE_DTO1.setValeur("testCar1");
        CARACTERISTIQUE_DTO1.setTypeCaracteristique(TYPE_CARACTERISTIQUE);

        CARACTERISTIQUE_DTO2 = new CaracteristiqueDTO();
        CARACTERISTIQUE_DTO2.setValeur("testCar2");
        CARACTERISTIQUE_DTO2.setTypeCaracteristique(TYPE_CARACTERISTIQUE);

        CARACTERISTIQUE1 = new Caracteristique();
        CARACTERISTIQUE1.setTypeCaracteristique(TYPE_CARACTERISTIQUE);
        CARACTERISTIQUE1.setValeur("test1");

        CARACTERISTIQUE2 = new Caracteristique();
        CARACTERISTIQUE2.setTypeCaracteristique(TYPE_CARACTERISTIQUE);
        CARACTERISTIQUE2.setValeur("test2");
	}

	@Test
    public void singleDtoToEntity(){
        Caracteristique car = CaracteristiqueTransformer.dtoToEntity(CARACTERISTIQUE_DTO1);

        Assert.assertNotNull(car);
        Assert.assertEquals(CARACTERISTIQUE_DTO1.getTypeCaracteristique(), car.getTypeCaracteristique());
        Assert.assertEquals(CARACTERISTIQUE_DTO1.getValeur(), car.getValeur());
    }

    @Test
    public void singleEntityToDto(){
	    CaracteristiqueDTO carDTO = CaracteristiqueTransformer.entityToDto(CARACTERISTIQUE1);

	    Assert.assertNotNull(carDTO);
        Assert.assertEquals(carDTO.getTypeCaracteristique(), CARACTERISTIQUE1.getTypeCaracteristique());
        Assert.assertEquals(carDTO.getValeur(), CARACTERISTIQUE1.getValeur());
    }

    @Test
    public void severalDtoToEntity(){
        List<CaracteristiqueDTO> listeDTO;
        listeDTO = new ArrayList<>();
        listeDTO.add(CARACTERISTIQUE_DTO1);
        listeDTO.add(CARACTERISTIQUE_DTO2);

        List<Caracteristique> listCar = new ArrayList<>(CaracteristiqueTransformer.dtoToEntity(listeDTO));

        Assert.assertNotNull(listCar);
        Assert.assertEquals(listCar.get(0).getTypeCaracteristique(), listeDTO.get(0).getTypeCaracteristique());
        Assert.assertEquals(listCar.get(0).getValeur(), listeDTO.get(0).getValeur());
        Assert.assertEquals(listCar.get(1).getTypeCaracteristique(), listeDTO.get(1).getTypeCaracteristique());
        Assert.assertEquals(listCar.get(1).getValeur(), listeDTO.get(1).getValeur());
    }

    @Test
    public void severalEntityToDto(){
        List<Caracteristique> listeCaracteristiques;
        listeCaracteristiques = new ArrayList<>();
        listeCaracteristiques.add(CARACTERISTIQUE1);
        listeCaracteristiques.add(CARACTERISTIQUE2);

        List<CaracteristiqueDTO> listDTO = new ArrayList<>(CaracteristiqueTransformer.entityToDto(listeCaracteristiques));

        Assert.assertNotNull(listDTO);
        Assert.assertEquals(listeCaracteristiques.get(0).getTypeCaracteristique(), listDTO.get(0).getTypeCaracteristique());
        Assert.assertEquals(listeCaracteristiques.get(0).getValeur(), listDTO.get(0).getValeur());
        Assert.assertEquals(listeCaracteristiques.get(1).getTypeCaracteristique(), listDTO.get(1).getTypeCaracteristique());
        Assert.assertEquals(listeCaracteristiques.get(1).getValeur(), listDTO.get(1).getValeur());
    }
}