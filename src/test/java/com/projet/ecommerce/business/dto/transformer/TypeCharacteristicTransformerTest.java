package com.projet.ecommerce.business.dto.transformer;

import com.projet.ecommerce.business.dto.TypeCharacteristicDTO;
import com.projet.ecommerce.persistance.entity.TypeCharacteristic;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.*;

@SpringBootTest
public class TypeCharacteristicTransformerTest {

    @Test
    public void listDtoToEntity() {
        List<TypeCharacteristicDTO> typeCharacteristicDTOList = new ArrayList<>();

        //Object 1
        TypeCharacteristicDTO typeCharacteristicDTO = buildTypeCharacteristicDTO(1, randomAlphabetic(6));
        typeCharacteristicDTOList.add(typeCharacteristicDTO);

        //Object 2
        TypeCharacteristicDTO typeCharacteristicDTO1 = buildTypeCharacteristicDTO(2, randomAlphabetic(6));
        typeCharacteristicDTOList.add(typeCharacteristicDTO1);

        List<TypeCharacteristic> typeCharacteristicList = TypeCharacteristicTransformer.listDtoToEntity(typeCharacteristicDTOList);

        assertNotNull(typeCharacteristicDTO);
        assertNotNull(typeCharacteristicDTO1);
        assertNotNull(typeCharacteristicDTOList);
    }

    @Test
    public void dtoToEntity() {
        TypeCharacteristicDTO typeCharacteristicDTO = buildTypeCharacteristicDTO(1, randomAlphabetic(6));
        TypeCharacteristic typeCharacteristic = TypeCharacteristicTransformer.dtoToEntity(typeCharacteristicDTO);

        assertNotNull(typeCharacteristic);
        assertNotNull(typeCharacteristicDTO);
        assertEquals(typeCharacteristic.getIdCharacteristicType(), typeCharacteristicDTO.getId());
        assertEquals(typeCharacteristic.getTypeCharacteristic(), typeCharacteristicDTO.getType());
    }

    @Test
    public void listEntityToDto() {
        List<TypeCharacteristic> typeCharacteristicList = new ArrayList<>();

        //Object 1
        TypeCharacteristic typeCharacteristic = buildTypeCharacteristic(1, randomAlphabetic(6));
        typeCharacteristicList.add(typeCharacteristic);

        //Object 2
        TypeCharacteristic typeCharacteristic1 = buildTypeCharacteristic(2, randomAlphabetic(6));
        typeCharacteristicList.add(typeCharacteristic1);

        List<TypeCharacteristicDTO> typeCharacteristicDTOList = TypeCharacteristicTransformer.listEntityToDto(typeCharacteristicList);

        assertNotNull(typeCharacteristic);
        assertNotNull(typeCharacteristic1);
        assertNotNull(typeCharacteristicList);
        assertNotNull(typeCharacteristicDTOList);
    }

    @Test
    public void entityToDto() {
        TypeCharacteristic typeCharacteristic = buildTypeCharacteristic(1, randomAlphabetic(6));
        TypeCharacteristicDTO typeCharacteristicDTO = TypeCharacteristicTransformer.entityToDto(typeCharacteristic);

        assertNotNull(typeCharacteristic);
        assertNotNull(typeCharacteristicDTO);
        assertEquals(typeCharacteristic.getIdCharacteristicType(), typeCharacteristicDTO.getId());
        assertEquals(typeCharacteristic.getTypeCharacteristic(), typeCharacteristicDTO.getType());
    }

    /**
     * Method defining the way to build a TypeCharacteristic Entity
     * @param id
     * @param type
     * @return
     */
    public TypeCharacteristic buildTypeCharacteristic(Integer id, String type) {
        TypeCharacteristic typeCharacteristic = new TypeCharacteristic();
        typeCharacteristic.setIdCharacteristicType(id);
        typeCharacteristic.setTypeCharacteristic(type);

        return typeCharacteristic;
    }

    /**
     * Method defining the way to build a DTO
     * @param id
     * @param type
     * @return
     */
    public TypeCharacteristicDTO buildTypeCharacteristicDTO(Integer id, String type) {
        TypeCharacteristicDTO typeCharacteristicDTO = new TypeCharacteristicDTO();
        typeCharacteristicDTO.setId(id);
        typeCharacteristicDTO.setType(type);

        return typeCharacteristicDTO;
    }
}