package com.backend.spring3.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.spring3.tienda.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
    Role findByName(String name);
}
