package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.LessonFatDto;
import com.example.sgswimming.DTOs.LessonSkinnyDto;

import java.util.List;

public interface LessonService {

    List<LessonFatDto> findAll();

    LessonFatDto findById(Long id);

    LessonFatDto saveOrUpdate(LessonSkinnyDto lessonDTO);

    void deleteById(Long id);
}
