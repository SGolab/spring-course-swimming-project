package com.example.swimmingproject.services;

import com.example.swimmingproject.model.Instructor;
import com.example.swimmingproject.model.NotFoundException;
import com.example.swimmingproject.repositories.InstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class InstructorServiceImplTest {

    @Mock
    InstructorRepository instructorRepository;

    @InjectMocks
    InstructorServiceImpl instructorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        when(instructorRepository.findAll()).thenReturn(List.of(new Instructor(), new Instructor()));

        List<Instructor> instructors = instructorService.findAll();

        assertNotNull(instructors);
        assertEquals(2, instructors.size());
    }

    @Test
    void findById() {
        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(new Instructor()));

        Instructor instructor = instructorService.findById(1L);

        assertNotNull(instructor);
    }

    @Test()
    void findByIdNotFound() {
        when(instructorRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> instructorService.findById(1L));
    }
}