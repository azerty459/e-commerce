package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CaracteristiqueTransformerTests {
    private static final Caracteristique CARACT = new Caracteristique();

    static {
        CARACT.setReferenceProduit("A");
        CARACT.setType("T");
        CARACT.setValeur("V");
    }

    @Test
    public void singleEntityToDto() {
        CaracteristiqueDTO caractDTO = CaracteristiqueTransformer.entityToDto(CARACT);

        Assert.assertNotNull(caractDTO);
        Assert.assertEquals(caractDTO.getReferenceProduit(), CARACT.getReferenceProduit());
        Assert.assertEquals(caractDTO.getType(), CARACT.getType());
        Assert.assertEquals(caractDTO.getValeur(), CARACT.getValeur());
    }

}
