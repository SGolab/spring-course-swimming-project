package com.example.sgswimming.repositories;

import com.example.sgswimming.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Set<Lesson> findAllByInstructorId(Long id);
    Set<Lesson> findAllBySwimmersId(Long id);
}
