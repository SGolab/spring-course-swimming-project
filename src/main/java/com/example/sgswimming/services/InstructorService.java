package com.example.sgswimming.services;

import com.example.sgswimming.web.DTOs.InstructorFatDto;
import com.example.sgswimming.web.DTOs.InstructorSkinnyDto;

import java.util.List;

public interface InstructorService {

    List<InstructorFatDto> findAll();

    List<InstructorFatDto> findAll(Long clientDataId);

    InstructorFatDto findById(Long id);

    InstructorFatDto findById(Long clientDataId, Long instructorId);

    InstructorFatDto saveOrUpdate(InstructorSkinnyDto instructorDTO);

    InstructorFatDto saveOrUpdate(Long clientDataId, InstructorSkinnyDto instructorDTO);

    void deleteById(Long id);

    void deleteById(Long clientDataId, Long id);
}
