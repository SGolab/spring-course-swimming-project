package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.mappers.InstructorMapper;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.controllers.exceptions.NotFoundException;
import com.example.sgswimming.repositories.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper = InstructorMapper.INSTANCE;

    @Override
    public List<InstructorDTO> findAll() {
        return instructorRepository
                .findAll()
                .stream()
                .map(instructorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InstructorDTO findById(Long id) {
        return instructorMapper.toDto(instructorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Instructor.class)));
    }

    @Override
    public InstructorDTO saveOrUpdate(InstructorDTO instructorDTO) {
        Instructor savedInstructor = instructorRepository.save(instructorMapper.toInstructor(instructorDTO));
        return instructorMapper.toDto(savedInstructor);
    }

    @Override
    public void deleteById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Instructor.class));

        instructor.getLessons().forEach(lesson -> lesson.setInstructor(null));
        instructorRepository.deleteById(id);
    }
}
