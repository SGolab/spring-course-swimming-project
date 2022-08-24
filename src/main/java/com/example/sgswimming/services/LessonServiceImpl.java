package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.mappers.LessonMapper;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService{

    private final LessonRepository lessonRepository;
    private final LessonMapper mapper = LessonMapper.INSTANCE;

    @Override
    public List<LessonDTO> findAll() {
        return lessonRepository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LessonDTO findById(Long id) {
        return mapper.toDto(lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Lesson.class)));
    }
}
