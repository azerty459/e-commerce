package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.CharacteristicValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Communication interface
 */

@Repository
public interface CharacteristicValueRepository extends CrudRepository<CharacteristicValue, String> {

}
