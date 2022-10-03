package com.example.sgswimming.repositories;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @EntityGraph("instructor-read-dto")
    @Override
    List<Instructor> findAll();

    @EntityGraph("instructor-read-dto")
    @Override
    Optional<Instructor> findById(Long aLong);

    @EntityGraph("instructor-read-dto")
    @Override
    List<Instructor> findAllById(Iterable<Long> longs);
}
