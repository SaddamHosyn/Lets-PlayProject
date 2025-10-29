package com.example.user_product_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.user_product_api.dto.UserRegistrationRequest;
import com.example.user_product_api.dto.UserResponse;
import com.example.user_product_api.service.UserService;
import com.example.user_product_api.entity.Product;
import com.example.user_product_api.service.ProductService;


import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

   @Autowired
   private UserService userService;


    @Autowired
    private ProductService productService;

    @GetMapping
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<List<UserResponse>> getAllUsers() {
      List<UserResponse> users = userService.getAllUsers();
      return ResponseEntity.ok(users);
   }

   @GetMapping("/{id}")
   @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#id, authentication.name)")
   @PostAuthorize("hasRole('ADMIN') or returnObject.body.email == authentication.name")
   public ResponseEntity<UserResponse> getUserById(@PathVariable String id, Authentication authentication) {
      UserResponse user = userService.getUserById(id);
      return ResponseEntity.ok(user);
   }

   @PutMapping("/{id}")
   @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#id, authentication.name)")
   public ResponseEntity<UserResponse> updateUser(
         @PathVariable String id,
         @Valid @RequestBody UserRegistrationRequest request,
         Authentication authentication) {

      UserResponse updatedUser = userService.updateUser(id, request);
      return ResponseEntity.ok(updatedUser);
   }

   @DeleteMapping("/{id}")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<?> deleteUser(@PathVariable String id) {
      userService.deleteUser(id);
      Map<String, String> response = new HashMap<>();
      response.put("message", "User deleted successfully");
      return ResponseEntity.ok(response);
   }

    @GetMapping("/{id}/products")
    @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#id, authentication.name)")
    public ResponseEntity<List<Product>> getUserProducts(@PathVariable String id) {
        List<Product> products = productService.getProductsByUserId(id);
        return ResponseEntity.ok(products);
    }

}
