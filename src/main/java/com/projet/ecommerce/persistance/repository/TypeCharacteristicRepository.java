package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.TypeCharacteristic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Communication interface
 */

@Repository
public interface TypeCharacteristicRepository extends CrudRepository<TypeCharacteristic, String> {

}
