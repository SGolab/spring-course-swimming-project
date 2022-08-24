package com.example.sgswimming.controllers;

import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.controllers.ui.SwimmerController;
import com.example.sgswimming.services.SwimmerService;
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

class SwimmerControllerTest {

    @Mock
    SwimmerService swimmerService;

    @InjectMocks
    SwimmerController swimmerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(swimmerController).build();
    }

    @Test
    void getAllSwimmers() throws Exception {
        when(swimmerService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/swimmers/"))
                .andExpect(status().isOk())
                .andExpect(view().name("swimmers/list"))
                .andExpect(model().attributeExists("swimmers"));
    }

    @Test
    void getSwimmerById() throws Exception {
        when(swimmerService.findById(anyLong())).thenReturn(new SwimmerDTO());

        mockMvc.perform(get("/swimmers/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("swimmers/show"))
                .andExpect(model().attributeExists("swimmer"));
    }
}