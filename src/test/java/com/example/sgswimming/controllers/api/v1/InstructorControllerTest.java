package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.DTOs.InstructorFatDto;
import com.example.sgswimming.DTOs.InstructorSkinnyDto;
import com.example.sgswimming.DTOs.LessonSkinnyDto;
import com.example.sgswimming.services.InstructorService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

class InstructorControllerTest {

    MockMvc mockMvc;

    @Mock
    InstructorService instructorService;

    @InjectMocks
    InstructorController instructorController;

    ObjectMapper objectMapper;
    UriBuilder uriBuilder;

    @BeforeEach
    void setUp() {
        uriBuilder = UriComponentsBuilder.fromUriString(InstructorController.URL);
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(instructorController).build();
    }

    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";
    String ID = "1";

    InstructorFatDto INSTRUCTOR_FAT_DTO = InstructorFatDto.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .lesson(new LessonSkinnyDto())
            .lesson(new LessonSkinnyDto())
            .lesson(new LessonSkinnyDto())
            .build();

    InstructorSkinnyDto INSTRUCTOR_SKINNY_DTO = InstructorSkinnyDto.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .lessonIds(List.of(1L, 2L, 3L))
            .build();

    InstructorSkinnyDto INSTRUCTOR_SKINNY_DTO_MALFORMED = InstructorSkinnyDto.builder()
            .firstName("")
            .lastName("")
            .build();

    List<InstructorFatDto> instructors = List.of(
            INSTRUCTOR_FAT_DTO,
            new InstructorFatDto(),
            new InstructorFatDto());

    @Test
    void getAllInstructors() throws Exception {
        when(instructorService.findAll()).thenReturn(instructors);

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
        when(instructorService.findById(anyLong())).thenReturn(INSTRUCTOR_FAT_DTO);

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
        when(instructorService.saveOrUpdate(any())).thenReturn(INSTRUCTOR_FAT_DTO);

        MockHttpServletRequestBuilder mockRequest = post(uriBuilder.build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INSTRUCTOR_SKINNY_DTO));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.lessons", hasSize(3)));
    }

    @Test
    void saveNewInstructorMalformedJson() throws Exception {
        when(instructorService.saveOrUpdate(any())).thenReturn(INSTRUCTOR_FAT_DTO);

        MockHttpServletRequestBuilder mockRequest = post(uriBuilder.build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INSTRUCTOR_SKINNY_DTO_MALFORMED));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void processUpdateInstructor() throws Exception {
        when(instructorService.saveOrUpdate(any())).thenReturn(INSTRUCTOR_FAT_DTO);

        MockHttpServletRequestBuilder mockRequest = put(uriBuilder.path(ID).build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INSTRUCTOR_SKINNY_DTO));

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

        verify(instructorService).deleteById(anyLong());
    }
}