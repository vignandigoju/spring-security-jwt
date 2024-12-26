package com.example.spring_security_jwt.repository;

import com.example.spring_security_jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
