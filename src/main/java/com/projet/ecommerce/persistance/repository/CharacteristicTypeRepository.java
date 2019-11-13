package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.CharacteristicType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Communication interface
 */

@Repository
public interface CharacteristicTypeRepository extends CrudRepository<CharacteristicType, String> {

}
