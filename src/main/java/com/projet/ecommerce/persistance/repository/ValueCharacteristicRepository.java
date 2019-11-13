package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.ValueCharacteristic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Communication interface
 */

@Repository
public interface ValueCharacteristicRepository extends CrudRepository<ValueCharacteristic, String> {

}
