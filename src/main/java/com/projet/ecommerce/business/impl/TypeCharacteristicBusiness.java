package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ITypeCharacteristicBusiness;
import com.projet.ecommerce.business.dto.TypeCharacteristicDTO;
import com.projet.ecommerce.business.dto.transformer.TypeCharacteristicTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.TypeCharacteristic;
import com.projet.ecommerce.persistance.repository.ITypeCharacteristicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeCharacteristicBusiness implements ITypeCharacteristicBusiness {

    @Autowired
    private ITypeCharacteristicRepository ITypeCharacteristicRepository;

    /**
     * Method defining the way to get all types
     *
     * @return
     */
    public List<TypeCharacteristicDTO> getAll() {
        return new ArrayList<>(TypeCharacteristicTransformer.listEntityToDto(ITypeCharacteristicRepository.findAll()));
    }

    /**
     * Method defining the way to add a type
     *
     * @param typeCharacteristicDTO
     * @return
     */
    public TypeCharacteristic add(TypeCharacteristicDTO typeCharacteristicDTO) {
        TypeCharacteristic typeCharacteristic = ITypeCharacteristicRepository.findById(typeCharacteristicDTO.getId())
                .orElse(null);
        if (typeCharacteristicDTO == null || typeCharacteristic != null) {
            throw new GraphQLCustomException("Either parameter is null or is already exist");
        } else {
            return ITypeCharacteristicRepository.save(typeCharacteristic);
        }
    }

    /**
     * Method defining the way to delete a type
     *
     * @param typeCharacteristicDTO
     */
    public void delete(TypeCharacteristicDTO typeCharacteristicDTO) {
        //Avoid nullPointer by getting the object or the null value
        TypeCharacteristic typeCharacteristic = ITypeCharacteristicRepository.findById(typeCharacteristicDTO.getId())
                .orElse(null);
        if (typeCharacteristicDTO == null || typeCharacteristic == null) {
            throw new GraphQLCustomException("Either parameter is null or is not found");
        } else {
            ITypeCharacteristicRepository.delete(typeCharacteristic);
        }
    }

    /**
     * Method defining the way to modify a type
     *
     * @param typeCharacteristicDTO
     * @return
     */
    public TypeCharacteristic update(TypeCharacteristicDTO typeCharacteristicDTO) {
        TypeCharacteristic typeCharacteristic = ITypeCharacteristicRepository.findById(typeCharacteristicDTO.getId())
                .orElse(null);

        if (typeCharacteristicDTO == null || typeCharacteristic == null) {
            throw new GraphQLCustomException("Either parameter is null or is not found");
        } else {
            return ITypeCharacteristicRepository.save(TypeCharacteristicTransformer.dtoToEntity(typeCharacteristicDTO));
        }
    }
}
