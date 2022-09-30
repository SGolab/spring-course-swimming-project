package com.example.sgswimming.services;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveDto;
import com.example.sgswimming.web.DTOs.update.LessonUpdateDto;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface LessonService {

    List<LessonReadDto> findAll();

    List<LessonReadDto> findAll(ClientData clientData);

    LessonReadDto findById(Long id);

    LessonReadDto findById(ClientData clientData, Long id);

    LessonReadDto save(LessonSaveDto swimmerDTO);

    LessonReadDto update(LessonUpdateDto swimmerDTO);

    void deleteById(Long id);
}
