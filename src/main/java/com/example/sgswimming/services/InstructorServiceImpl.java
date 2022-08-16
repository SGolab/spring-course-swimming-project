package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.mappers.InstructorMapper;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.NotFoundException;
import com.example.sgswimming.repositories.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    private final InstructorMapper instructorMapper = InstructorMapper.INSTANCE;

    @Override
    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor findById(Long id) {
        return instructorRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public InstructorDTO saveOrUpdate(InstructorDTO instructorDTO) {
        Instructor savedInstructor = instructorRepository.save(instructorMapper.toInstructor(instructorDTO));
        return instructorMapper.toDto(savedInstructor);
    }
}
