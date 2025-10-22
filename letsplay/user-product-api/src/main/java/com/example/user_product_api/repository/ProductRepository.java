package com.example.user_product_api.repository;

import com.example.user_product_api.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByUserId(String userId);

    // ignore case search and regex automatically handled by Spring Data MongoDB
    List<Product> findByNameContainingIgnoreCase(String name);
}
