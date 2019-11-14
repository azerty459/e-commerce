package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.TypeCharacteristicDTO;
import com.projet.ecommerce.persistance.entity.TypeCharacteristic;

import java.util.List;

/**
 * Characteristic types interface
 */
public interface ITypeCharacteristicBusiness {

    /**
     * Method defining the way to get all types
     * @return
     */
    List<TypeCharacteristicDTO> getAll();

    TypeCharacteristic add(TypeCharacteristicDTO typeCharacteristicDTO);

    void delete(TypeCharacteristicDTO typeCharacteristicDTO);

    TypeCharacteristic update(TypeCharacteristicDTO typeCharacteristicDTO);
}
