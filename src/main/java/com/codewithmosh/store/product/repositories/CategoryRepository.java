package com.codewithmosh.store.product.repositories;

import com.codewithmosh.store.product.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}