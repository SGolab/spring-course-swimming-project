package com.example.sgswimming.services;

import com.example.sgswimming.model.Swimmer;

import java.util.List;

public interface SwimmerService {

    List<Swimmer> findAll();

    Swimmer findById(Long id);
}
