package com.example.sgswimming.services;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.NotFoundException;
import com.example.sgswimming.repositories.LessonRepository;
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

class LessonServiceImplTest {

    @Mock
    LessonRepository lessonRepository;

    @InjectMocks
    LessonServiceImpl lessonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        when(lessonRepository.findAll()).thenReturn(List.of(new Lesson(), new Lesson()));

        List<LessonDTO> Lessons = lessonService.findAll();

        assertNotNull(Lessons);
        assertEquals(2, Lessons.size());
    }

    @Test
    void findById() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(new Lesson()));

        LessonDTO lesson = lessonService.findById(1L);

        assertNotNull(lesson);
    }

    @Test()
    void findByIdNotFound() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> lessonService.findById(1L));
    }
}