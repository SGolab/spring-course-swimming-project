package com.example.sgswimming.services;

import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveOrUpdateDto;

import java.util.List;

public interface LessonService {

    List<LessonReadDto> findAll();

    LessonReadDto findById(Long id);

    LessonReadDto save(LessonSaveOrUpdateDto swimmerDTO);

    LessonReadDto update(LessonSaveOrUpdateDto swimmerDTO);

    void deleteById(Long id);
}
