package com.example.sgswimming.services;

import com.example.sgswimming.web.DTOs.LessonFatDto;
import com.example.sgswimming.web.DTOs.LessonSkinnyDto;

import java.util.List;

public interface LessonService {

    List<LessonFatDto> findAll();

    LessonFatDto findById(Long id);

    LessonFatDto saveOrUpdate(LessonSkinnyDto lessonDTO);

    void deleteById(Long id);
}
