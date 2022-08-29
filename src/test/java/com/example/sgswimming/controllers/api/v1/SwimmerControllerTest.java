package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.LessonDTO;
import com.example.sgswimming.DTOs.SwimmerDTO;
import com.example.sgswimming.services.SwimmerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SwimmerControllerTest {

    @Mock
    SwimmerService swimmerService;

    @InjectMocks
    SwimmerController swimmerController;

    MockMvc mockMvc;

    ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(swimmerController).build();
    }

    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";

    SwimmerDTO SWIMMER = SwimmerDTO.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .lesson(new LessonDTO.Skinny())
            .lesson(new LessonDTO.Skinny())
            .lesson(new LessonDTO.Skinny())
            .build();

    List<SwimmerDTO> swimmers = List.of(
            SWIMMER,
            new SwimmerDTO(),
            new SwimmerDTO());

    @Test
    void getAllInstructors() throws Exception {
        when(swimmerService.findAll()).thenReturn(swimmers);

        mockMvc.perform(get("/api/v1/swimmers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$[0].lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$[0].lessons", hasSize(3)));
    }

    @Test
    void getInstructorById() throws Exception {
        when(swimmerService.findById(anyLong())).thenReturn(SWIMMER);

        mockMvc.perform(get("/api/v1/swimmers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.lessons", hasSize(3)));
    }

    @Test
    void saveNewInstructor() throws Exception {
        when(swimmerService.saveOrUpdate(any())).thenReturn(SWIMMER);

        MockHttpServletRequestBuilder mockRequest = post("/api/v1/swimmers/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SWIMMER));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.lessons", hasSize(3)));
    }

    @Test
    void processUpdateInstructor() throws Exception {
        when(swimmerService.saveOrUpdate(any())).thenReturn(SWIMMER);

        MockHttpServletRequestBuilder mockRequest = put("/api/v1/swimmers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SWIMMER));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.lessons", hasSize(3)));
    }

    @Test
    void deleteInstructorById() throws Exception {
        mockMvc.perform(delete("/api/v1/swimmers/1"))
                .andExpect(status().isOk());

        verify(swimmerService).deleteById(anyLong());
    }
}