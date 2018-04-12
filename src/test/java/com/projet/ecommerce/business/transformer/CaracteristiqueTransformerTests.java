package com.projet.ecommerce.business.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CaracteristiqueTransformerTests {

    TypeCaracteristique typeCaracteristique;
    CaracteristiqueDTO caracteristiqueDTO1, caracteristiqueDTO2;
    Caracteristique caracteristique1, caracteristique2;
    List<CaracteristiqueDTO> listeDTO;
    List<Caracteristique> listeCaracteristiques;

	@Before
	public void setUp() {
        typeCaracteristique = new TypeCaracteristique();
        typeCaracteristique.setIdTypeCaracteristique(0);
        typeCaracteristique.setType("testType");

        caracteristiqueDTO1 = new CaracteristiqueDTO();
        caracteristiqueDTO1.setValeur("testCar1");
        caracteristiqueDTO1.setTypeCaracteristique(typeCaracteristique);

        caracteristiqueDTO2 = new CaracteristiqueDTO();
        caracteristiqueDTO2.setValeur("testCar2");
        caracteristiqueDTO2.setTypeCaracteristique(typeCaracteristique);

        caracteristique1 = new Caracteristique();
        caracteristique1.setTypeCaracteristique(typeCaracteristique);
        caracteristique1.setValeur("test1");

        caracteristique2 = new Caracteristique();
        caracteristique2.setTypeCaracteristique(typeCaracteristique);
        caracteristique2.setValeur("test2");

        listeDTO = new ArrayList<CaracteristiqueDTO>();
        listeDTO.add(caracteristiqueDTO1);
        listeDTO.add(caracteristiqueDTO2);

        listeCaracteristiques = new ArrayList<Caracteristique>();
        listeCaracteristiques.add(caracteristique1);
        listeCaracteristiques.add(caracteristique2);
	}

	@Test
    public void singleDtoToEntity(){
        Caracteristique car = CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO1);

        Assert.assertNotNull(car);
        Assert.assertEquals(caracteristiqueDTO1.getTypeCaracteristique(), car.getTypeCaracteristique());
        Assert.assertEquals(caracteristiqueDTO1.getValeur(), car.getValeur());
    }

    @Test
    public void singleEntityToDto(){
	    CaracteristiqueDTO carDTO = CaracteristiqueTransformer.entityToDto(caracteristique1);

	    Assert.assertNotNull(carDTO);
        Assert.assertEquals(carDTO.getTypeCaracteristique(), caracteristique1.getTypeCaracteristique());
        Assert.assertEquals(carDTO.getValeur(), caracteristique1.getValeur());
    }

    @Test
    public void severalDtoToEntity(){
        List<Caracteristique> listCar = new ArrayList<>(CaracteristiqueTransformer.dtoToEntity(listeDTO));

        Assert.assertNotNull(listCar);
        Assert.assertEquals(listCar.get(0).getTypeCaracteristique(), listeDTO.get(0).getTypeCaracteristique());
        Assert.assertEquals(listCar.get(0).getValeur(), listeDTO.get(0).getValeur());
        Assert.assertEquals(listCar.get(1).getTypeCaracteristique(), listeDTO.get(1).getTypeCaracteristique());
        Assert.assertEquals(listCar.get(1).getValeur(), listeDTO.get(1).getValeur());
    }

    @Test
    public void severalEntityToDto(){
        List<CaracteristiqueDTO> listDTO = new ArrayList<>(CaracteristiqueTransformer.entityToDto(listeCaracteristiques));

        Assert.assertNotNull(listDTO);
        Assert.assertEquals(listeCaracteristiques.get(0).getTypeCaracteristique(), listDTO.get(0).getTypeCaracteristique());
        Assert.assertEquals(listeCaracteristiques.get(0).getValeur(), listDTO.get(0).getValeur());
        Assert.assertEquals(listeCaracteristiques.get(1).getTypeCaracteristique(), listDTO.get(1).getTypeCaracteristique());
        Assert.assertEquals(listeCaracteristiques.get(1).getValeur(), listDTO.get(1).getValeur());
    }
}