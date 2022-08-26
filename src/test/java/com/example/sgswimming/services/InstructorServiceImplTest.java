package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.InstructorDTO;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.InstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

        List<InstructorDTO> instructors = instructorService.findAll();

        assertNotNull(instructors);
        assertEquals(2, instructors.size());
    }

    @Test
    void findById() {
        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(new Instructor()));

        InstructorDTO instructor = instructorService.findById(1L);

        assertNotNull(instructor);
    }

    @Test()
    void findByIdNotFound() {
        when(instructorRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> instructorService.findById(1L));
    }

    @Test
    void save() {
        InstructorDTO instructorDTO = new InstructorDTO();
        Instructor instructor = new Instructor();

        when(instructorRepository.save(any())).thenReturn(instructor);

        InstructorDTO foundInstructor = instructorService.saveOrUpdate(instructorDTO);

        assertNotNull(foundInstructor);
        verify(instructorRepository).save(any(Instructor.class));
        verify(instructorRepository, never()).findById(anyLong());
    }

    @Test
    void update() {
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setId(1L);

        Instructor instructor = new Instructor();

        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(instructor));
        when(instructorRepository.save(any())).thenReturn(instructor);

        InstructorDTO foundInstructor = instructorService.saveOrUpdate(instructorDTO);

        assertNotNull(foundInstructor);
        verify(instructorRepository).save(any(Instructor.class));
        verify(instructorRepository).findById(anyLong());
    }

    @Test
    void updateNotFound() {
        InstructorDTO instructorDTO = new InstructorDTO();

        when(instructorRepository.findById(anyLong())).thenReturn(Optional.empty());

        instructorService.saveOrUpdate(instructorDTO);
        assertThrows(NotFoundException.class, () -> instructorService.findById(1L));
    }

    @Test
    void deleteById() {

        Long id = 1L;

        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(new Instructor()));

        instructorService.deleteById(id);

        verify(instructorRepository).deleteById(anyLong());
    }
}