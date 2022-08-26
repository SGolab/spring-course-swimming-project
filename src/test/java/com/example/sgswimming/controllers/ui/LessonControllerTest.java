package com.example.sgswimming.controllers.ui;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.controllers.ui.LessonController;
import com.example.sgswimming.services.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

class LessonControllerTest {

    @Mock
    LessonService lessonService;

    @InjectMocks
    LessonController lessonController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lessonController).build();
    }

    @Test
    void getAllLessons() throws Exception {
        when(lessonService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/lessons/"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons/list"))
                .andExpect(model().attributeExists("lessons"));
    }

    @Test
    void getLessonById() throws Exception {
        when(lessonService.findById(anyLong())).thenReturn(new LessonDTO());

        mockMvc.perform(get("/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons/show"))
                .andExpect(model().attributeExists("lesson"));
    }
}