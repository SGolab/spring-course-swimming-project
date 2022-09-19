package com.example.sgswimming.repositories;

import com.example.sgswimming.model.ClientData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDataRepository extends JpaRepository<ClientData, Long> {
}
