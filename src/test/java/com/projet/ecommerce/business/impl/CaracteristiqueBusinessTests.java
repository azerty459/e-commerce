package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CaracteristiqueBusinessTests {
	
	private static final Caracteristique CARACTERISTIQUE = new Caracteristique();

    @InjectMocks
    private CaracteristiqueBusiness caracteristiqueBusiness;

    @Mock
    private CaracteristiqueRepository caracteristiqueRepository;

    @Mock
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    static {

        //Permet d'écraser la config application.properties par application-test.properties
        System.setProperty("spring.config.location", "classpath:application-test.properties");

        buildCaracteristique();

    }
    
    private static void buildCaracteristique() {
    	CARACTERISTIQUE.setReferenceProduit("reference");
    	CARACTERISTIQUE.setTypeCaracteristique("type");
    	CARACTERISTIQUE.setValeurCaracteristique("valeur");
    }

    @Test
    public void addCaracteristique(){
        Caracteristique caracteristique = CARACTERISTIQUE;
        Mockito.when(caracteristiqueRepository.save(Mockito.any())).thenReturn(caracteristique);
        Mockito.when(typeCaracteristiqueRepository.existsById(Mockito.any())).thenReturn(true);

        CaracteristiqueDTO caracteristiqueDTO = caracteristiqueBusiness.addCaracteristique("reference", "type", "valeur");
        Assert.assertNotNull(caracteristiqueDTO);
        Assert.assertEquals(caracteristique.getValeurCaracteristique(), caracteristiqueDTO.getValeur());
        Assert.assertEquals(caracteristique.getTypeCaracteristique(), caracteristiqueDTO.getType());
        Assert.assertEquals(caracteristique.getReferenceProduit(), caracteristiqueDTO.getReferenceProduit());
    }
}
