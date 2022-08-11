package com.example.swimmingproject.services;

import com.example.swimmingproject.model.Instructor;
import com.example.swimmingproject.model.Lesson;

import java.util.List;

public interface LessonService {

    List<Lesson> findAll();

    Lesson findById(Long id);
}
