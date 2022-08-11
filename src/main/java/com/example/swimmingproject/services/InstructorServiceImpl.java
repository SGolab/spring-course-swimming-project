package com.example.swimmingproject.services;

import com.example.swimmingproject.model.Instructor;
import com.example.swimmingproject.model.NotFoundException;
import com.example.swimmingproject.repositories.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    @Override
    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor findById(Long id) {
        return instructorRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
