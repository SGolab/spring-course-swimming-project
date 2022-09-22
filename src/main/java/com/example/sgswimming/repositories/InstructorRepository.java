package com.example.sgswimming.repositories;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findAllByClientDataSet(ClientData clientData);

    Optional<Instructor> findByIdAndClientDataSet(Long id, ClientData clientData);
}
