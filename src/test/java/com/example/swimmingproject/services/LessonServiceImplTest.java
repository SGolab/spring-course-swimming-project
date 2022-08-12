package com.example.swimmingproject.services;

import com.example.swimmingproject.model.Lesson;
import com.example.swimmingproject.repositories.LessonRepository;
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
    LessonRepository LessonRepository;

    @InjectMocks
    LessonServiceImpl LessonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        when(LessonRepository.findAll()).thenReturn(List.of(new Lesson(), new Lesson()));

        List<Lesson> Lessons = LessonService.findAll();

        assertNotNull(Lessons);
        assertEquals(2, Lessons.size());
    }

    @Test
    void findById() {
        when(LessonRepository.findById(anyLong())).thenReturn(Optional.of(new Lesson()));

        Lesson lesson = LessonService.findById(1L);

        assertNotNull(lesson);
    }
}