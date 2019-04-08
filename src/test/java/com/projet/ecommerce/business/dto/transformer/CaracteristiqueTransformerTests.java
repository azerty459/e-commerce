package com.projet.ecommerce.business.dto.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

public class CaracteristiqueTransformerTests {

	
	private CaracteristiqueDTO getCaracteristiqueDTO() {
		CaracteristiqueDTO caracteristiqueDTO = new CaracteristiqueDTO();
		caracteristiqueDTO.setIdCaracteristique(new Random().nextInt(1000));
		caracteristiqueDTO.setValeur("Valeur de la caractéristique");
		TypeCaracteristique typeCar = new TypeCaracteristique();
		typeCar.setIdTypeCaracteristique(new Random().nextInt(1000));
		typeCar.setTypeCaracteristique("Longueur");
		caracteristiqueDTO.setTypeCaracteristique(typeCar);
		Produit produitTest = new Produit();
		produitTest.setDescription("ceci est un test");
		produitTest.setNom("test");
		produitTest.setPrixHT(1);

		caracteristiqueDTO.setProduit(produitTest);
		
		return caracteristiqueDTO;
	}

	private Caracteristique getCaracteristique() {
		Caracteristique caracteristique = new Caracteristique();
		caracteristique.setIdCaracteristique(new Random().nextInt(1000));
		caracteristique.setValeurCaracteristique("Valeur de la caractéristique");
		TypeCaracteristique typeCar = new TypeCaracteristique();
		typeCar.setIdTypeCaracteristique(new Random().nextInt(1000));
		typeCar.setTypeCaracteristique("Longueur");
		caracteristique.setTypeCaracteristique(typeCar);
		Produit produitTest = new Produit();
		produitTest.setDescription("ceci est un test");
		produitTest.setNom("test");
		produitTest.setPrixHT(1);

		caracteristique.setProduit(produitTest);
		
		return caracteristique;
	}
	
	
	private void assertData(Caracteristique caracteristique, CaracteristiqueDTO caracteristiqueDTO){
        Assert.assertNotNull(caracteristique);
        Assert.assertNotNull(caracteristiqueDTO);
        Assert.assertEquals(caracteristique.getIdCaracteristique(), caracteristiqueDTO.getIdCaracteristique());
        Assert.assertEquals(caracteristique.getValeurCaracteristique(), caracteristiqueDTO.getValeur());
        Assert.assertEquals(caracteristique.getProduit(), caracteristiqueDTO.getProduit());
        Assert.assertEquals(caracteristique.getTypeCaracteristique(), caracteristiqueDTO.getTypeCaracteristique());
    }
	
	private List<CaracteristiqueDTO> getListeCaracteristiqueDTO() {
		List<CaracteristiqueDTO> listeCaracteristiqueDTO = new ArrayList<>();
		listeCaracteristiqueDTO.add(this.getCaracteristiqueDTO());
		listeCaracteristiqueDTO.add(this.getCaracteristiqueDTO());
        return listeCaracteristiqueDTO;
	}
	
	private List<Caracteristique> getListeCaracteristique() {
		List<Caracteristique> listeCaracteristique = new ArrayList<>();
		listeCaracteristique.add(this.getCaracteristique());
		listeCaracteristique.add(this.getCaracteristique());
        return listeCaracteristique;
	}
	
	@Test
    public void singleDtoToEntity() {
        CaracteristiqueDTO caracteristiqueDTO = this.getCaracteristiqueDTO();
        Caracteristique caracteristique = CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO);

        this.assertData(caracteristique, caracteristiqueDTO);
    }
	
	@Test
    public void singleEntiyToDto() {
        Caracteristique caracteristique = this.getCaracteristique();
        CaracteristiqueDTO caracteristiqueDTO = CaracteristiqueTransformer.entityToDto(caracteristique);

        this.assertData(caracteristique, caracteristiqueDTO);
    }

	@Test
    public void severalDtoToEntity() {
        List<CaracteristiqueDTO> listeCaracteristiqueDTO = this.getListeCaracteristiqueDTO();
        List<Caracteristique> listeCaracteristique = new ArrayList<>(CaracteristiqueTransformer.listeDtoToEntity(listeCaracteristiqueDTO));

        Assert.assertNotNull(listeCaracteristique);
        this.assertData(listeCaracteristique.get(0), listeCaracteristiqueDTO.get(0));
        this.assertData(listeCaracteristique.get(1), listeCaracteristiqueDTO.get(1));
    }

    @Test
    public void severalEntityToDto() {
        List<Caracteristique> listeCaracteristique = this.getListeCaracteristique();
        List<CaracteristiqueDTO> listeCaracteristiqueDTO = new ArrayList<>(CaracteristiqueTransformer.listeEntityToDto(listeCaracteristique));

        Assert.assertNotNull(listeCaracteristiqueDTO);
        this.assertData(listeCaracteristique.get(0), listeCaracteristiqueDTO.get(0));
        this.assertData(listeCaracteristique.get(1), listeCaracteristiqueDTO.get(1));
    }

	
	
}
