package com.codewithmosh.store.user.repositories;

import com.codewithmosh.store.user.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}