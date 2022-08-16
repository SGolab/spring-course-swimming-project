package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Swimmer;

import java.util.List;

public interface InstructorService {

    List<Instructor> findAll();

    Instructor findById(Long id);

    InstructorDTO saveOrUpdate(InstructorDTO instructorDTO);
}
