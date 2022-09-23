package com.example.sgswimming.services;

import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveDto;
import com.example.sgswimming.web.DTOs.update.LessonUpdateDto;
import com.example.sgswimming.web.mappers.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final InstructorRepository instructorRepository;
    private final SwimmerRepository swimmerRepository;

    private final LessonMapper mapper = LessonMapper.getInstance();

    @Override
    public List<LessonReadDto> findAll() {
        return null;
    }

    @Override
    public LessonReadDto findById(Long id) {
        return null;
    }

    @Override
    public LessonReadDto save(LessonSaveDto swimmerDTO) {
        return null;
    }

    @Override
    public LessonReadDto update(LessonUpdateDto swimmerDTO) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
