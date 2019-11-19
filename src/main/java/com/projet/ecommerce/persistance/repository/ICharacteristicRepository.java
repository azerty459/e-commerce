package com.projet.ecommerce.persistance.repository;

import com.projet.ecommerce.persistance.entity.Characteristic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Communication interface
 */

@Repository
public interface ICharacteristicRepository extends CrudRepository<Characteristic, String> {

}
