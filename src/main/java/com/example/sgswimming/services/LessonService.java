package com.example.sgswimming.services;

import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveDto;
import com.example.sgswimming.web.DTOs.update.LessonUpdateDto;

import java.util.List;

public interface LessonService {

    List<LessonReadDto> findAll();

    LessonReadDto findById(Long id);

    LessonReadDto save(LessonSaveDto swimmerDTO);

    LessonReadDto update(LessonUpdateDto swimmerDTO);

    void deleteById(Long id);
}
