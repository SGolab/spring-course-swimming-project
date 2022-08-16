package com.example.sgswimming.services;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;

import java.util.List;

public interface LessonService {

    List<Lesson> findAll();

    Lesson findById(Long id);
}
