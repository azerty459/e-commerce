package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CaracteristiqueTransformerTests {
    
    private static final Random RANDOM = new Random();
    private static final int ITERATION = 8;
    
    @Test
    public void testEntityToDto() {
        Caracteristique caracteristique = buildCaracteristique();
        System.out.println(caracteristique.getLibelle());
        CaracteristiqueDTO caracteristiqueDTO = CaracteristiqueTransformer.entityToDto(caracteristique);
        assertTransformEquals(caracteristique, caracteristiqueDTO);
    }
    
    @Test
    public void testCollectionEntityToDto() {
        List<Caracteristique> listCaracteristique = new ArrayList<>();
        for(int i = 0; i < ITERATION; i++) {
            listCaracteristique.add(buildCaracteristique());
        }
        List<CaracteristiqueDTO> listCaracteristiqueDTO = new ArrayList<>(CaracteristiqueTransformer.entityToDto(listCaracteristique));
        Assert.assertEquals(listCaracteristique.size(), listCaracteristiqueDTO.size());
        for(int i = 0; i < ITERATION; i++) {
            assertTransformEquals(listCaracteristique.get(i), listCaracteristiqueDTO.get(i));
        }
    }
    
    @Test
    public void testDtoToEntity() {
        CaracteristiqueDTO caracteristiqueDTO = buildCaracteristiqueDTO();
        Caracteristique caracteristique = CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO);
        assertTransformEquals(caracteristique, caracteristiqueDTO);
    }
    
    @Test
    public void testCollectionDtoToEntity() {
        List<CaracteristiqueDTO> listCaracteristiqueDTO = new ArrayList<>();
        for(int i = 0; i < ITERATION; i++) {
            listCaracteristiqueDTO.add(buildCaracteristiqueDTO());
        }
        List<Caracteristique> listCaracteristique = new ArrayList<>(CaracteristiqueTransformer.dtoToEntity(listCaracteristiqueDTO));
        Assert.assertEquals(listCaracteristiqueDTO.size(), listCaracteristique.size());
        for(int i = 0; i < ITERATION; i++) {
            assertTransformEquals(listCaracteristique.get(i), listCaracteristiqueDTO.get(i));
        }
    }
    
    private void assertTransformEquals (Caracteristique caracteristique, CaracteristiqueDTO caracteristiqueDTO) {
        Assert.assertEquals(caracteristique.getIdCaracteristique(), caracteristiqueDTO.getId());
        Assert.assertEquals(caracteristique.getLibelle(), caracteristiqueDTO.getLibelle());
    }
    
    /* ----- Builder ----- */
    
    private Caracteristique buildCaracteristique() {
        Caracteristique c = new Caracteristique();
        c.setIdCaracteristique(RANDOM.nextInt());
        c.setLibelle(randomString());
        return c;
    }
    
    private CaracteristiqueDTO buildCaracteristiqueDTO() {
        return new CaracteristiqueDTO(RANDOM.nextInt(), randomString());
    }
    
    private String randomString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < ITERATION; i++){
            builder.append((char) (RANDOM.nextInt(26) + 97));
        }
        return builder.toString();
    }
    
}
