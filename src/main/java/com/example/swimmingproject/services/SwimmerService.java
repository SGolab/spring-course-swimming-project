package com.example.swimmingproject.services;

import com.example.swimmingproject.model.Swimmer;

import java.util.List;

public interface SwimmerService {

    List<Swimmer> findAll();

    Swimmer findById(Long id);
}
