package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
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

    @InjectMocks
    private CaracteristiqueBusiness business;

    @Mock
    private CaracteristiqueRepository caracteristiqueRepository;

    @Mock
    private TypeCaracteristiqueRepository typeCaracteristiqueRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addCaracteristique(){
        Caracteristique caracteristique = buildCaracteristique();
        Mockito.when(caracteristiqueRepository.save(Mockito.any())).thenReturn(caracteristique);
        Mockito.when(typeCaracteristiqueRepository.existsById(Mockito.any())).thenReturn(true);

        CaracteristiqueDTO caractDTO = business.addCaracteristique("R", "T", "V");
        Assert.assertNotNull(caractDTO);
        Assert.assertEquals(caracteristique.getValeur(), caractDTO.getValeur());
        Assert.assertEquals(caracteristique.getType(), caractDTO.getType());
        Assert.assertEquals(caracteristique.getReferenceProduit(), caractDTO.getReferenceProduit());
    }

    @Test
    public void addCaracteristiqueDejaExistante(){
        Mockito.when(caracteristiqueRepository.existsById(Mockito.any())).thenReturn(true);
        thrown.expect(GraphQLCustomException.class);
        business.addCaracteristique("R", "T", "V");
    }

    @Test
    public void addCaracteristiqueAvecTypeNonRepertorie(){
        Mockito.when(typeCaracteristiqueRepository.existsById(Mockito.any())).thenReturn(false);
        thrown.expect(GraphQLCustomException.class);
        business.addCaracteristique("R", "T", "V");
    }

    private Caracteristique buildCaracteristique() {
        Caracteristique caracteristique = new Caracteristique();
        caracteristique.setReferenceProduit("R");
        caracteristique.setType("T");
        caracteristique.setValeur("V");
        return caracteristique;
    }
}
