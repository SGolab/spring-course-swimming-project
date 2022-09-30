package com.example.sgswimming.repositories;

import com.example.sgswimming.model.ClientData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientDataRepository extends JpaRepository<ClientData, Long> {

    @EntityGraph("client_data-operations")
    @Override
    Optional<ClientData> findById(Long aLong);

    ClientData findByFirstName(String firstName);
}
