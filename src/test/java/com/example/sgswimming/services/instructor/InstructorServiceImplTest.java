package com.example.sgswimming.services.instructor;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.exceptions.NotFoundException;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.services.InstructorServiceImpl;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.save.InstructorSaveDto;
import com.example.sgswimming.web.DTOs.update.InstructorUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class InstructorServiceImplTest {

    @Mock
    InstructorRepository repository;

    @Mock
    LessonRepository lessonRepository;

    @InjectMocks
    InstructorServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(new Instructor(), new Instructor()));

        List<InstructorReadDto> instructors = service.findAll();

        assertNotNull(instructors);
        assertEquals(2, instructors.size());
    }

    @Test
    void findById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Instructor()));

        InstructorReadDto instructor = service.findById(1L);

        assertNotNull(instructor);
    }

    @Test()
    void findByIdNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                NotFoundException.class,
                () -> service.findById(1L));
    }

    @Test
    void save() {
        InstructorSaveDto instructorDTO = new InstructorSaveDto();
        Instructor instructor = new Instructor();

        when(repository.save(any())).thenReturn(instructor);

        InstructorReadDto foundInstructor = service.save(instructorDTO);

        assertNotNull(foundInstructor);
        verify(repository).save(any(Instructor.class));
        verify(repository, never()).findById(anyLong());
    }

    @Test
    void update() {
        InstructorUpdateDto instructorDTO = new InstructorUpdateDto();
        instructorDTO.setId(1L);

        Instructor instructor = new Instructor();

        when(repository.findById(anyLong())).thenReturn(Optional.of(instructor));
        when(repository.save(any())).thenReturn(instructor);

        InstructorReadDto foundInstructor = service.update(instructorDTO);

        assertNotNull(foundInstructor);
        verify(repository).save(any(Instructor.class));
        verify(repository).findById(anyLong());
    }

    @Test
    void updateIllegalArgument() {
        InstructorUpdateDto instructorDTO = new InstructorUpdateDto();

        assertThrows(IllegalArgumentException.class, () -> service.update(instructorDTO));
    }

    @Test
    void updateNotFound() {
        InstructorUpdateDto instructorDTO = new InstructorUpdateDto();
        instructorDTO.setId(1L);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(instructorDTO));
    }


    @Test
    void deleteById() {
        Long id = 1L;

        when(repository.findById(anyLong())).thenReturn(Optional.of(new Instructor()));

        service.deleteById(id);

        verify(repository).deleteById(anyLong());
        verify(lessonRepository).saveAll(any());
    }
}