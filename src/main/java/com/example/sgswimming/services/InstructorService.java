package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.InstructorDTO;

import java.util.List;

public interface InstructorService {

    List<InstructorDTO> findAll();

    InstructorDTO findById(Long id);

    InstructorDTO saveOrUpdate(InstructorDTO instructorDTO);

    void deleteById(Long id);
}
