package com.example.sgswimming.repositories;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface SwimmerRepository extends JpaRepository<Swimmer, Long> {
    Set<Swimmer> findAllByClientDataSet(ClientData clientData);

    Optional<Swimmer> findByIdAndClientDataSet(Long id, ClientData clientData);
}
