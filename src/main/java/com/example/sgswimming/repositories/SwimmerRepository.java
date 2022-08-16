package com.example.sgswimming.repositories;

import com.example.sgswimming.model.Swimmer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SwimmerRepository extends JpaRepository<Swimmer, Long> {
}
