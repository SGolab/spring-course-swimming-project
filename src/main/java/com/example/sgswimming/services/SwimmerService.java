package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.SwimmerDTO;

import java.util.List;

public interface SwimmerService {

    List<SwimmerDTO> findAll();

    SwimmerDTO findById(Long id);
}
