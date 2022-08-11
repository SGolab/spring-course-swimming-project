package com.example.swimmingproject.services;

import com.example.swimmingproject.model.Lesson;
import com.example.swimmingproject.model.NotFoundException;
import com.example.swimmingproject.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService{

    private final LessonRepository lessonRepository;

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
