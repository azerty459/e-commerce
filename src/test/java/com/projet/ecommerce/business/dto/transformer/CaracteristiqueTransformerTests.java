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

    private static Caracteristique caracteristique1;
    private static Caracteristique caracteristique2;
    private static List<Caracteristique> caracteristiques = new ArrayList<>();

    static {
        caracteristique1 = new Caracteristique();
        caracteristique1.setIdCaracteristique(1);
        caracteristique1.setType(new TypeCaracteristique(1, "Broché"));
        caracteristique1.setValeur("223 pages");
        caracteristiques.add(caracteristique1);

        caracteristique2 = new Caracteristique();
        caracteristique2.setIdCaracteristique(2);
        caracteristique2.setType(new TypeCaracteristique(2, "Editeur"));
        caracteristique2.setValeur("Flammarion");
        caracteristiques.add(caracteristique2);
    }

    @Test
    public void shouldConvertEntityToDto() {
        CaracteristiqueDTO dto = CaracteristiqueTransformer.entityDTO(caracteristique1);
        Assert.assertNotNull(dto);
        Assert.assertEquals(caracteristique1.getType().getType(), dto.getType());
        Assert.assertEquals(caracteristique1.getValeur(), dto.getValeur());
    }

    @Test
    public void shouldConvertEntityListToDtos() {
        List<CaracteristiqueDTO> dtos = CaracteristiqueTransformer.entitiesToDtos(caracteristiques);
        Assert.assertNotNull(dtos);
        Assert.assertEquals(caracteristique1.getType().getType(), dtos.stream().findFirst().get().getType());
        Assert.assertEquals(caracteristique1.getValeur(), dtos.stream().findFirst().get().getValeur());

        Assert.assertEquals(caracteristique2.getType().getType(), dtos.stream().reduce((first, second) -> second).orElse(null).getType());
        Assert.assertEquals(caracteristique2.getValeur(), dtos.stream().reduce((first, second) -> second).orElse(null).getValeur());
    }
}
