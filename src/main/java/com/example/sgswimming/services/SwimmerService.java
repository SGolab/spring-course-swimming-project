package com.example.sgswimming.services;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import com.example.sgswimming.web.DTOs.save.SwimmerSaveDto;
import com.example.sgswimming.web.DTOs.update.SwimmerUpdateDto;

import java.util.List;

public interface SwimmerService {

    List<SwimmerReadDto> findAll();

    List<SwimmerReadDto> findAll(ClientData clientData);

    SwimmerReadDto findById(Long id);

    SwimmerReadDto findById(Long id, ClientData clientData);

    SwimmerReadDto save(SwimmerSaveDto swimmerDTO);

    SwimmerReadDto update(SwimmerUpdateDto swimmerDTO);

    void deleteById(Long id);
}
