package com.example.sgswimming.repositories;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @EntityGraph(value = "lesson.dto.read")
    @Override
    List<Lesson> findAll();

    Set<Lesson> findAllByInstructorId(Long id);

    Set<Lesson> findAllBySwimmersId(Long id);

    Set<Lesson> findAllByInstructor(Instructor instructor);

    Set<Lesson> findAllBySwimmers(Swimmer swimmer);

    Optional<Lesson> findByIdAndSwimmers(Long id, Swimmer swimmer);

    Optional<Lesson> findByIdAndInstructor(Long id, Instructor instructor);
}
