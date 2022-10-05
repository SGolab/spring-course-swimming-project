package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.services.LessonService;
import com.example.sgswimming.web.DTOs.read.LessonReadDto;
import com.example.sgswimming.web.DTOs.save.LessonSaveDto;
import com.example.sgswimming.web.DTOs.update.LessonUpdateDto;
import com.example.sgswimming.web.config.JsonDateMappingConfig;
import com.example.sgswimming.web.controllers.api.v1.LessonController;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LessonControllerTest {

    MockMvc mockMvc;

    @Mock
    LessonService lessonService;

    @InjectMocks
    LessonController lessonController;

    ObjectMapper objectMapper;
    UriBuilder uriBuilder;

    String BLANK = "";
    String ID = "1";
    String DESCRIPTION = "Desc";
    LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    String LOCAL_DATE_TIME_STRING = LOCAL_DATE_TIME.format(DateTimeFormatter.ofPattern(JsonDateMappingConfig.DATE_TIME_FORMAT));

    LessonReadDto LESSON_READ_DTO;

    LessonSaveDto LESSON_SAVE_DTO;
    LessonSaveDto LESSON_SAVE_DTO_MALFORMED;

    LessonUpdateDto LESSON_UPDATE_DTO;
    LessonUpdateDto LESSON_UPDATE_DTO_MALFORMED;

    List<LessonReadDto> lessons;

    @BeforeEach
    void setUp() {
        uriBuilder = UriComponentsBuilder.fromUriString(LessonController.URL);
        objectMapper = JsonMapper.builder().findAndAddModules().build();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lessonController).build();

        LESSON_READ_DTO = new LessonReadDto();
        LESSON_READ_DTO.setDescription(DESCRIPTION);
        LESSON_READ_DTO.setLocalDateTime(LOCAL_DATE_TIME_STRING);

        LESSON_SAVE_DTO = new LessonSaveDto();
        LESSON_SAVE_DTO.setDescription(DESCRIPTION);
        LESSON_SAVE_DTO.setLocalDateTime(LOCAL_DATE_TIME_STRING);

        LESSON_SAVE_DTO_MALFORMED = new LessonSaveDto();
        LESSON_SAVE_DTO_MALFORMED.setDescription(BLANK);
        LESSON_SAVE_DTO_MALFORMED.setLocalDateTime(BLANK);

        LESSON_UPDATE_DTO = new LessonUpdateDto();
        LESSON_UPDATE_DTO.setId(1L);
        LESSON_UPDATE_DTO.setDescription(DESCRIPTION);
        LESSON_UPDATE_DTO.setLocalDateTime(LOCAL_DATE_TIME_STRING);

        LESSON_UPDATE_DTO_MALFORMED = new LessonUpdateDto();
        LESSON_UPDATE_DTO_MALFORMED.setId(null);
        LESSON_UPDATE_DTO_MALFORMED.setDescription(BLANK);
        LESSON_UPDATE_DTO_MALFORMED.setLocalDateTime(BLANK);

        lessons = List.of(
                LESSON_READ_DTO,
                new LessonReadDto(),
                new LessonReadDto());
    }

    @Test
    void getAllLessons() throws Exception {
        when(lessonService.findAll()).thenReturn(lessons);

        mockMvc.perform(get(uriBuilder.build())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].description", equalTo(DESCRIPTION)))
                .andExpect(jsonPath("$[0].localDateTime", notNullValue()));
    }

    @Test
    void getAllLessonsWithoutInstructor() throws Exception {
        when(lessonService.findAllWithoutInstructor()).thenReturn(lessons);

        mockMvc.perform(get(uriBuilder.path("noInstructor").build())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].description", equalTo(DESCRIPTION)))
                .andExpect(jsonPath("$[0].localDateTime", notNullValue()));
    }

    @Test
    void getLessonById() throws Exception {
        when(lessonService.findById(anyLong())).thenReturn(LESSON_READ_DTO);

        mockMvc.perform(get(uriBuilder.path(ID).build())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", equalTo(DESCRIPTION)))
                .andExpect(jsonPath("$.localDateTime", notNullValue()));
    }

    @Test
    void save() throws Exception {
        when(lessonService.save(any())).thenReturn(LESSON_READ_DTO);

        MockHttpServletRequestBuilder mockRequest = post(uriBuilder.build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LESSON_SAVE_DTO));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", equalTo(DESCRIPTION)))
                .andExpect(jsonPath("$.localDateTime", notNullValue()));
    }

    @Test
    void saveMalformedJson() throws Exception {
        MockHttpServletRequestBuilder mockRequest = post(uriBuilder.build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LESSON_SAVE_DTO_MALFORMED));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void update() throws Exception {
        when(lessonService.update(any())).thenReturn(LESSON_READ_DTO);

        MockHttpServletRequestBuilder mockRequest = put(uriBuilder.path(ID).build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LESSON_UPDATE_DTO));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", equalTo(DESCRIPTION)))
                .andExpect(jsonPath("$.localDateTime", notNullValue()));
    }

    @Test
    void updateMalformedJson() throws Exception {
        when(lessonService.update(any())).thenReturn(LESSON_READ_DTO);

        MockHttpServletRequestBuilder mockRequest = put(uriBuilder.path(ID).build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LESSON_UPDATE_DTO_MALFORMED));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void deleteLessonById() throws Exception {
        mockMvc.perform(delete(uriBuilder.path(ID).build()))
                .andExpect(status().isOk());

        verify(lessonService).deleteById(anyLong());
    }
}