package com.example.sgswimming.controllers.api.v1;

import com.example.sgswimming.web.DTOs.LessonFatDto;
import com.example.sgswimming.web.DTOs.LessonSkinnyDto;
import com.example.sgswimming.web.DTOs.SwimmerSkinnyDto;
import com.example.sgswimming.services.LessonService;
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

import static com.example.sgswimming.web.config.JsonDateMappingConfig.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @BeforeEach
    void setUp() {
        uriBuilder = UriComponentsBuilder.fromUriString(LessonController.URL);
        objectMapper = JsonMapper.builder().findAndAddModules().build();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lessonController).build();
    }

    String DESC = "Description";
    LocalDateTime TIME = LocalDateTime.now();
    String ID = "1";

    LessonFatDto LESSON_FAT_DTO = LessonFatDto.builder()
            .description(DESC)
            .localDateTime(TIME)
            .swimmer(SwimmerSkinnyDto.builder().build())
            .swimmer(SwimmerSkinnyDto.builder().build())
            .swimmer(SwimmerSkinnyDto.builder().build())
            .build();

    LessonSkinnyDto LESSON_SKINNY_DTO = LessonSkinnyDto.builder()
            .description(DESC)
            .localDateTime(TIME.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
            .swimmerIds(List.of(1L, 2L, 3L))
            .build();

    LessonSkinnyDto LESSON_SKINNY_DTO_MALFORMED = LessonSkinnyDto.builder()
            .description("")
            .localDateTime("12:33 01-32-2000")
            .build();

    List<LessonFatDto> lessons = List.of(
            LESSON_FAT_DTO,
            new LessonFatDto(),
            new LessonFatDto());

    @Test
    void getAllLessons() throws Exception {
        when(lessonService.findAll()).thenReturn(lessons);

        mockMvc.perform(get(uriBuilder.build())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].description", equalTo(DESC)))
                .andExpect(jsonPath("$[0].localDateTime", notNullValue()))
                .andExpect(jsonPath("$[0].swimmers", hasSize(3)));
    }

    @Test
    void getLessonById() throws Exception {
        when(lessonService.findById(anyLong())).thenReturn(LESSON_FAT_DTO);

        mockMvc.perform(get(uriBuilder.path(ID).build())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", equalTo(DESC)))
                .andExpect(jsonPath("$.localDateTime", notNullValue()))
                .andExpect(jsonPath("$.swimmers", hasSize(3)));
    }

    @Test
    void saveNewLesson() throws Exception {
        when(lessonService.saveOrUpdate(any())).thenReturn(LESSON_FAT_DTO);

        MockHttpServletRequestBuilder mockRequest = post(uriBuilder.build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LESSON_SKINNY_DTO));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", equalTo(DESC)))
                .andExpect(jsonPath("$.localDateTime", notNullValue()));
    }

    @Test
    void saveNewLessonMalformedJson() throws Exception {
        when(lessonService.saveOrUpdate(any())).thenReturn(LESSON_FAT_DTO);

        MockHttpServletRequestBuilder mockRequest = post(uriBuilder.build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LESSON_SKINNY_DTO_MALFORMED));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void updateLesson() throws Exception {
        when(lessonService.saveOrUpdate(any())).thenReturn(LESSON_FAT_DTO);

        MockHttpServletRequestBuilder mockRequest = put(uriBuilder.path(ID).build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LESSON_SKINNY_DTO));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", equalTo(DESC)))
                .andExpect(jsonPath("$.localDateTime", notNullValue()))
                .andExpect(jsonPath("$.swimmers", hasSize(3)));
    }

    @Test
    void deleteLessonById() throws Exception {
        mockMvc.perform(delete(uriBuilder.path(ID).build()))
                .andExpect(status().isOk());

        verify(lessonService).deleteById(anyLong());
    }
}