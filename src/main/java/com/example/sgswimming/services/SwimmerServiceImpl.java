package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.mappers.SwimmerMapper;
import com.example.sgswimming.model.NotFoundException;
import com.example.sgswimming.repositories.SwimmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SwimmerServiceImpl implements SwimmerService{

    private final SwimmerRepository swimmerRepository;
    private final SwimmerMapper mapper = SwimmerMapper.INSTANCE;

    @Override
    public List<SwimmerDTO> findAll() {
        return swimmerRepository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SwimmerDTO findById(Long id) {
        return mapper.toDto(swimmerRepository.findById(id).orElseThrow(NotFoundException::new));
    }
}
