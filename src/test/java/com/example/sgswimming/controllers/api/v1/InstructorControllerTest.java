package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.services.InstructorService;
import com.example.sgswimming.web.DTOs.read.InstructorReadDto;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.InstructorSaveDto;
import com.example.sgswimming.web.DTOs.update.InstructorUpdateDto;
import com.example.sgswimming.web.controllers.api.v1.InstructorController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
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

    String FIRST_NAME = "John";
    String LAST_NAME = "Kowalski";
    String ID = "1";
    String BLANK = "";

    InstructorReadDto INSTRUCTOR_READ_DTO;

    InstructorSaveDto INSTRUCTOR_SAVE_DTO;
    InstructorSaveDto INSTRUCTOR_SKINNY_DTO_MALFORMED;

    InstructorUpdateDto INSTRUCTOR_UPDATE_DTO;
    InstructorUpdateDto INSTRUCTOR_UPDATE_DTO_MALFORMED;

    List<InstructorReadDto> instructors;

    @BeforeEach
    void setUp() {
        uriBuilder = UriComponentsBuilder.fromUriString(InstructorController.URL);
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(instructorController).build();

        Set<LessonReadDto> lessonReadDtoSet = Set.of(new LessonReadDto());

        INSTRUCTOR_READ_DTO = new InstructorReadDto();
        INSTRUCTOR_READ_DTO.setFirstName(FIRST_NAME);
        INSTRUCTOR_READ_DTO.setLastName(LAST_NAME);
        INSTRUCTOR_READ_DTO.setLessons(lessonReadDtoSet);

        INSTRUCTOR_SAVE_DTO = new InstructorSaveDto();
        INSTRUCTOR_SAVE_DTO.setFirstName(FIRST_NAME);
        INSTRUCTOR_SAVE_DTO.setLastName(LAST_NAME);

        INSTRUCTOR_SKINNY_DTO_MALFORMED = new InstructorSaveDto();
        INSTRUCTOR_SKINNY_DTO_MALFORMED.setFirstName(BLANK);
        INSTRUCTOR_SKINNY_DTO_MALFORMED.setLastName(BLANK);

        INSTRUCTOR_UPDATE_DTO = new InstructorUpdateDto();
        INSTRUCTOR_UPDATE_DTO.setId(1L);
        INSTRUCTOR_UPDATE_DTO.setFirstName(FIRST_NAME);
        INSTRUCTOR_UPDATE_DTO.setLastName(LAST_NAME);
        INSTRUCTOR_UPDATE_DTO.setLessons(Set.of(1L, 2L, 3L));

        INSTRUCTOR_UPDATE_DTO_MALFORMED = new InstructorUpdateDto();
        INSTRUCTOR_UPDATE_DTO_MALFORMED.setId(null);
        INSTRUCTOR_UPDATE_DTO_MALFORMED.setFirstName(BLANK);
        INSTRUCTOR_UPDATE_DTO_MALFORMED.setLastName(BLANK);
        INSTRUCTOR_UPDATE_DTO_MALFORMED.setLessons(Set.of(1L));

        instructors = List.of(
                INSTRUCTOR_READ_DTO,
                new InstructorReadDto(),
                new InstructorReadDto());
    }

    @Test
    void getAll() throws Exception {
        when(instructorService.findAll()).thenReturn(instructors);

        mockMvc.perform(get(uriBuilder.build())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$[0].lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$[0].lessons", hasSize(1)));
    }

    @Test
    void getById() throws Exception {
        when(instructorService.findById(anyLong())).thenReturn(INSTRUCTOR_READ_DTO);

        mockMvc.perform(get(uriBuilder.path(ID).build())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.lessons", hasSize(1)));
    }

    @Test
    void save() throws Exception {
        when(instructorService.save(any())).thenReturn(INSTRUCTOR_READ_DTO);

        MockHttpServletRequestBuilder mockRequest = post(uriBuilder.build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INSTRUCTOR_SAVE_DTO));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.lessons", hasSize(1)));
    }

    @Test
    void saveMalformedJson() throws Exception {
        MockHttpServletRequestBuilder mockRequest = post(uriBuilder.build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INSTRUCTOR_SKINNY_DTO_MALFORMED));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()));

        verify(instructorService, never()).save(any());
    }

    @Test
    void update() throws Exception {
        when(instructorService.update(any())).thenReturn(INSTRUCTOR_READ_DTO);

        MockHttpServletRequestBuilder mockRequest = put(uriBuilder.path(ID).build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INSTRUCTOR_UPDATE_DTO));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.lessons", hasSize(1)));
    }

    @Test
    void updateMalformedJson() throws Exception {
        when(instructorService.update(any())).thenReturn(INSTRUCTOR_READ_DTO);

        MockHttpServletRequestBuilder mockRequest = put(uriBuilder.path(ID).build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INSTRUCTOR_UPDATE_DTO_MALFORMED));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()));

        verify(instructorService, never()).update(any());
    }

    @Test
    void deleteInstructorById() throws Exception {
        mockMvc.perform(delete(uriBuilder.path(ID).build()))
                .andExpect(status().isOk());

        verify(instructorService).deleteById(anyLong());
    }
}