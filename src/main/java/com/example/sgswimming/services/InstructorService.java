package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.InstructorFatDto;
import com.example.sgswimming.DTOs.InstructorSkinnyDto;

import java.util.List;

public interface InstructorService {

    List<InstructorFatDto> findAll();

    InstructorFatDto findById(Long id);

    InstructorFatDto saveOrUpdate(InstructorSkinnyDto instructorDTO);

    void deleteById(Long id);
}
