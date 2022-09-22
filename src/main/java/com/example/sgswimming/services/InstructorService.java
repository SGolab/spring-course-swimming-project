package com.example.sgswimming.services;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.save.InstructorSaveDto;
import com.example.sgswimming.web.DTOs.update.InstructorUpdateDto;

import java.util.List;

public interface InstructorService {

    List<InstructorReadDto> findAll();

    List<InstructorReadDto> findAll(ClientData clientData);

    InstructorReadDto findById(Long id);

    InstructorReadDto findById(ClientData clientData, Long id);

    InstructorReadDto save(InstructorSaveDto dto);

    InstructorReadDto update(InstructorUpdateDto dto);

    void deleteById(Long id);
}
