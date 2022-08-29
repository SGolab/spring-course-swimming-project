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
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

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

    MockMvc mockMvc;

    @Mock
    SwimmerService swimmerService;

    @InjectMocks
    SwimmerController swimmerController;

    ObjectMapper objectMapper;
    UriBuilder uriBuilder;

    @BeforeEach
    void setUp() {
        uriBuilder = UriComponentsBuilder.fromUriString(SwimmerController.URL);
        objectMapper = JsonMapper.builder().findAndAddModules().build();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(swimmerController).build();
    }

    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";
    String ID = "1";

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

        mockMvc.perform(get(uriBuilder.build())
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

        mockMvc.perform(get(uriBuilder.path(ID).build())
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

        MockHttpServletRequestBuilder mockRequest = post(uriBuilder.build())
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

        MockHttpServletRequestBuilder mockRequest = put(uriBuilder.path(ID).build())
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
        mockMvc.perform(delete(uriBuilder.path(ID).build()))
                .andExpect(status().isOk());

        verify(swimmerService).deleteById(anyLong());
    }
}