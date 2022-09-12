package com.example.sgswimming.services;

import com.example.sgswimming.web.DTOs.SwimmerFatDto;
import com.example.sgswimming.web.DTOs.SwimmerSkinnyDto;

import java.util.List;

public interface SwimmerService {

    List<SwimmerFatDto> findAll();

    SwimmerFatDto findById(Long id);

    SwimmerFatDto saveOrUpdate(SwimmerSkinnyDto swimmerDTO);

    void deleteById(Long id);
}
