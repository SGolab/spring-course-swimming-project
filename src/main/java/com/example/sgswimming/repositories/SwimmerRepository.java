package com.example.sgswimming.repositories;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SwimmerRepository extends JpaRepository<Swimmer, Long> {

    @EntityGraph("swimmer-read-dto")
    @Override
    List<Swimmer> findAll();

    @EntityGraph("swimmer-read-dto")
    @Override
    Optional<Swimmer> findById(Long aLong);

    @EntityGraph("swimmer-read-dto")
    @Override
    List<Swimmer> findAllById(Iterable<Long> longs);
}
