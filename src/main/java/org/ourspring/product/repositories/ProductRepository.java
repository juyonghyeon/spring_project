package org.ourspring.product.repositories;

import org.ourspring.product.entities.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Long> {
}
