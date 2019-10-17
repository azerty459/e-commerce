package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.TypeCaracteristiqueRepository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
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
	
	private Caracteristique caracteristique;
	private Caracteristique caracteristique1;
	private Caracteristique caracteristique2;
	private Caracteristique caracteristique3;

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

    }
    
    private void buildCaracteristique() {
    	caracteristique = new Caracteristique();
    	caracteristique.setReferenceProduit("reference");
    	caracteristique.setTypeCaracteristique("type");
    	caracteristique.setValeurCaracteristique("valeur");
    	
    	caracteristique1 = new Caracteristique();
    	caracteristique1.setTypeCaracteristique("type");
    	caracteristique1.setValeurCaracteristique("valeur");
    	
    	caracteristique2 = new Caracteristique();
    	caracteristique2.setReferenceProduit("reference");
    	caracteristique2.setValeurCaracteristique("valeur");
    	
    	caracteristique3 = new Caracteristique();
    	caracteristique3.setReferenceProduit("reference");
    	caracteristique3.setTypeCaracteristique("type");
    }
    
    @Before
    public void setUp() {
    	buildCaracteristique();
    }

    @Test
    public void testAddCaracteristiqueOk(){
        Caracteristique caract = caracteristique;
        Mockito.when(caracteristiqueRepository.save(Mockito.any())).thenReturn(caract);

        CaracteristiqueDTO caracteristiqueDTO = caracteristiqueBusiness.addCaracteristique("reference", "type", "valeur");
        Assert.assertNotNull(caracteristiqueDTO);
        Assert.assertEquals(caract.getValeurCaracteristique(), caracteristiqueDTO.getValeur());
        Assert.assertEquals(caract.getTypeCaracteristique(), caracteristiqueDTO.getType());
        Assert.assertEquals(caract.getReferenceProduit(), caracteristiqueDTO.getReferenceProduit());
    }
    
    @Test
    public void addCaracteristiqueNullParams(){
        thrown.expect(GraphQLCustomException.class);
        thrown.expectMessage("Erreur dans l'ajout de la caractéristique (la référence du produit, le type et la valeur ne peuvent être null)");
        caracteristiqueBusiness.addCaracteristique(null, "type", "valeur");
        
        thrown.expect(GraphQLCustomException.class);
        thrown.expectMessage("Erreur dans l'ajout de la caractéristique (la référence du produit, le type et la valeur ne peuvent être null)");
        caracteristiqueBusiness.addCaracteristique("reference", null, "valeur");
        
        thrown.expect(GraphQLCustomException.class);
        thrown.expectMessage("Erreur dans l'ajout de la caractéristique (la référence du produit, le type et la valeur ne peuvent être null)");
        caracteristiqueBusiness.addCaracteristique("reference", "type", null);
    }
    
    @Test
    public void addCaracteristiqueFindByIdOk(){
    	Mockito.when(caracteristiqueRepository.findById(Mockito.any())).thenReturn(Optional.of(caracteristique));
        
        thrown.expect(GraphQLCustomException.class);
        thrown.expectMessage("Le produit possède déjà ce type de caractéristique");
        caracteristiqueBusiness.addCaracteristique("reference", "type", "valeur");
    }
    
    @Test
    public void updateCaracteristiqueNull(){
    	Caracteristique caract = null;
    	CaracteristiqueDTO caracteristiqueDTO = caracteristiqueBusiness.updateCaracteristique(caract);
    	Assert.assertNull(caracteristiqueDTO);
    	
    	CaracteristiqueDTO caracteristiqueDTO1 = caracteristiqueBusiness.updateCaracteristique(caracteristique1);
    	Assert.assertNull(caracteristiqueDTO1);
    	CaracteristiqueDTO caracteristiqueDTO2 = caracteristiqueBusiness.updateCaracteristique(caracteristique2);
    	Assert.assertNull(caracteristiqueDTO2);
    	CaracteristiqueDTO caracteristiqueDTO3 = caracteristiqueBusiness.updateCaracteristique(caracteristique3);
    	Assert.assertNull(caracteristiqueDTO3);
    }
    
    @Test
    public void updateCaracteristiqueExistsByIdKo(){
    	Mockito.when(caracteristiqueRepository.existsById(Mockito.any())).thenReturn(false);
        
        thrown.expect(GraphQLCustomException.class);
        thrown.expectMessage("Le produit ne possède pas ce type de caractéristique");
        caracteristiqueBusiness.updateCaracteristique(caracteristique);
    }
    
    @Test
    public void testUpdateCaracteristiqueOk(){
        Caracteristique caract = caracteristique;
        Mockito.when(caracteristiqueRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(caracteristiqueRepository.save(Mockito.any())).thenReturn(caract);

        CaracteristiqueDTO caracteristiqueDTO = caracteristiqueBusiness.updateCaracteristique(caract);
        Assert.assertNotNull(caracteristiqueDTO);
        Assert.assertEquals(caract.getValeurCaracteristique(), caracteristiqueDTO.getValeur());
        Assert.assertEquals(caract.getTypeCaracteristique(), caracteristiqueDTO.getType());
        Assert.assertEquals(caract.getReferenceProduit(), caracteristiqueDTO.getReferenceProduit());
    }
    
    @Test
    public void deleteCaracteristiqueNullParams(){
    	Assert.assertFalse(caracteristiqueBusiness.deleteCaracteristique(null, null));
    	Assert.assertFalse(caracteristiqueBusiness.deleteCaracteristique(null, "type"));
    	Assert.assertFalse(caracteristiqueBusiness.deleteCaracteristique("reference", null));
    }
    
    @Test
    public void testDeleteCaracteristiqueOk(){
    	Assert.assertTrue(caracteristiqueBusiness.deleteCaracteristique("reference", "type"));
    }
}
