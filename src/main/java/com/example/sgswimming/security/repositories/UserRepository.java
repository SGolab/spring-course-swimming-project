package com.example.sgswimming.security.repositories;

import com.example.sgswimming.security.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph("user-graph")
    Optional<User> findByUsername(String username);
}