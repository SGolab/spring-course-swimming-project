package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.LessonDTO;

import java.util.List;

public interface LessonService {

    List<LessonDTO> findAll();

    LessonDTO findById(Long id);

    LessonDTO saveOrUpdate(LessonDTO lessonDTO);

    void deleteById(Long id);
}
