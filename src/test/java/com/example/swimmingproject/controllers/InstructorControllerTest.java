package com.example.swimmingproject.controllers;

import com.example.swimmingproject.model.Instructor;
import com.example.swimmingproject.services.InstructorService;
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

class InstructorControllerTest {

    @Mock
    InstructorService instructorService;

    @InjectMocks
    InstructorController instructorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(instructorController).build();
    }

    @Test
    void getAllInstructors() throws Exception {
        when(instructorService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/instructors/"))
                .andExpect(status().isOk())
                .andExpect(view().name("instructors/list"))
                .andExpect(model().attributeExists("instructors"));
    }

    @Test
    void getInstructorById() throws Exception {
        when(instructorService.findById(anyLong())).thenReturn(new Instructor());

        mockMvc.perform(get("/instructors/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("instructors/show"))
                .andExpect(model().attributeExists("instructor"));
    }
}