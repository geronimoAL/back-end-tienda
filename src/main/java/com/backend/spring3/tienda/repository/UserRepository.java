package com.backend.spring3.tienda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.spring3.tienda.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByName(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    // Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByName(String username);

}
