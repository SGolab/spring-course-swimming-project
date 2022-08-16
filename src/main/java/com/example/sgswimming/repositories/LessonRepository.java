package com.example.sgswimming.repositories;

import com.example.sgswimming.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
