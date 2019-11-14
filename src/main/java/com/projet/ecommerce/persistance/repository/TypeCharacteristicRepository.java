package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.TypeCharacteristic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Communication interface
 */

@Repository
public interface TypeCharacteristicRepository extends CrudRepository<TypeCharacteristic, Integer> {

    @Override
    List<TypeCharacteristic> findAll();
}
