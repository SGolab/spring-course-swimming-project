package com.example.swimmingproject.repositories;

import com.example.swimmingproject.model.Swimmer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SwimmerRepository extends JpaRepository<Swimmer, Long> {
}
