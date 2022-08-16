package com.example.sgswimming.services;

import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.NotFoundException;
import com.example.sgswimming.repositories.LessonRepository;
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
