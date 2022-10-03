package com.example.sgswimming.repositories;

import com.example.sgswimming.model.ClientData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientDataRepository extends JpaRepository<ClientData, Long> {

    @EntityGraph("client_data-instructor-user")
    @Query("SELECT c FROM ClientData c WHERE c.id = ?1")
    Optional<ClientData> findByIdForInstructorUser(Long aLong);

    @EntityGraph("client_data-swimmer-user")
    @Query("SELECT c FROM ClientData c WHERE c.id = ?1")
    Optional<ClientData> findByIdForSwimmerUser(Long aLong);

    ClientData findByFirstName(String firstName);
}
