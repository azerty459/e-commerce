package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CaracteristiqueTransformerTests {
	
    private static final Caracteristique CARACTERISTIQUE = new Caracteristique();

    static {
    	CARACTERISTIQUE.setReferenceProduit("reference");
    	CARACTERISTIQUE.setTypeCaracteristique("type");
    	CARACTERISTIQUE.setValeurCaracteristique("valeur");
    }

    @Test
    public void entityToDto() {
        CaracteristiqueDTO caracteristiqueDTO = CaracteristiqueTransformer.entityToDto(CARACTERISTIQUE);

        Assert.assertNotNull(caracteristiqueDTO);
        Assert.assertEquals(caracteristiqueDTO.getReferenceProduit(), CARACTERISTIQUE.getReferenceProduit());
        Assert.assertEquals(caracteristiqueDTO.getType(), CARACTERISTIQUE.getTypeCaracteristique());
        Assert.assertEquals(caracteristiqueDTO.getValeur(), CARACTERISTIQUE.getValeurCaracteristique());
    }

}
