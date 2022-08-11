package com.example.swimmingproject.services;

import com.example.swimmingproject.model.Instructor;
import com.example.swimmingproject.model.Swimmer;

import java.util.List;

public interface InstructorService {

    List<Instructor> findAll();

    Instructor findById(Long id);
}
