package com.codewithmosh.store.user.repositories;

import com.codewithmosh.store.user.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}