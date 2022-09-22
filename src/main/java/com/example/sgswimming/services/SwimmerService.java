package com.example.sgswimming.services;

import com.example.sgswimming.web.DTOs.read.SwimmerReadDto;
import com.example.sgswimming.web.DTOs.save.SwimmerSaveDto;
import com.example.sgswimming.web.DTOs.update.SwimmerUpdateDto;

import java.util.List;

public interface SwimmerService {

    List<SwimmerReadDto> findAll();

    SwimmerReadDto findById(Long id);

    SwimmerReadDto save(SwimmerSaveDto swimmerDTO);

    SwimmerReadDto update(SwimmerUpdateDto swimmerDTO);

    void deleteById(Long id);
}
