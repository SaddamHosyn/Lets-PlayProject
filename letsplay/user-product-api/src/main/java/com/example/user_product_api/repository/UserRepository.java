package com.example.user_product_api.repository;

import com.example.user_product_api.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    //optional helps to avoid null pointer exceptions, which will avoid crash application.
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
