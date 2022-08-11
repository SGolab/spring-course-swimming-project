package com.example.swimmingproject.repositories;

import com.example.swimmingproject.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
